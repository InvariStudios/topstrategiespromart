package invari.studios.promartretotecnico.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import invari.studios.promartretotecnico.data.repository.MovieRepository

@HiltWorker
class MovieWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val movieRepository: MovieRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            movieRepository.getPopularMoviesAndSave()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
