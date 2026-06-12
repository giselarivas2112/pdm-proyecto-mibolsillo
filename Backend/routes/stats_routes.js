import express from 'express'
import { getResumenMensual } from '../controllers/stats_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.get('/resumen', verifyToken, getResumenMensual)

export default router