import express from 'express'
import {register, login, saveOneSignalId, getProfile} from '../controllers/auth_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'
const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Auth
 *   description: Endpoints de autenticación
 */

/**
 * @swagger
 * /api/auth/register:
 *   post:
 *     summary: Registrar un nuevo usuario
 *     tags: [Auth]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - nombre
 *               - email
 *               - password
 *             properties:
 *               nombre:
 *                 type: string
 *                 example: Gisela
 *               email:
 *                 type: string
 *                 example: gise@test.com
 *               password:
 *                 type: string
 *                 example: 123456
 *     responses:
 *       201:
 *         description: Usuario registrado exitosamente
 *       400:
 *         description: El email ya está registrado
 *       500:
 *         description: Error del servidor
 */
router.post('/register', register)

/**
 * @swagger
 * /api/auth/login:
 *   post:
 *     summary: Iniciar sesión
 *     tags: [Auth]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - email
 *               - password
 *             properties:
 *               email:
 *                 type: string
 *                 example: gise@test.com
 *               password:
 *                 type: string
 *                 example: 123456
 *     responses:
 *       200:
 *         description: Login exitoso, devuelve token JWT
 *       401:
 *         description: Email o contraseña incorrectos
 *       500:
 *         description: Error del servidor
 */
router.post('/login', login)

/**
 * @swagger
 * /api/auth/onesignal:
 *   put:
 *     summary: Guardar o actualizar el OneSignal ID del usuario
 *     tags: [Auth]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - onesignal_id
 *             properties:
 *               onesignal_id:
 *                 type: string
 *                 example: 12345678-abcd-1234-abcd-1234567890ab
 *     responses:
 *       200:
 *         description: OneSignal ID guardado correctamente
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.put('/onesignal', verifyToken, saveOneSignalId)

/**
 * @swagger
 * /api/auth/profile:
 *   get:
 *     summary: Obtener el perfil del usuario autenticado
 *     tags: [Auth]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Perfil del usuario obtenido correctamente
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/profile', verifyToken, getProfile)

export default router