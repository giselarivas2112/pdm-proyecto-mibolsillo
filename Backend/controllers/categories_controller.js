import supabase from '../config/supabase.js'

export const createCategory = async (req, res) => {
  const { nombre, icono } = req.body

  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .insert([
        {
          usuario_id: usuario_id,
          nombre: nombre,
          icono: icono
        }
      ])
      .select()
      .single()

    if (error) {
      throw error
    }

    return res.status(201).json({
      message: 'Categoría creada exitosamente',
      category: data
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}

export const getCategories = async (req, res) => {
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .select('*')
      .eq('usuario_id', usuario_id)

    if (error) {
      throw error
    }

    return res.json(data)

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}

export const updateCategory = async (req, res) => {
  const { id } = req.params

  const { nombre, icono } = req.body

  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('categorias')
      .update({
        nombre: nombre,
        icono: icono
      })
      .eq('id', id)
      .eq('usuario_id', usuario_id)
      .select()
      .single()

    if (error) {
      throw error
    }

    return res.json({
      message: 'Categoría actualizada',
      category: data
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
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

    if (error) {
      throw error
    }

    return res.json({
      message: 'Categoría eliminada'
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}