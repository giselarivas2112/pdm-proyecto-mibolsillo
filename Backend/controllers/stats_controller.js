import supabase from '../config/supabase.js'

export const getResumenMensual = async (req, res) => {

    const usuario_id = req.user.id

    const mes = req.query.mes
    const anio = req.query.anio

    if (!mes || !anio) {
        return res.status(400).json({
            error: 'El mes y el año son requeridos'
        })
    }

    try {

    
        // OBTENER PRESUPUESTOS
      

        const resultadoPresupuestos = await supabase
            .from('presupuestos')
            .select('*, categorias (id, nombre, icono)')
            .eq('usuario_id', usuario_id)
            .eq('mes', mes)
            .eq('anio', anio)

        const presupuestos = resultadoPresupuestos.data
        const errorPresupuestos = resultadoPresupuestos.error

        if (errorPresupuestos) {
            throw errorPresupuestos
        }

     
        // CALCULAR FECHAS DEL MES
      

        const mesPadded = String(mes).padStart(2, '0')

        const fechaInicio = `${anio}-${mesPadded}-01`

        const ultimoDia = new Date(anio, mes, 0).getDate()

        const fechaFin = `${anio}-${mesPadded}-${ultimoDia}`

      
        // OBTENER GASTOS
       
        const resultadoGastos = await supabase
            .from('gastos')
            .select('categoria_id, monto')
            .eq('usuario_id', usuario_id)
            .gte('fecha', fechaInicio)
            .lte('fecha', fechaFin)

        const gastos = resultadoGastos.data
        const errorGastos = resultadoGastos.error

        if (errorGastos) {
            throw errorGastos
        }

     
        // SUMAR GASTOS POR CATEGORÍA
     

        const gastosPorCategoria = {}

        gastos.forEach((gasto) => {

            const categoria_id = gasto.categoria_id
            const monto = gasto.monto

            const gastoActual =gastosPorCategoria[categoria_id] || 0

            const montoNumerico =parseFloat(monto)

            gastosPorCategoria[categoria_id] =gastoActual + montoNumerico
        })

        // GENERAR RESUMEN
      

        const categorias = presupuestos.map((presupuesto) => {

            const gastado =gastosPorCategoria[presupuesto.categoria_id] || 0

            const limite =parseFloat(presupuesto.monto_limite)

            const disponible =limite - gastado

            const operacionPorcentaje =(gastado / limite) * 100

            const porcentaje =parseFloat(operacionPorcentaje.toFixed(2))

            let estado

            if (porcentaje >= 100) {

                estado = 'excedido'

            } else if (
                porcentaje >= presupuesto.alerta_porcentaje
            ) {

                estado = 'cerca_del_limite'

            } else {

                estado = 'en_control'
            }

            return {
                presupuesto_id: presupuesto.id,
                categoria: presupuesto.categorias,
                limite: limite,
                gastado: gastado,
                disponible: disponible,
                porcentaje_usado: porcentaje,
                alerta_porcentaje: presupuesto.alerta_porcentaje,
                estado: estado,
                notas: presupuesto.notas
            }
        })

     
        // CALCULAR TOTALES
        

        let total_presupuestado = 0

        for (let i = 0;i < presupuestos.length;i++) {

            total_presupuestado =total_presupuestado +Number(presupuestos[i].monto_limite)
        }

        let total_gastado = 0

        for (const categoria in gastosPorCategoria) {

            total_gastado =total_gastado +gastosPorCategoria[categoria]
        }

        const porcentajeGlobal =parseFloat(((total_gastado / total_presupuestado) * 100).toFixed(2))

        // RESPUESTA
      
        res.json({

            mes: parseInt(mes),

            anio: parseInt(anio),

            totales: {

                total_presupuestado: total_presupuestado,

                total_gastado: total_gastado,

                total_disponible:total_presupuestado - total_gastado,

                porcentaje_global: porcentajeGlobal
            },

            categorias: categorias
        })

    } catch (error) {

        res.status(500).json({
            error: error.message
        })
    }
}



