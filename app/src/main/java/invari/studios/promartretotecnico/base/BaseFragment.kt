package invari.studios.promartretotecnico.base

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import invari.studios.promartretotecnico.databinding.ModalErrorBinding

abstract class BaseFragment : Fragment() {
    private var progressDialog: LoadingDialog? = null
    private var progressIniciado = false
    fun showProgressDialog() {
        if (isAdded && !requireActivity().isFinishing && !progressIniciado) {
            progressDialog = LoadingDialog()
            progressDialog?.isCancelable = false
            progressDialog?.show(parentFragmentManager, "LoadingDialog")
            progressIniciado = true
        }
    }

    fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressIniciado = false
    }
    fun showErrorMessague(msj:String){
        val binding = ModalErrorBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
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
                    true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    true
                }
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
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