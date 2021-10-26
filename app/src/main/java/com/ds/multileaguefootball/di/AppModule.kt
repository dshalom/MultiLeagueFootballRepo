package com.ds.multileaguefootball.di

import android.content.Context
import android.util.Log
import com.ds.multileaguefootball.BuildConfig
import com.ds.multileaguefootball.data.InMemoryCache
import com.ds.multileaguefootball.data.httpclient.ApiServiceImpl
import com.ds.multileaguefootball.data.repo.RepoImpl
import com.ds.multileaguefootball.domain.repo.Repo
import com.ds.multileaguefootball.domain.usecases.DataStoreManager
import com.ds.multileaguefootball.domain.usecases.DataStoreManagerImpl
import com.ds.multileaguefootball.domain.usecases.LeagueNavUseCase
import com.ds.multileaguefootball.domain.usecases.LeagueNavUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Singleton

private const val TIME_OUT = 60_000

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
                engine {
                    connectTimeout = TIME_OUT
                    socketTimeout = TIME_OUT
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("X-Auth-Token", BuildConfig.APP_KEY)
            }
        }
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "http://api.football-data.org/v2/"

    @Provides
    @Singleton
    fun provideApiService(httpClient: HttpClient, baseUrl: String): ApiServiceImpl {
        return ApiServiceImpl(httpClient, baseUrl)
    }

    @Provides
    @Singleton
    fun provideRepo(apiServiceImpl: ApiServiceImpl, inMemoryCache: InMemoryCache): Repo {
        return RepoImpl(apiServiceImpl, inMemoryCache)
    }

    @Provides
    @Singleton
    fun provideAvailableLeagueCodes(): List<Int> {
        return listOf(2002, 2003, 2014, 2015, 2016, 2017, 2019)
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager {
        return DataStoreManagerImpl(context = appContext)
    }

    @Provides
    @Singleton
    fun provideLeagueNavUseCase(dataStoreManager: DataStoreManager): LeagueNavUseCase {
        return LeagueNavUseCaseImpl(dataStoreManager)
    }
}
