package com.example.helloandroidagain.service

import com.example.helloandroidagain.BuildConfig
import com.example.helloandroidagain.model.TournamentLogo
import com.example.helloandroidagain.model.mapper.TournamentLogoMapper.Companion.JSON_RESULT_PATH
import com.example.helloandroidagain.model.mapper.TournamentLogoMapper.Companion.mapJsonObjectToTournamentLogo
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type

const val TOURNAMENT_LOGO_PER_PAGE = 5

interface ImageRemoteService {
    @GET("/search/photos?query=tennis&per_page=$TOURNAMENT_LOGO_PER_PAGE&orientation=landscape")
    fun searchLogo(@Query("page") page: Int): Single<List<TournamentLogo>>
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.unsplash.com/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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