// DISTRIBUCIÓN DE GASTOS POR CATEGORÍA


export const getDistribucion = async (req, res) => {

    const usuario_id = req.user.id

    const mes = req.query.mes
    const anio = req.query.anio

    if (!mes || !anio) {
        return res.status(400).json({
            error: 'El mes y el año son requeridos'
        })
    }

    try {

        const mesPadded =String(mes).padStart(2, '0')

        const fechaInicio = `${anio}-${mesPadded}-01`

        const ultimoDia =new Date(anio, mes, 0).getDate()

        const fechaFin =`${anio}-${mesPadded}-${ultimoDia}`

        const resultado = await supabase
            .from('gastos')
            .select('monto, categorias (id, nombre, icono)')
            .eq('usuario_id', usuario_id)
            .gte('fecha', fechaInicio)
            .lte('fecha', fechaFin)

        const gastos = resultado.data
        const error = resultado.error

        if (error) {
            throw error
        }

        const agrupado = {}

        let totalGastado = 0

        gastos.forEach((gasto) => {

            const monto =parseFloat(gasto.monto)

            let nombreCategoria ='Sin categoría'

            let icono = null

            if (gasto.categorias) {

                nombreCategoria =gasto.categorias.nombre

                icono =gasto.categorias.icono
            }

            if (!agrupado[nombreCategoria]) {

                agrupado[nombreCategoria] = {
                    nombre: nombreCategoria,
                    icono: icono,
                    total: 0
                }
            }

            agrupado[nombreCategoria].total =agrupado[nombreCategoria].total + monto

            totalGastado =totalGastado + monto
        })

        const distribucion =Object.values(agrupado).map((categoria) => {

                const porcentaje =parseFloat(((categoria.total / totalGastado) * 100).toFixed(2))

                return {
                    nombre: categoria.nombre,
                    icono: categoria.icono,
                    total: parseFloat(
                        categoria.total.toFixed(2)
                    ),
                    porcentaje: porcentaje
                }
            })

        distribucion.sort((a, b) => {
            return b.total - a.total
        })

        res.json({

            mes: parseInt(mes),

            anio: parseInt(anio),

            total_gastado:
                parseFloat(totalGastado.toFixed(2)),

            distribucion: distribucion
        })

    } catch (error) {

        res.status(500).json({
            error: error.message
        })
    }
}



// GASTOS DIARIOS


export const getGastosDiarios = async (req, res) => {

    const usuario_id = req.user.id

    const mes = req.query.mes
    const anio = req.query.anio

    if (!mes || !anio) {
        return res.status(400).json({
            error: 'El mes y el año son requeridos'
        })
    }

    try {

        const mesPadded =String(mes).padStart(2, '0')

        const fechaInicio = `${anio}-${mesPadded}-01`

        const ultimoDia =new Date(anio, mes, 0).getDate()

        const fechaFin =`${anio}-${mesPadded}-${ultimoDia}`

        const resultado = await supabase
            .from('gastos')
            .select('monto, fecha')
            .eq('usuario_id', usuario_id)
            .gte('fecha', fechaInicio)
            .lte('fecha', fechaFin)

        const gastos = resultado.data
        const error = resultado.error

        if (error) {
            throw error
        }

        const gastosPorDia = {}

        gastos.forEach((gasto) => {

            const fecha =gasto.fecha

            const monto =parseFloat(gasto.monto)

            const partesFecha =fecha.split('-')

            const dia =parseInt(partesFecha[2])

            const gastoActual =gastosPorDia[dia] || 0

            gastosPorDia[dia] =gastoActual + monto
        })

        const dias = []

        for (let dia = 1; dia <= ultimoDia; dia++) {

            dias.push({
                dia: dia,
                total: parseFloat((gastosPorDia[dia] || 0).toFixed(2))
            })
        }

        res.json({

            mes: parseInt(mes),

            anio: parseInt(anio),

            dias: dias
        })

    } catch (error) {

        res.status(500).json({
            error: error.message
        })
    }
}