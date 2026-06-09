import supabase from '../config/supabase.js'

export const createCategory = async (req, res) => {
  const { nombre, icono } = req.body
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .insert([{ usuario_id, nombre, icono }])
      .select()
      .single()

    if (error) throw error

    res.status(201).json({ message: 'Categoría creada exitosamente', category: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const getCategories = async (req, res) => {
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .select('*')
      .eq('usuario_id', usuario_id)

    if (error) throw error

    res.json(data)

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const updateCategory = async (req, res) => {
  const { id } = req.params
  const { nombre, icono } = req.body
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .update({ nombre, icono })
      .eq('id', id)
      .eq('usuario_id', usuario_id)
      .select()
      .single()

    if (error) throw error

    res.json({ message: 'Categoría actualizada', category: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

export const deleteCategory = async (req, res) => {
  const { id } = req.params
  const usuario_id = req.user.id

  try {
    const { error } = await supabase
      .from('categorias')
      .delete()
      .eq('id', id)
      .eq('usuario_id', usuario_id)

    if (error) throw error

    res.json({ message: 'Categoría eliminada' })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}