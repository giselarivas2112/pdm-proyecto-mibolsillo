import supabase from '../config/supabase.js'

export const getResumenMensual = async (req, res) => {
    const usuario_id = req.user.id
    const { mes, anio } = req.query

    if (!mes || !anio) {
        return res.status(400).json({ error: 'El mes y el año son requeridos' })
    }

    try {
        // 1. Obtener presupuestos del mes
        const { data: presupuestos, error: errorPresupuestos } = await supabase
            .from('presupuestos')
            .select('*, categorias (id, nombre, icono)')
            .eq('usuario_id', usuario_id)
            .eq('mes', mes)
            .eq('anio', anio)

        if (errorPresupuestos) throw errorPresupuestos

        // 2. Obtener gastos del mes
        const mesPadded = String(mes).padStart(2, '0')
        const fechaInicio = `${anio}-${mesPadded}-01`
        const ultimoDia = new Date(anio, mes, 0).getDate()
        const fechaFin = `${anio}-${mesPadded}-${ultimoDia}`

        const { data: gastos, error: errorGastos } = await supabase
            .from('gastos')
            .select('categoria_id, monto')
            .eq('usuario_id', usuario_id)
            .gte('fecha', fechaInicio)
            .lte('fecha', fechaFin)

        if (errorGastos) throw errorGastos

        // 3. Sumar gastos por categoría
        const gastosPorCategoria = {}
        gastos.forEach(({ categoria_id, monto }) => {
            gastosPorCategoria[categoria_id] = (gastosPorCategoria[categoria_id] || 0) + parseFloat(monto)
        })

        // 4. Calcular resumen y estado de alerta por cada presupuesto
        const categorias = presupuestos.map(p => {
            const gastado = gastosPorCategoria[p.categoria_id] || 0
            const limite = parseFloat(p.monto_limite)
            const disponible = limite - gastado
            const porcentaje = parseFloat((gastado / limite) * 100)

            let estado
            if (porcentaje >= 100) estado = 'excedido'
            else if (porcentaje >= p.alerta_porcentaje) estado = 'cerca_del_limite'
            else estado = 'en_control'

            return {
                presupuesto_id: p.id,
                categoria: p.categorias,
                limite,
                gastado,
                disponible,
                porcentaje_usado: porcentaje,
                alerta_porcentaje: p.alerta_porcentaje,
                estado,
                notas: p.notas
            }
        })

        // 5. Totales generales
        let total_presupuestado = 0

        for (let i = 0; i < presupuestos.length; i++) {
            total_presupuestado += Number(presupuestos[i].monto_limite)
        }
        let total_gastado = 0

        for (const categoria in gastosPorCategoria) {
            total_gastado += gastosPorCategoria[categoria]
        }

        res.json({
            mes: parseInt(mes),
            anio: parseInt(anio),
            totales: {
                total_presupuestado,
                total_gastado,
                total_disponible: total_presupuestado - total_gastado,
                porcentaje_global:parseFloat((total_gastado / total_presupuestado) * 100)
            },
            categorias
        })

    } catch (error) {
        res.status(500).json({ error: error.message })
    }
}