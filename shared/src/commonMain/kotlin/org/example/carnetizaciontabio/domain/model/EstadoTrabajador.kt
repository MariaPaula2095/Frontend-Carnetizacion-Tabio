package org.example.carnetizaciontabio.domain.model

// Estado del trabajador dentro del proceso de autorizacion (igual que en el backend).
// PENDIENTE: se registro, falta que un administrador lo revise.
// AUTORIZADO: ya puede usar la app.
// INACTIVO: ya no trabaja en la empresa.
// RECHAZADO: el administrador no aprobo su registro.
enum class EstadoTrabajador {
    PENDIENTE,
    AUTORIZADO,
    INACTIVO,
    RECHAZADO
}
