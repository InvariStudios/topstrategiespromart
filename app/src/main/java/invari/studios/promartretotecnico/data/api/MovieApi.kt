package invari.studios.promartretotecnico.data.api

import invari.studios.promartretotecnico.data.model.AuthenticateResponse
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("authentication")
    suspend fun getAuthentication(

    ): AuthenticateResponse

    @GET("movie/popular")
    suspend fun getPopularList(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ResultMovieResponse


}