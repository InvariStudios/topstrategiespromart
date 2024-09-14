package invari.studios.promartretotecnico.data.api

import invari.studios.promartretotecnico.data.model.AuthenticateResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthenticateApi {
    @GET("authentication")
    suspend fun getAuthentication(
        @Header("accept") accept: String = "application/json"
    ): AuthenticateResponse

}