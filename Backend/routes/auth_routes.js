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

router.put('/onesignal', verifyToken, saveOneSignalId)

router.get('/profile', verifyToken, getProfile)

export default router