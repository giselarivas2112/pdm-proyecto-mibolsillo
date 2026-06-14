import express from 'express'
import {
  createFixedPayment,
  getFixedPayments,
  updateFixedPayment,
  deleteFixedPayment,
  getProximosVencimientos
} from '../controllers/fixed_payments_controller.js'
import { verifyToken } from '../middlewares/auth_middleware.js'

const router = express.Router()

router.post('/', verifyToken, createFixedPayment)
router.get('/', verifyToken, getFixedPayments)
router.get('/proximos', verifyToken, getProximosVencimientos)
router.put('/:id', verifyToken, updateFixedPayment)
router.delete('/:id', verifyToken, deleteFixedPayment)

export default router