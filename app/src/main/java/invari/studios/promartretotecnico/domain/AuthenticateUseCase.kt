package invari.studios.promartretotecnico.domain

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(): ServiceResult<Boolean> {
        return loginRepository.authenticate()
    }
}