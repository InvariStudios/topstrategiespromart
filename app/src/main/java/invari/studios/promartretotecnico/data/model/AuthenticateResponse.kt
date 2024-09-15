package invari.studios.promartretotecnico.data.model

import com.google.gson.annotations.SerializedName

data class AuthenticateResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("success")
    val success: Boolean
)