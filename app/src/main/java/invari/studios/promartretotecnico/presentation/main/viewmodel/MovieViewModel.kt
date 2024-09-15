package invari.studios.promartretotecnico.presentation.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.model.MovieDB
import invari.studios.promartretotecnico.data.model.ResultMovieResponse
import invari.studios.promartretotecnico.domain.MoviesDbUseCase
import invari.studios.promartretotecnico.domain.PopularMoviesFlowUseCase
import invari.studios.promartretotecnico.domain.PopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val moviesDbUseCase: MoviesDbUseCase,
    private val popularMoviesFlowUseCase: PopularMoviesFlowUseCase
) : ViewModel() {

    //Use LiveData
    private val _popularMovieResult = MutableLiveData<ServiceResult<ResultMovieResponse>?>()
    val popularMovieResult: MutableLiveData<ServiceResult<ResultMovieResponse>?> get() = _popularMovieResult

    //Use Flows
    private val _popularMovieFlowResult = MutableStateFlow<ServiceResult<ResultMovieResponse>?>(null)
    val popularMovieFlowResult: StateFlow<ServiceResult<ResultMovieResponse>?> get() = _popularMovieFlowResult

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
    fun getPopularMoviesWithFlows() {
        viewModelScope.launch {
            if (_popularMovieFlowResult.value !is ServiceResult.Loading) {
                popularMoviesFlowUseCase.execute()
                    .onStart {
                    }
                    .catch {
                        e -> _popularMovieFlowResult.value = ServiceResult.Error("Error: ${e.message}")
                    }
                    .collect {
                        result -> _popularMovieFlowResult.value = result
                    }
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