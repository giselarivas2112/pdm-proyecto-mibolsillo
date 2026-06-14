import express from 'express'
import { getResumenMensual, getDistribucion, getGastosDiarios } from '../controllers/stats_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.get('/resumen', verifyToken, getResumenMensual)
router.get('/distribucion', verifyToken, getDistribucion)
router.get('/gastos-diarios', verifyToken, getGastosDiarios)

export default router