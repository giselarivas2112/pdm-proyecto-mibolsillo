import swaggerJsdoc from 'swagger-jsdoc'

const options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'MiBolsillo API',
      version: '1.0.0',
      description: 'API REST para la app de gestión de finanzas personales MiBolsillo'
    },
    tags: [
      { name: 'Auth', description: 'Endpoints de autenticación' },
      { name: 'Categorías', description: 'Endpoints de gestión de categorías' },
      { name: 'Gastos', description: 'Endpoints de gestión de gastos' },
      { name: 'Presupuestos', description: 'Endpoints de gestión de presupuestos' },
      { name: 'Pagos Fijos', description: 'Endpoints de gestión de pagos fijos' },
      { name: 'Estadísticas', description: 'Endpoints de estadísticas y reportes' }
    ],
    servers: [
      {
        url: 'https://mibolsillo-api-t96z.onrender.com',
        description: 'Servidor de producción'
      },
      {
        url: 'http://localhost:3000',
        description: 'Servidor local'
      }
    ],
    components: {
      securitySchemes: {
        bearerAuth: {
          type: 'http',
          scheme: 'bearer',
          bearerFormat: 'JWT'
        }
      }
    }
  },
  apis: ['./routes/*.js']
}

export default swaggerJsdoc(options)