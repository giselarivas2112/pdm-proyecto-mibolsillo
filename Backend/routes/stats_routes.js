import express from 'express'
import { getResumenMensual, getDistribucion, getGastosDiarios } from '../controllers/stats_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Estadísticas
 *   description: Endpoints de estadísticas y reportes financieros
 */

/**
 * @swagger
 * /api/estadisticas/resumen:
 *   get:
 *     summary: Obtener resumen mensual con alertas de presupuesto
 *     tags: [Estadísticas]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: mes
 *         required: true
 *         schema:
 *           type: integer
 *         description: Mes a consultar (1-12)
 *         example: 6
 *       - in: query
 *         name: anio
 *         required: true
 *         schema:
 *           type: integer
 *         description: Año a consultar
 *         example: 2026
 *     responses:
 *       200:
 *         description: Resumen mensual con totales y estado de alerta por categoría
 *       400:
 *         description: El mes y el año son requeridos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/resumen', verifyToken, getResumenMensual)

/**
 * @swagger
 * /api/estadisticas/distribucion:
 *   get:
 *     summary: Obtener distribución de gastos por categoría
 *     tags: [Estadísticas]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: mes
 *         required: true
 *         schema:
 *           type: integer
 *         description: Mes a consultar (1-12)
 *         example: 6
 *       - in: query
 *         name: anio
 *         required: true
 *         schema:
 *           type: integer
 *         description: Año a consultar
 *         example: 2026
 *     responses:
 *       200:
 *         description: Distribución porcentual de gastos por categoría
 *       400:
 *         description: El mes y el año son requeridos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/distribucion', verifyToken, getDistribucion)

/**
 * @swagger
 * /api/estadisticas/gastos-diarios:
 *   get:
 *     summary: Obtener gastos diarios del mes
 *     tags: [Estadísticas]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: mes
 *         required: true
 *         schema:
 *           type: integer
 *         description: Mes a consultar (1-12)
 *         example: 6
 *       - in: query
 *         name: anio
 *         required: true
 *         schema:
 *           type: integer
 *         description: Año a consultar
 *         example: 2026
 *     responses:
 *       200:
 *         description: Total gastado por cada día del mes
 *       400:
 *         description: El mes y el año son requeridos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/gastos-diarios', verifyToken, getGastosDiarios)

export default router