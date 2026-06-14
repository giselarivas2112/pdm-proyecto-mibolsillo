import express from 'express'
import { createCategory, getCategories, updateCategory, deleteCategory } from '../controllers/categories_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.post('/', verifyToken, createCategory)
router.get('/', verifyToken, getCategories)
router.put('/:id', verifyToken, updateCategory)
router.delete('/:id', verifyToken, deleteCategory)

export default router