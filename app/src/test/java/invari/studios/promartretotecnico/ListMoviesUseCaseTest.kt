package invari.studios.promartretotecnico


import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.PopularMovie
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.data.repository.MovieRepository
import invari.studios.promartretotecnico.domain.PopularMoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListMoviesUseCaseTest {

    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var popularMoviesUseCase: PopularMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        popularMoviesUseCase = PopularMoviesUseCase(movieRepository)
    }

    @Test
    fun testInvoke() = runBlocking {
        val mockPopularMovies = listOf(
            PopularMovie(adult = true, backdropPath = "", genreIds = listOf(1,2), id = 1, originalLanguage = "", originalTitle = "", overview = "", popularity = 1.0, posterPath = "", releaseDate = "", title = "", video = false, voteCount = 10, voteAverage = 1.0)
        )
        val mockResultMovieResponse = ResultMovieResponse(
            page = 1,
            results = mockPopularMovies,
            totalPages = 1,
            totalResults = 1
        )
        val mockResult = ServiceResult.Success(mockResultMovieResponse)

        coEvery { movieRepository.getPopularMovies() } returns mockResult
        val result = popularMoviesUseCase.execute()

        if (result is ServiceResult.Success) {
            assert(result.data.results.isNotEmpty())
        }
    }
}