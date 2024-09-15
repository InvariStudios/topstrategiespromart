package invari.studios.promartretotecnico.data.datasources

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.api.MovieApi
import invari.studios.promartretotecnico.data.database.MovieDao
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.data.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getPopularMovies(): ServiceResult<ResultMovieResponse> {
        return try {
            val response = movieApi.getPopularList()
            ServiceResult.Success(response)
        } catch (e: Exception) {
            ServiceResult.Error(e.message!!)
        }
    }

    override suspend fun getPopularMoviesAndSave() {
        val response = movieApi.getPopularList()
        val movies = response.results.map { movieResponse ->
            MovieDB(
                id = movieResponse.id,
                adult = movieResponse.adult,
                title = movieResponse.title,
                popularity = movieResponse.popularity,
                posterPath = movieResponse.posterPath,
                backdropPath = movieResponse.backdropPath,
                releaseDate = movieResponse.releaseDate
            )
        }
        movieDao.insertMovies(movies)
    }

    override suspend fun getMoviesFromDb(query:String): ServiceResult<List<MovieDB>> {
        return ServiceResult.Success(movieDao.searchMovies(query))
    }
}