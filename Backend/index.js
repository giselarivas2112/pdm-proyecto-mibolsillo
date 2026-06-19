import express from 'express'
import dotenv from 'dotenv'
import authRoutes from './routes/auth_routes.js'
import categoriesRoutes from './routes/categories_routes.js'
import expensesRoutes from './routes/expenses_routes.js'
import budgetsRoutes from './routes/budgets_routes.js'
import statsRoutes from './routes/stats_routes.js'
import fixedPaymentsRoutes from './routes/fixed_payments_routes.js'
import swaggerUi from 'swagger-ui-express'
import swaggerSpec from './swagger.js'
import cors from 'cors'


dotenv.config()

const app = express()
const PORT = process.env.PORT || 4000
app.use(cors())
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec))

app.use(express.json())
app.use('/api/auth', authRoutes)
app.use('/api/categorias', categoriesRoutes)
app.use('/api/gastos', expensesRoutes)
app.use('/api/presupuestos', budgetsRoutes)
app.use('/api/estadisticas', statsRoutes)
app.use('/api/pagos-fijos', fixedPaymentsRoutes)

app.get('/', (req, res) => {
    res.json({ message: 'MiBolsillo API funcionando :D' })
})

app.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`)
})