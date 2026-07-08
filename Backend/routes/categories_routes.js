import express from 'express'
import { createCategory, getCategories, deleteCategory, getCategoriesByMonth } from '../controllers/categories_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

/**
 * @swagger
 * tags:
 *   name: Categorías
 *   description: Endpoints de gestión de categorías
 */

/**
 * @swagger
 * /api/categorias:
 *   post:
 *     summary: Crear una categoría
 *     tags: [Categorías]
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
 *               - icono
 *             properties:
 *               nombre:
 *                 type: string
 *                 example: Comida
 *               icono:
 *                 type: string
 *                 example: 🍔
 *     responses:
 *       201:
 *         description: Categoría creada exitosamente
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.post('/', verifyToken, createCategory)

/**
 * @swagger
 * /api/categorias:
 *   get:
 *     summary: Obtener todas las categorías del usuario
 *     tags: [Categorías]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Lista de categorías
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/', verifyToken, getCategories)


/**
 * @swagger
 * /api/categorias/{id}:
 *   delete:
 *     summary: Eliminar una categoría
 *     tags: [Categorías]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *         description: ID de la categoría
 *     responses:
 *       200:
 *         description: Categoría eliminada
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.delete('/:id', verifyToken, deleteCategory)

/**
 * @swagger
 * /api/categorias/month:
 *   get:
 *     summary: Obtener categorías del usuario por mes y año
 *     tags: [Categorías]
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
 *         description: Lista de categorías correspondientes al mes y año indicados
 *       401:
 *         description: Token requerido o inválido
 *       500:
 *         description: Error del servidor
 */
router.get('/month', verifyToken, getCategoriesByMonth)

export default router