import supabase from '../config/supabase.js'

// Crear presupuesto
export const createBudget = async (req, res) => {
  const {
    categoria_id,
    monto_limite,
    mes,
    anio,
    alerta_porcentaje,
    notas
  } = req.body

  const usuario_id = req.user.id

  if (
    !categoria_id ||
    !monto_limite ||
    !mes ||
    !anio ||
    !alerta_porcentaje
  ) {
    return res.status(400).json({
      error: 'Todos los campos obligatorios son requeridos'
    })
  }

  try {
    const { data, error } = await supabase
      .from('presupuestos')
      .insert([
        {
          usuario_id: usuario_id,
          categoria_id: categoria_id,
          monto_limite: monto_limite,
          mes: mes,
          anio: anio,
          alerta_porcentaje: alerta_porcentaje,
          notas: notas
        }
      ])
      .select(`*,categorias (id,nombre,icono)`)
      .single()

    if (error) {
      throw error
    }

    return res.status(201).json({
      message: 'Presupuesto creado exitosamente',
      budget: data
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}

// Obtener presupuestos
export const getBudgets = async (req, res) => {
  const usuario_id = req.user.id

  const { mes, anio } = req.query

  try {
    let query = supabase
      .from('presupuestos')
      .select(`*,categorias (id,nombre,icono)`)
      .eq('usuario_id', usuario_id)
      .order('fecha_creacion', {
        ascending: false
      })

    if (mes && anio) {
      query = query
        .eq('mes', mes)
        .eq('anio', anio)
    }

    const { data, error } = await query

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

// Actualizar presupuesto
export const updateBudget = async (req, res) => {
  const { id } = req.params

  const {
    categoria_id,
    monto_limite,
    mes,
    anio,
    alerta_porcentaje,
    notas
  } = req.body

  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('presupuestos')
      .update({
        categoria_id: categoria_id,
        monto_limite: monto_limite,
        mes: mes,
        anio: anio,
        alerta_porcentaje: alerta_porcentaje,
        notas: notas
      })
      .eq('id', id)
      .eq('usuario_id', usuario_id)
      .select(`*,categorias (id,nombre,icono)`)
      .single()

    if (error) {
      throw error
    }

    return res.json({
      message: 'Presupuesto actualizado',
      budget: data
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}

// Eliminar presupuesto
export const deleteBudget = async (req, res) => {
  const { id } = req.params

  const usuario_id = req.user.id

  try {
    const { error } = await supabase
      .from('presupuestos')
      .delete()
      .eq('id', id)
      .eq('usuario_id', usuario_id)

    if (error) {
      throw error
    }

    return res.json({
      message: 'Presupuesto eliminado'
    })

  } catch (error) {
    return res.status(500).json({
      error: error.message
    })
  }
}