package com.example.helloandroidagain.data.repository.remote

import com.example.helloandroidagain.BuildConfig
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.mapper.TournamentLogoMapper.Companion.JSON_RESULT_PATH
import com.example.helloandroidagain.data.mapper.TournamentLogoMapper.Companion.mapJsonObjectToTournamentLogo
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type

const val TOURNAMENT_LOGO_PER_PAGE = 5

interface ImageRemoteApi {
    @GET("/search/photos?query=tennis&per_page=$TOURNAMENT_LOGO_PER_PAGE&orientation=landscape")
    suspend fun searchLogoSuspend(@Query("page") page: Int): List<TournamentLogo>
}

object ImageRetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.UNSPLASH_BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .addConverterFactory(object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *> {
                    return TournamentLogoConverter(Gson())
                }
            })
            .build()
    }
}

private val authInterceptor = Interceptor { chain ->
    val originalRequest: Request = chain.request()
    val modifiedRequest: Request = originalRequest.newBuilder()
        .header("Authorization", "Client-ID ${BuildConfig.UNSPLASH_CLIENT_ID}")
        .build()
    chain.proceed(modifiedRequest)
}

class TournamentLogoConverter(private val gson: Gson) :
    Converter<ResponseBody, List<TournamentLogo>> {
    override fun convert(value: ResponseBody): List<TournamentLogo> {
        val jsonObject = gson.fromJson(value.string(), JsonObject::class.java)
        val result = mutableListOf<TournamentLogo>()
        jsonObject.getAsJsonArray(JSON_RESULT_PATH).forEach { jsonLogo ->
            result.add(mapJsonObjectToTournamentLogo(jsonLogo.asJsonObject))
        }
        return result.toList()
    }
}