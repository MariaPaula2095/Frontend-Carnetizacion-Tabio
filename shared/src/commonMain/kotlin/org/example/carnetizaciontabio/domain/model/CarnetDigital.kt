package org.example.carnetizaciontabio.domain.model

/*
 Carnet digital del trabajador tal como lo usan las pantallas.
 Versión limpia de CarnetDigitalDto: sin JSON y con menos nulos.
 */
data class CarnetDigital(
    val id: Long,
    val trabajadorId: String,
    /* Texto que contiene el QR; la pantalla lo dibuja como imagen. */
    val codigoQr: String,
    val fechaExpedicion: String?,
    val fechaVencimiento: String?,
    val estado: EstadoCarnet
)