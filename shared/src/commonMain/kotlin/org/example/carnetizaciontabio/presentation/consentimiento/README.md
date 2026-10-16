# consentimiento

Pantalla "Consentimiento": muestra el texto de la politica de tratamiento de datos de EMSERTABIO, su version, una casilla "He leido y acepto los terminos" y el boton "Aceptar y Continuar".

- Usa: `AceptarConsentimientoUseCase`.
- Backend: `POST /api/consentimientos/aceptar` (la version de la politica por defecto es 1.0) y `GET /api/consentimientos/mi-consentimiento`.
- Responde a la Ley 1581 de proteccion de datos personales: queda constancia de quien acepto y cuando.
