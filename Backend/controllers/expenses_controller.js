import supabase from '../config/supabase.js'

export const createExpense = async (req, res) => {
  const { categoria_id, monto, fecha, descripcion } = req.body
  const usuario_id = req.user.id

  if (!monto || !fecha) {
    return res.status(400).json({ error: 'El monto y la fecha son requeridos' })
  }

  try {
    const { data, error } = await supabase
      .from('gastos')
      .insert([{ usuario_id, categoria_id, monto, fecha, descripcion }])
      .select(`
        *,
        categorias (id, nombre, icono)
      `)
      .single()

    if (error) throw error

    res.status(201).json({ message: 'Gasto registrado exitosamente', expense: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const getExpenses = async (req, res) => {
  const usuario_id = req.user.id
  const { month, year } = req.query

  try {
    let query = supabase
      .from('gastos')
      .select(`
        *,
        categorias (id, nombre, icono)
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
      .select(`
        *,
        categorias (id, nombre, icono)
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