package invari.studios.promartretotecnico.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import invari.studios.promartretotecnico.data.repository.MovieRepository
import javax.inject.Inject

class MovieWorkerFactory @Inject constructor(
    private val movieRepository: MovieRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            MovieWorker::class.java.name -> MovieWorker(appContext, workerParameters, movieRepository)
            else -> null
        }
    }
}

