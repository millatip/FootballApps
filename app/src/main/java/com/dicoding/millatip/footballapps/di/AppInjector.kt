package com.dicoding.millatip.footballapps.di

import com.dicoding.millatip.footballapps.data.network.NetworkService
import com.dicoding.millatip.footballapps.data.repository.league.LeagueDataStore
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchDataStore
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchPresenter
import com.dicoding.millatip.footballapps.presentation.ui.splash.SplashContract
import com.dicoding.millatip.footballapps.presentation.ui.splash.SplashPresenter
import com.dicoding.millatip.footballapps.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private fun createRetrofit(): Retrofit{
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{ chain ->
            val request = chain.request()
                .newBuilder()
                .method(chain.request().method(), chain.request().body())
                .build()
            chain.proceed(request)
        }
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    val builder =  Retrofit.Builder()
    builder.client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())

    return builder.build()
}

private fun createNetworkService(retrofit: Retrofit): NetworkService{
    return retrofit.create(NetworkService::class.java)
}

val networkModule = module {
    single { createRetrofit() }
    single { createNetworkService(get()) }
}

val appModule = module {
    factory<MatchRepository>{MatchDataStore(get(), androidContext())}
    factory<LeagueRepository>{LeagueDataStore(get(), androidContext())}

    factory { SplashPresenter<SplashContract.View>(get()) }
    factory { NextMatchPresenter<NextMatchContract.View>(get(), get()) }
}
