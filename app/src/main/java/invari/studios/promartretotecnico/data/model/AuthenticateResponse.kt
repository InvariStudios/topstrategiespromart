package invari.studios.promartretotecnico.data.model

data class AuthenticateResponse(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)