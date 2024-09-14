package invari.studios.promartretotecnico.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import invari.studios.promartretotecnico.data.datasources.LoginRepositoryImpl
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(remoteLoginRepository: LoginRepositoryImpl): LoginRepository {
        return remoteLoginRepository
    }
}