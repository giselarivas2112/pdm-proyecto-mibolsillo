import express from 'express'
import { createBudget, getBudgets, updateBudget, deleteBudget } from '../controllers/budgets_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Presupuestos
 *   description: Endpoints de gestión de presupuestos
 */

/**
 * @swagger
 * /api/presupuestos:
 *   post:
 *     summary: Crear un presupuesto
 *     tags: [Presupuestos]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - categoria_id
 *               - monto_limite
 *               - mes
 *               - anio
 *               - alerta_porcentaje
 *             properties:
 *               categoria_id:
 *                 type: string
 *                 example: uuid-de-categoria
 *               monto_limite:
 *                 type: number
 *                 example: 2000
 *               mes:
 *                 type: integer
 *                 example: 6
 *               anio:
 *                 type: integer
 *                 example: 2026
 *               alerta_porcentaje:
 *                 type: integer
 *                 example: 80
 *               notas:
 *                 type: string
 *                 example: Presupuesto para comida de junio
 *     responses:
 *       201:
 *         description: Presupuesto creado exitosamente
 *       400:
 *         description: Campos obligatorios faltantes
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.post('/', verifyToken, createBudget)

/**
 * @swagger
 * /api/presupuestos:
 *   get:
 *     summary: Obtener presupuestos del usuario
 *     tags: [Presupuestos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: mes
 *         schema:
 *           type: integer
 *         description: Mes a filtrar (1-12)
 *         example: 6
 *       - in: query
 *         name: anio
 *         schema:
 *           type: integer
 *         description: Año a filtrar
 *         example: 2026
 *     responses:
 *       200:
 *         description: Lista de presupuestos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/', verifyToken, getBudgets)

/**
 * @swagger
 * /api/presupuestos/{id}:
 *   put:
 *     summary: Actualizar un presupuesto
 *     tags: [Presupuestos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del presupuesto
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               categoria_id:
 *                 type: string
 *                 example: uuid-de-categoria
 *               monto_limite:
 *                 type: number
 *                 example: 2500
 *               mes:
 *                 type: integer
 *                 example: 6
 *               anio:
 *                 type: integer
 *                 example: 2026
 *               alerta_porcentaje:
 *                 type: integer
 *                 example: 90
 *               notas:
 *                 type: string
 *                 example: Actualicé el límite
 *     responses:
 *       200:
 *         description: Presupuesto actualizado
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.put('/:id', verifyToken, updateBudget)

/**
 * @swagger
 * /api/presupuestos/{id}:
 *   delete:
 *     summary: Eliminar un presupuesto
 *     tags: [Presupuestos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del presupuesto
 *     responses:
 *       200:
 *         description: Presupuesto eliminado
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.delete('/:id', verifyToken, deleteBudget)

export default router