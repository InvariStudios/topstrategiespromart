package invari.studios.promartretotecnico.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import invari.studios.promartretotecnico.domain.IsUserUseCase
import invari.studios.promartretotecnico.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.domain.AuthenticateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val isUserUseCase: IsUserUseCase,
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<ServiceResult<Boolean>>()
    val loginResult: LiveData<ServiceResult<Boolean>> get() = _loginResult

    private val _isUser = MutableLiveData<ServiceResult<Boolean>>()
    val isUser: LiveData<ServiceResult<Boolean>> get() = _isUser

    private val _authenticate = MutableLiveData<ServiceResult<Boolean>>()
    val authenticate: LiveData<ServiceResult<Boolean>> get() = _authenticate

    fun signInWithGoogle(idToken:String) {
        viewModelScope.launch {
            _loginResult.postValue(ServiceResult.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    loginUseCase.execute(idToken)
                }
                _loginResult.postValue(result)
            } catch (e: Exception) {
                _loginResult.postValue(ServiceResult.Error("Error google login service: ${e.message}"))
            }
        }
    }

    fun isLogin(){
        viewModelScope.launch {
            _isUser.postValue(ServiceResult.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    isUserUseCase.execute()
                }
                _isUser.postValue(result)
            } catch (e: Exception) {
                _isUser.postValue(ServiceResult.Error("Error isLogin: ${e.message}"))
            }
        }
    }
    fun authenticate(){
        viewModelScope.launch {
            _authenticate.postValue(ServiceResult.Loading)
            try {
                val result = withContext(Dispatchers.IO) {
                    authenticateUseCase.execute()
                }
                _authenticate.postValue(result)
            } catch (e: Exception) {
                _authenticate.postValue(ServiceResult.Error("Error authenticate: ${e.message}"))
            }
        }
    }
}