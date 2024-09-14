package invari.studios.promartretotecnico.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import invari.studios.promartretotecnico.R
import invari.studios.promartretotecnico.base.BaseActivity
import invari.studios.promartretotecnico.base.ServiceResult
import invari.studios.promartretotecnico.databinding.ActivityLoginBinding
import invari.studios.promartretotecnico.presentation.main.MainActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val googleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignIn.getClient(this, gso)
    }
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken
                viewModel.signInWithGoogle(idToken ?: "")
            } catch (e: ApiException) {
                //Error
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        viewModel.isLogin()
        binding.btnConfirmar.setOnClickListener{
            signInWithGoogle()
        }
    }
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun authenticate(){
        viewModel.authenticate()
    }
    private fun setObserver(){
        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is ServiceResult.Success -> {
                    hideProgressDialog()
                    authenticate()
                }
                is ServiceResult.Error -> {
                    hideProgressDialog()
                }
                ServiceResult.Loading -> {
                    showProgressDialog()
                }
                else -> {
                    hideProgressDialog()
                }
            }
        }
        viewModel.authenticate.observe(this) { result ->
            when (result) {
                is ServiceResult.Success -> {
                    goToMain()
                }
                is ServiceResult.Error -> {
                }
                ServiceResult.Loading -> {
                }
            }
        }
        viewModel.isUser.observe(this) { result ->
            when (result) {
                is ServiceResult.Success -> {
                    hideProgressDialog()
                    if(result.data)
                        authenticate()
                }
                is ServiceResult.Error -> {
                    hideProgressDialog()
                }
                ServiceResult.Loading -> {
                    showProgressDialog()
                }
            }
        }
    }
    private fun goToMain(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}