# login

Pantalla de inicio de sesion del Trabajador con cedula y PIN.

- Usa: `LoginTrabajadorUseCase`.
- Backend: `POST /api/trabajadores/login`, que devuelve un token JWT.
- Si el trabajador todavia no fue autorizado por un administrador (estado PENDIENTE o RECHAZADO), el backend responde con error: la pantalla debe mostrar un mensaje claro.
- Al entrar bien, se guarda el token (ver `core`) y se navega a `home`.
