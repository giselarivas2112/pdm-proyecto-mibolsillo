import express from 'express'
import dotenv from 'dotenv'
import authRoutes from './routes/auth_routes.js'
import categoriesRoutes from './routes/categories_routes.js'
import expensesRoutes from './routes/expenses_routes.js'

dotenv.config()


const app = express()
const PORT = process.env.PORT || 3000

app.use(express.json())
app.use('/api/auth', authRoutes)
app.use('/api/categories', categoriesRoutes)
app.use('/api/expenses', expensesRoutes)

app.get('/', (req, res) => {
    res.json({ message: 'MiBolsillo API funcionando :D' })
})

app.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`)
})