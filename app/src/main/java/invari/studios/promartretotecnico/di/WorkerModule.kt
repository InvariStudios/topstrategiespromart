package invari.studios.promartretotecnico.di

import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import invari.studios.promartretotecnico.data.repository.MovieRepository
import invari.studios.promartretotecnico.workers.MovieWorkerFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    @Singleton
    fun provideWorkerFactory(
        movieRepository: MovieRepository
    ): WorkerFactory {
        return MovieWorkerFactory(movieRepository)
    }
}

