package com.healyks.app.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.BuildConfig
import com.healyks.app.data.local.periods.CycleDao
import com.healyks.app.data.local.periods.CycleDatabase
import com.healyks.app.data.remote.AnalyzeApi
import com.healyks.app.data.remote.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        // Logging Interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        // HTTP Client with bearer token
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAnalyzeApi(retrofit: Retrofit): AnalyzeApi {
        return retrofit.create(AnalyzeApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences("user_details", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideCycleDatabase(@ApplicationContext context: Context): CycleDatabase {
        return CycleDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCycleDao(database: CycleDatabase): CycleDao {
        return database.cycleDao()
    }
}