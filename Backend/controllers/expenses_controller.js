import supabase from '../config/supabase.js'

export const createExpense = async (req, res) => {
  const { categoria_id, monto, fecha, descripcion } = req.body
  const usuario_id = req.user.id

  if (!monto || !fecha) {
    return res.status(400).json({
      error: 'El monto y la fecha son requeridos'
    })
  }

  try {
    const { data, error } = await supabase
      .from('gastos')
      .insert([
        {
          usuario_id: usuario_id,
          categoria_id: categoria_id,
          monto: monto,
          fecha: fecha,
          descripcion: descripcion
        }
      ])
      .select(`*,categorias (id, nombre, icono)`)
      .single()

    if (error) {
      throw error
    }

    let alerta = null

    if (categoria_id) {

      const fechaGasto = new Date(fecha)

      const mes = fechaGasto.getMonth() + 1
      const anio = fechaGasto.getFullYear()

      const { data: presupuesto } = await supabase
        .from('presupuestos')
        .select('*')
        .eq('usuario_id', usuario_id)
        .eq('categoria_id', categoria_id)
        .eq('mes', mes)
        .eq('anio', anio)
        .single()

      if (presupuesto) {

        const mesFormateado = String(mes).padStart(2, '0')

        const primerDiaMes = `${anio}-${mesFormateado}-01`

        const ultimoDiaMes = `${anio}-${mesFormateado}-${new Date(anio, mes, 0).getDate()}`

        const { data: gastos } = await supabase
          .from('gastos')
          .select('monto')
          .eq('usuario_id', usuario_id)
          .eq('categoria_id', categoria_id)
          .gte('fecha', primerDiaMes)
          .lte('fecha', ultimoDiaMes)

        let totalGastado = 0

        for (const gasto of gastos) {
          totalGastado += parseFloat(gasto.monto)
        }

        const montoLimite = parseFloat(presupuesto.monto_limite)

        const porcentajeUsado =
          (totalGastado / montoLimite) * 100

        if (porcentajeUsado >= presupuesto.alerta_porcentaje) {
          alerta = {
            mensaje:
              `Has usado el ${porcentajeUsado.toFixed(1)}% de tu presupuesto de ${presupuesto.categorias?.nombre || 'esta categoría'}`,

            porcentaje_usado: porcentajeUsado.toFixed(1),

            monto_limite: presupuesto.monto_limite,

            total_gastado: totalGastado
          }
        }
      }
    }

    return res.status(201).json({
      message: 'Gasto registrado exitosamente',
      expense: data,
      alerta: alerta
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}

export const getExpenses = async (req, res) => {
  const usuario_id = req.user.id
  const { month, year } = req.query

  try {
    let query = supabase
      .from('gastos')
      .select(`*, categorias (id, nombre, icono)
      `)
      .eq('usuario_id', usuario_id)
      .order('fecha', { ascending: false })

    if (month && year) {
      const paddedMonth = String(month).padStart(2, '0')
      const startDate = `${year}-${paddedMonth}-01`
      const lastDay = new Date(year, month, 0).getDate()
      const endDate = `${year}-${paddedMonth}-${lastDay}`

      query = query.gte('fecha', startDate).lte('fecha', endDate)
    }

    const { data, error } = await query

    if (error) throw error

    res.json(data)

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const updateExpense = async (req, res) => {
  const { id } = req.params
  const { categoria_id, monto, fecha, descripcion } = req.body
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('gastos')
      .update({ categoria_id, monto, fecha, descripcion })
      .eq('id', id)
      .eq('usuario_id', usuario_id)
      .select(`*, categorias (id, nombre, icono)
      `)
      .single()

    if (error) throw error

    res.json({ message: 'Gasto actualizado', expense: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const deleteExpense = async (req, res) => {
  const { id } = req.params
  const usuario_id = req.user.id

  try {
    const { error } = await supabase
      .from('gastos')
      .delete()
      .eq('id', id)
      .eq('usuario_id', usuario_id)

    if (error) throw error

    res.json({ message: 'Gasto eliminado' })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}