package invari.studios.promartretotecnico.data.datasources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.data.api.MovieApi
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val authenticateApi: MovieApi
) : LoginRepository {
    override suspend fun signInWithGoogle(idToken: String) : ServiceResult<Boolean> {
        return suspendCoroutine { continuation ->
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        try {
                            //Podria registrarlo a db
                            val user = task.result?.user
                            continuation.resume(
                                ServiceResult.Success(true)
                            )
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    } else {
                        continuation.resumeWithException(Exception("Authentication failed"))
                    }
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    override suspend fun isUser(): ServiceResult<Boolean> {
        return suspendCoroutine { continuation ->
            try {
                val uid = auth.currentUser?.uid
                if(uid!=null){
                    continuation.resume(
                        ServiceResult.Success(true)
                    )
                }
                else{
                    continuation.resume(
                        ServiceResult.Success(false)
                    )
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }
    override suspend fun authenticate(): ServiceResult<Boolean> {
        return try {
            val response = authenticateApi.getAuthentication()
            ServiceResult.Success(response.success)
        } catch (e: Exception) {
            ServiceResult.Error(e.message!!)
        }
    }
}