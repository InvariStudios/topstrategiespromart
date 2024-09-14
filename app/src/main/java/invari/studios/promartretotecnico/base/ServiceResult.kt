package invari.studios.promartretotecnico.base

sealed class ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>()
    data class Error(val message: String) : ServiceResult<Nothing>()
    object Loading : ServiceResult<Nothing>()
}