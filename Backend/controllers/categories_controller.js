import supabase from '../config/supabase.js'

//Crear una categoría
export const createCategory = async (req, res) => {
  const { name, icon } = req.body
  const user_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categories')
      .insert([{ user_id, name, icon }])
      .select()
      .single()

    if (error) throw error

    res.status(201).json({ message: 'Categoría creada exitosamente', category: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}


// Obtener todas las categorías del usuario
export const getCategories = async (req, res) => {
  const user_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categories')
      .select('*')
      .eq('user_id', user_id)

    if (error) throw error

    res.json(data)

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Actualizar categoría
export const updateCategory = async (req, res) => {
  const { id } = req.params
  const { name, icon } = req.body
  const user_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categories')
      .update({ name, icon })
      .eq('id', id)
      .eq('user_id', user_id)
      .select()
      .single()

    if (error) throw error

    res.json({ message: 'Categoría actualizada', category: data })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Eliminar categoría
export const deleteCategory = async (req, res) => {
  const { id } = req.params
  const user_id = req.user.id

  try {
    const { error } = await supabase
      .from('categories')
      .delete()
      .eq('id', id)
      .eq('user_id', user_id)

    if (error) throw error

    res.json({ message: 'Categoría eliminada' })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}