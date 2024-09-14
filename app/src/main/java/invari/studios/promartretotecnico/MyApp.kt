package invari.studios.promartretotecnico

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import invari.studios.promartretotecnico.base.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltAndroidApp
class MyApp : Application(){
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        preferencesManager = PreferencesManager(this)

        val accessToken = preferencesManager.getAccessToken()
        if (accessToken.isNullOrEmpty()) {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)

            CoroutineScope(Dispatchers.IO).launch {
                fetchAndActivateRemoteConfig(remoteConfig)
            }
        }
    }
    private suspend fun fetchAndActivateRemoteConfig(remoteConfig: FirebaseRemoteConfig) {
        try {
            val fetchResult = remoteConfig.fetchAndActivate().await()
            if (fetchResult) {
                val accessToken = remoteConfig.getString("access_token")
                PreferencesManager(this@MyApp).saveAccessToken(accessToken)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}