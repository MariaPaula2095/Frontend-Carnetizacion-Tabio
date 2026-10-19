package org.example.carnetizaciontabio.core

/*
  Transforma el contenido de un ApiResult solo si fue exitoso.
  Si fue Error, lo deja pasar tal cual. Evita repetir el mismo `when` en cada repositorio.
 */
inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success -> ApiResult.Success(transform(data))
    is ApiResult.Error -> this
}