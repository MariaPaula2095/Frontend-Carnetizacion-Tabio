package org.example.carnetizaciontabio.presentation.theme

import androidx.compose.ui.graphics.Color

// Paleta de EMSERTABIO, tomada de la pagina oficial. En Color() el 0xFF inicial es la opacidad (100%).

// Colores de marca
val AzulHeader = Color(0xFF0943B5)    // barra superior y encabezados
val AzulAccion = Color(0xFF004884)    // selector, encabezado de tabla, item activo del menu
val CelesteAccion = Color(0xFF00A4E0) // botones y tarjetas secundarias
val VerdeAccion = Color(0xFF28A745)   // botones de accion principal (Ingresar, Generar, Aceptar)
val AzulLink = Color(0xFF30A2FF)      // titulos de seccion y enlaces

// Neutros
val Blanco = Color(0xFFFFFFFF)        // fondo general
val GrisNavbar = Color(0xFFF6F8F9)    // fondo del menu de navegacion y paneles claros
val TextoPrincipal = Color(0xFF4C4C4C)
val TextoSecundario = Color(0xFF6C757D) // subtitulos y titulo del login
val BordeGris = Color(0xFFE2E2E2)       // bordes de campos y tarjetas
val BordeAcento = Color(0xFF00CBF7)     // linea celeste bajo el menu

// Estados del trabajador (no estaban en la lista de la pagina: salen de los mockups de Figma)
val EstadoAutorizado = VerdeAccion
val EstadoPendiente = Color(0xFFF5A623)
val EstadoInactivo = Color(0xFF9AA0A6)
val EstadoRechazado = Color(0xFFD93025)