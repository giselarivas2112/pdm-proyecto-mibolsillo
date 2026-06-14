import express from 'express'
import { createExpense, getExpenses, updateExpense, deleteExpense } from '../controllers/expenses_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.post('/', verifyToken, createExpense)
router.get('/', verifyToken, getExpenses)
router.put('/:id', verifyToken, updateExpense)
router.delete('/:id', verifyToken, deleteExpense)

export default router