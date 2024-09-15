package invari.studios.promartretotecnico

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import invari.studios.promartretotecnico.base.PreferencesManager
import invari.studios.promartretotecnico.workers.MovieWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.work.*
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        setupWorker()
        val accessToken = PreferencesManager(this).getAccessToken()
        if (accessToken.isNullOrEmpty()) {
            val remoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
            remoteConfig.setConfigSettingsAsync(configSettings)

            CoroutineScope(Dispatchers.IO).launch {
                getAccessToken(remoteConfig)
            }
        }
    }

    private fun setupWorker() {
        val workRequest = OneTimeWorkRequestBuilder<MovieWorker>().build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "MovieWorker",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    private suspend fun getAccessToken(remoteConfig: FirebaseRemoteConfig) {
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

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
