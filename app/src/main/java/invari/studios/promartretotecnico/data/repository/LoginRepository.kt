package invari.studios.promartretotecnico.data.repository

import invari.studios.promartretotecnico.base.ServiceResult


interface LoginRepository {
    suspend fun signInWithGoogle(idToken:String): ServiceResult<Boolean>
    suspend fun isUser() : ServiceResult<Boolean>
    suspend fun authenticate() : ServiceResult<Boolean>
}