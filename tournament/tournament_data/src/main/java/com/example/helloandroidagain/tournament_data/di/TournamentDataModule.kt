package com.example.helloandroidagain.tournament_data.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.helloandroidagain.tournament_data.db.LogoDao
import com.example.helloandroidagain.tournament_data.db.StorageDatabase
import com.example.helloandroidagain.tournament_data.db.TournamentDao
import com.example.helloandroidagain.tournament_data.mapper.RemoteImageResponseConverter
import com.example.helloandroidagain.tournament_data.repository.local.ExportRepositoryImpl
import com.example.helloandroidagain.tournament_data.repository.local.ImageCacheRepositoryImpl
import com.example.helloandroidagain.tournament_data.repository.local.TournamentRepositoryImpl
import com.example.helloandroidagain.tournament_data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.tournament_data.repository.remote.ImageRemoteRepositoryImpl
import com.example.helloandroidagain.tournament_domain.repository.ExportRepository
import com.example.helloandroidagain.tournament_domain.repository.ImageCacheRepository
import com.example.helloandroidagain.tournament_domain.repository.ImageRemoteRepository
import com.example.helloandroidagain.tournament_domain.repository.TournamentRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TournamentDataModule {

// ------------ Room: db + Daos ------------
    companion object {
        private const val STORAGE_DB = "application_storage_db"
    }

    @Provides
    @Singleton
    fun provideStorageDatabase(context: Context): StorageDatabase =
        Room.databaseBuilder(
            context,
            StorageDatabase::class.java,
            STORAGE_DB
        ).addMigrations(object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE logo ADD COLUMN thumbImage BLOB DEFAULT NULL")
            }
        }).build()

    @Provides
    fun provideTournamentDao(database: StorageDatabase): TournamentDao =
        database.tournamentDao()

    @Provides
    fun provideLogoDao(database: StorageDatabase): LogoDao =
        database.logoDao()

// ------------ ImageCacheRepository ------------
    @Singleton
    @Provides
    fun provideImageCacheRepository(logoDao: LogoDao): ImageCacheRepository =
        ImageCacheRepositoryImpl(logoDao)

// ------------ TournamentRepository ------------
    @Singleton
    @Provides
    fun provideTournamentRepository(
        tournamentDao: TournamentDao,
        logoDao: LogoDao,
        storageDatabase: StorageDatabase
    ): TournamentRepository = TournamentRepositoryImpl(tournamentDao, logoDao, storageDatabase)

// ------------ ExportRepository ------------
    @Singleton
    @Provides
    fun provideExportRepository(
        contentResolver: ContentResolver
    ): ExportRepository = ExportRepositoryImpl(contentResolver)

// ------------ ImageRemoteRepository ------------
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @Named("unsplash_client_id") clientId: String
    ): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Authorization", "Client-ID $clientId")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideImageRetrofitInstance(
        @Named("unsplash_base_url") baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *> {
                return RemoteImageResponseConverter(Gson())
            }
        })
        .build()

    @Singleton
    @Provides
    fun provideImageRemoteApi(imageRetrofitInstance: Retrofit): ImageRemoteApi {
        return imageRetrofitInstance.create(ImageRemoteApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImageRemoteRepository(imageRemoteApi: ImageRemoteApi): ImageRemoteRepository =
        ImageRemoteRepositoryImpl(imageRemoteApi)
}