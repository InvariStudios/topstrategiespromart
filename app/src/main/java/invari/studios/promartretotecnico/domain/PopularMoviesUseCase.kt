package invari.studios.promartretotecnico.domain

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.data.repository.LoginRepository
import invari.studios.promartretotecnico.data.repository.MovieRepository
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun execute(): ServiceResult<ResultMovieResponse> {
        return movieRepository.getPopularMovies()
    }
}