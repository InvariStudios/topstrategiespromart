package invari.studios.promartretotecnico.data.repository

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.ResultMovieResponse


interface MovieRepository {
    suspend fun getPopularMovies() : ServiceResult<ResultMovieResponse>
    suspend fun getPopularMoviesAndSave()
    suspend fun getMoviesFromDb(query:String): ServiceResult<List<MovieDB>>

}