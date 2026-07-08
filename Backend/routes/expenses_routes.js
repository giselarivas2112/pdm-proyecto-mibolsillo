import express from 'express'
import { createExpense, getExpenses,  deleteExpense } from '../controllers/expenses_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Gastos
 *   description: Endpoints de gestión de gastos
 */

/**
 * @swagger
 * /api/gastos:
 *   post:
 *     summary: Registrar un gasto
 *     tags: [Gastos]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - monto
 *               - fecha
 *             properties:
 *               categoria_id:
 *                 type: string
 *                 example: uuid-de-categoria
 *               monto:
 *                 type: number
 *                 example: 145.00
 *               fecha:
 *                 type: string
 *                 example: 2026-06-09
 *               descripcion:
 *                 type: string
 *                 example: McDonald's
 *     responses:
 *       201:
 *         description: Gasto registrado exitosamente
 *       400:
 *         description: El monto y la fecha son requeridos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.post('/', verifyToken, createExpense)

/**
 * @swagger
 * /api/gastos:
 *   get:
 *     summary: Obtener gastos del usuario
 *     tags: [Gastos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: month
 *         schema:
 *           type: integer
 *         description: Mes a filtrar (1-12)
 *         example: 6
 *       - in: query
 *         name: year
 *         schema:
 *           type: integer
 *         description: Año a filtrar
 *         example: 2026
 *     responses:
 *       200:
 *         description: Lista de gastos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/', verifyToken, getExpenses)


/**
 * @swagger
 * /api/gastos/{id}:
 *   delete:
 *     summary: Eliminar un gasto
 *     tags: [Gastos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del gasto
 *     responses:
 *       200:
 *         description: Gasto eliminado
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.delete('/:id', verifyToken, deleteExpense)

export default router