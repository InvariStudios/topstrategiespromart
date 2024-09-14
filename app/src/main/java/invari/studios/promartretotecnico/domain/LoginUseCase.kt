package invari.studios.promartretotecnico.domain

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(idToken:String): ServiceResult<Boolean> {
        return loginRepository.signInWithGoogle(idToken)
    }
}