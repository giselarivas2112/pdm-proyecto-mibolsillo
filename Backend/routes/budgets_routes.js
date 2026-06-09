import express from 'express'
import { createBudget, getBudgets, updateBudget, deleteBudget } from '../controllers/budgets_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.post('/', verifyToken, createBudget)
router.get('/', verifyToken, getBudgets)
router.put('/:id', verifyToken, updateBudget)
router.delete('/:id', verifyToken, deleteBudget)

export default router