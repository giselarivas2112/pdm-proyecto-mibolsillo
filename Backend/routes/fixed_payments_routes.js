import express from 'express'
import {
  createFixedPayment,
  getFixedPayments,
  updateFixedPayment,
  deleteFixedPayment,
  getProximosVencimientos
} from '../controllers/fixed_payments_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'
import { sendPushNotification } from '../services/notification_service.js'


const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Pagos Fijos
 *   description: Endpoints de gestión de pagos fijos
 */

/**
 * @swagger
 * /api/pagos-fijos:
 *   post:
 *     summary: Registrar un pago fijo
 *     tags: [Pagos Fijos]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - nombre
 *               - categoria_id
 *               - monto
 *               - dia_vencimiento
 *               - dias_recordatorio
 *             properties:
 *               nombre:
 *                 type: string
 *                 example: Netflix
 *               categoria_id:
 *                 type: string
 *                 example: uuid-de-categoria
 *               monto:
 *                 type: number
 *                 example: 199.00
 *               dia_vencimiento:
 *                 type: integer
 *                 example: 20
 *               dias_recordatorio:
 *                 type: integer
 *                 example: 3
 *     responses:
 *       201:
 *         description: Pago fijo registrado exitosamente
 *       400:
 *         description: Campos obligatorios faltantes o valores inválidos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.post('/', verifyToken, createFixedPayment)

/**
 * @swagger
 * /api/pagos-fijos:
 *   get:
 *     summary: Obtener todos los pagos fijos del usuario
 *     tags: [Pagos Fijos]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Lista de pagos fijos ordenados por día de vencimiento
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/', verifyToken, getFixedPayments)

/**
 * @swagger
 * /api/pagos-fijos/proximos:
 *   get:
 *     summary: Obtener pagos próximos a vencer hoy
 *     tags: [Pagos Fijos]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Lista de pagos cuyo recordatorio cae en el día de hoy
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/proximos', verifyToken, getProximosVencimientos)

/**
 * @swagger
 * /api/pagos-fijos/{id}:
 *   put:
 *     summary: Actualizar un pago fijo
 *     tags: [Pagos Fijos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del pago fijo
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nombre:
 *                 type: string
 *                 example: Spotify
 *               categoria_id:
 *                 type: string
 *                 example: uuid-de-categoria
 *               monto:
 *                 type: number
 *                 example: 99.00
 *               dia_vencimiento:
 *                 type: integer
 *                 example: 15
 *               dias_recordatorio:
 *                 type: integer
 *                 example: 2
 *     responses:
 *       200:
 *         description: Pago fijo actualizado
 *       400:
 *         description: Valores inválidos
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.put('/:id', verifyToken, updateFixedPayment)

/**
 * @swagger
 * /api/pagos-fijos/{id}:
 *   delete:
 *     summary: Eliminar un pago fijo
 *     tags: [Pagos Fijos]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID del pago fijo
 *     responses:
 *       200:
 *         description: Pago fijo eliminado
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.delete('/:id', verifyToken, deleteFixedPayment)



export default router