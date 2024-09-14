package invari.studios.promartretotecnico.base

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import invari.studios.promartretotecnico.databinding.LoadingBinding
import invari.studios.promartretotecnico.databinding.ModalErrorBinding

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var binding : LoadingBinding
    private var progressDialog: LoadingDialog = LoadingDialog()
    private var progressIniciado = false
    private var statusOnline = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showProgressDialog() {
        if (!progressIniciado) {
            progressDialog.isCancelable = false
            progressIniciado = true
            progressDialog.show(supportFragmentManager, "")
        }
    }

    fun hideProgressDialog() {
        if (progressIniciado) {
            progressDialog.dismiss()
            progressIniciado = false
        }
    }
    fun showError(msj:String){
        val binding = ModalErrorBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        dialog.show()
        binding.etmodalerrormsj.text = msj
        binding.btnmodalerroraceptar.setOnClickListener{
            dialog.dismiss()
        }
    }
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            return when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    statusOnline = true
                    true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    statusOnline = true
                    true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    statusOnline = true
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }
}