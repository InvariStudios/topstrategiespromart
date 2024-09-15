package invari.studios.promartretotecnico.presentation.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.domain.MoviesDbUseCase
import invari.studios.promartretotecnico.domain.PopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val moviesDbUseCase: MoviesDbUseCase
) : ViewModel() {

    private val _popularMovieResult = MutableLiveData<ServiceResult<ResultMovieResponse>?>()
    val popularMovieResult: MutableLiveData<ServiceResult<ResultMovieResponse>?> get() = _popularMovieResult

    private val _moviesDbResult = MutableLiveData<ServiceResult<List<MovieDB>>?>()
    val moviesDbResult: MutableLiveData<ServiceResult<List<MovieDB>>?> get() = _moviesDbResult

    fun getPopularMovies(){
        viewModelScope.launch {
            _popularMovieResult.postValue(ServiceResult.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    popularMoviesUseCase.execute()
                }
                _popularMovieResult.postValue(result)
            } catch (e: Exception) {
                _popularMovieResult.postValue(ServiceResult.Error("Error popular list movie: ${e.message}"))
            }
        }
    }
    fun getMoviesFromDb(query:String){
        viewModelScope.launch {
            _moviesDbResult.postValue(ServiceResult.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    moviesDbUseCase.execute(query)
                }
                _moviesDbResult.postValue(result)
            } catch (e: Exception) {
                _moviesDbResult.postValue(ServiceResult.Error("Error popular list movie: ${e.message}"))
            }
        }
    }
    fun cleanDb(){
        _moviesDbResult.postValue(null)
    }
    fun cleanRequest(){
        _popularMovieResult.postValue(null)
    }
}