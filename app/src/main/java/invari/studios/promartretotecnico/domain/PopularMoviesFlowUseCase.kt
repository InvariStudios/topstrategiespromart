package invari.studios.promartretotecnico.domain

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.data.repository.LoginRepository
import invari.studios.promartretotecnico.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularMoviesFlowUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun execute(): Flow<ServiceResult<ResultMovieResponse>> {
        return flow {
            emit(movieRepository.getPopularMovies())
        }
    }
}