import supabase from '../config/supabase.js'
import bcrypt from 'bcryptjs'
import jwt from 'jsonwebtoken'

export const register = async (req, res) => {
    const { name, email, password } = req.body

    try {
        const { data: existingUser } = await supabase
            .from('users')
            .select('id')
            .eq('email', email)
            .single()

        if (existingUser) {
            return res.status(400).json({ error: 'El email ya está registrado' })
        }

        const password_hash = await bcrypt.hash(password, 10)

        const { data, error } = await supabase
            .from('users')
            .insert([{ name, email, password_hash }])
            .select()
            .single()

        if (error) throw error

        res.status(201).json({
            message: 'Usuario registrado exitosamente',
            user: { id: data.id, name: data.name, email: data.email }
        })

    } catch (error) {
        res.status(500).json({ error: error.message })
    }
}



export const login = async (req, res) => {
  const { email, password } = req.body

  try {
    // Buscar usuario por email
    const { data: user, error } = await supabase
      .from('users')
      .select('*')
      .eq('email', email)
      .single()

    if (error || !user) {
      return res.status(401).json({ error: 'Email o contraseña incorrectos' })
    }

    // Verificar contraseña
    const validPassword = await bcrypt.compare(password, user.password_hash)

    if (!validPassword) {
      return res.status(401).json({ error: 'Email o contraseña incorrectos' })
    }

    // Generar JWT
    const token = jwt.sign(
      { id: user.id, email: user.email },
      process.env.JWT_SECRET,
      { expiresIn: '7d' }
    )

    res.json({
      message: 'Login exitoso',
      token,
      user: { id: user.id, name: user.name, email: user.email }
    })

  } catch (error) {
    res.status(500).json({ error: error.message })
  }
}