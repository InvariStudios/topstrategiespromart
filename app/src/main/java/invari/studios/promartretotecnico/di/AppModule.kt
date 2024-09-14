package invari.studios.promartretotecnico.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import invari.studios.promartretotecnico.base.JwtInterceptor
import invari.studios.promartretotecnico.base.PreferencesManager
import invari.studios.promartretotecnico.data.datasources.LoginRepositoryImpl
import invari.studios.promartretotecnico.data.repository.LoginRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [NetworkModule::class])
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideJwtInterceptor(preferencesManager: PreferencesManager): JwtInterceptor {
        return JwtInterceptor(preferencesManager)
    }
    @Provides
    @Singleton
    fun provideLoginRepository(remoteLoginRepository: LoginRepositoryImpl): LoginRepository {
        return remoteLoginRepository
    }
}