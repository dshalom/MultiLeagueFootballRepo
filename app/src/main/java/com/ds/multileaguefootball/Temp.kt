package com.ds.multileaguefootball

import android.util.Log
import com.ds.multileaguefootball.data.model.Competitions
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

private const val TIME_OUT = 60_000

val ktorHttpClient = HttpClient(Android) {

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
        header("X-Auth-Token", "28e9f6765daa49f595cb6313e4a779d1")
    }
}

class UserApi(private val client: HttpClient) {
    suspend fun getUserKtor(
        userId: String
    ): Competitions = client.get("http://api.football-data.org/v2/competitions")

//    suspend fun saveUser(user: UserEntity) {
//        client.post<UserEntity>("$END_POINT_POST_USER_KTOR") {
//            body = user
//        }
//    }
}
