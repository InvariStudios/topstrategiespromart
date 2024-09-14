package invari.studios.promartretotecnico.base

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("promartRefs", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("ACCESS_TOKEN", token)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString("ACCESS_TOKEN", null)
    }
}