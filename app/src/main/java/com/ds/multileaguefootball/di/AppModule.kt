package com.ds.multileaguefootball.di

import android.util.Log
import com.ds.multileaguefootball.BuildConfig
import com.ds.multileaguefootball.data.httpclient.ApiService
import com.ds.multileaguefootball.data.repo.RepoImpl
import com.ds.multileaguefootball.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
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
    fun provideApiService(httpClient: HttpClient, baseUrl: String): ApiService {
        return ApiService(httpClient, baseUrl)
    }

    @Provides
    @Singleton
    fun provideRepo(apiService: ApiService): Repo {
        return RepoImpl(apiService)
    }
}