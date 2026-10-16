package org.example.carnetizaciontabio.core

// Guarda el token de sesion (JWT) que devuelve el login, para adjuntarlo en cada peticion.
interface TokenStorage {
    fun getToken(): String?
    fun saveToken(token: String)
    fun clearToken()
}

// Version simple: el token vive solo mientras la app esta abierta.
// Mas adelante se reemplaza por una que lo guarde en el celular.
class InMemoryTokenStorage : TokenStorage {
    private var token: String? = null

    override fun getToken(): String? = token
    override fun saveToken(token: String) { this.token = token }
    override fun clearToken() { token = null }
}