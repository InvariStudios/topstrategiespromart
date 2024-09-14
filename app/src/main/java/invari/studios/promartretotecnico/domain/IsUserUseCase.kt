package invari.studios.promartretotecnico.domain

import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Inject

class IsUserUseCase @Inject constructor(
    private val repository: LoginRepository
)
{
    suspend fun execute(): ServiceResult<Boolean> {
        return repository.isUser()
    }
}