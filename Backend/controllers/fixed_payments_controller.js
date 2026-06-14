import supabase from '../config/supabase.js'

// Crear pago fijo
export const createFixedPayment = async (req, res) => {
  const { categoria_id, nombre, monto, dia_vencimiento, dias_recordatorio } = req.body
  const usuario_id = req.user.id

  if (!nombre || !monto || !dia_vencimiento || !dias_recordatorio || !categoria_id) {
    return res.status(400).json({ error: 'Todos los campos son requeridos' })
  }

  if (dia_vencimiento < 1 || dia_vencimiento > 31) {
    return res.status(400).json({ error: 'El día de vencimiento debe ser entre 1 y 31' })
  }

  if (dias_recordatorio < 1 || dias_recordatorio > 3) {
    return res.status(400).json({ error: 'El recordatorio máximo es 3 días antes' })
  }

  try {
    const nuevoPago = {
      usuario_id: usuario_id,
      categoria_id: categoria_id,
      nombre: nombre,
      monto: monto,
      dia_vencimiento: dia_vencimiento,
      dias_recordatorio: dias_recordatorio
    }

    const { data, error } = await supabase
      .from('pagos_fijos')
      .insert([nuevoPago])
      .select('*, categorias (id, nombre, icono)')
      .single()

    if (error) throw error

    res.status(201).json({
      message: 'Pago fijo registrado exitosamente',
      pago: data
    })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Obtener todos los pagos fijos del usuario
export const getFixedPayments = async (req, res) => {
  const usuario_id = req.user.id

  try {
    const { data, error } = await supabase
      .from('pagos_fijos')
      .select('*, categorias (id, nombre, icono)')
      .eq('usuario_id', usuario_id)
      .order('dia_vencimiento', { ascending: true })

    if (error) throw error

    res.json(data)

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Actualizar pago fijo
export const updateFixedPayment = async (req, res) => {
  const { id } = req.params
  const { categoria_id, nombre, monto, dia_vencimiento, dias_recordatorio } = req.body
  const usuario_id = req.user.id

  if (dia_vencimiento && (dia_vencimiento < 1 || dia_vencimiento > 31)) {
    return res.status(400).json({ error: 'El día de vencimiento debe ser entre 1 y 31' })
  }

  if (dias_recordatorio && (dias_recordatorio < 1 || dias_recordatorio > 3)) {
    return res.status(400).json({ error: 'El recordatorio máximo es 3 días antes' })
  }

  try {
    const { data, error } = await supabase
      .from('pagos_fijos')
      .update({ categoria_id, nombre, monto, dia_vencimiento, dias_recordatorio })
      .eq('id', id)
      .eq('usuario_id', usuario_id)
      .select('*, categorias (id, nombre, icono)')
      .single()

    if (error) throw error

    res.json({
      message: 'Pago fijo actualizado',
      pago: data
    })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Eliminar pago fijo
export const deleteFixedPayment = async (req, res) => {
  const { id } = req.params
  const usuario_id = req.user.id

  try {
    const { error } = await supabase
      .from('pagos_fijos')
      .delete()
      .eq('id', id)
      .eq('usuario_id', usuario_id)

    if (error) throw error

    res.json({ message: 'Pago fijo eliminado' })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}

// Obtener pagos próximos a vencer (para OneSignal)
export const getProximosVencimientos = async (req, res) => {
  const usuario_id = req.user.id

  try {
    const hoy = new Date()
    const diaHoy = hoy.getDate()

    const { data, error } = await supabase
      .from('pagos_fijos')
      .select('*, categorias (id, nombre, icono)')
      .eq('usuario_id', usuario_id)

    if (error) throw error

    const proximosVencer = []

    for (let i = 0; i < data.length; i++) {
      const pago = data[i]

      const diaRecordatorio = pago.dia_vencimiento - pago.dias_recordatorio

      if (diaHoy === diaRecordatorio) {
        proximosVencer.push(pago)
      }
    }

    res.json(proximosVencer)

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}