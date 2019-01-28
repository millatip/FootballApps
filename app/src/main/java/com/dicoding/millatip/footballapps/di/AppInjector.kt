package com.dicoding.millatip.footballapps.di

import com.dicoding.millatip.footballapps.data.network.NetworkService
import com.dicoding.millatip.footballapps.data.repository.league.LeagueDataStore
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchDataStore
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.player.PlayerDataStore
import com.dicoding.millatip.footballapps.data.repository.player.PlayerRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamDataStore
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.presentation.ui.favoritematches.FavoriteMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.favoritematches.FavoriteMatchPresenter
import com.dicoding.millatip.footballapps.presentation.ui.favoriteteam.FavoriteTeamContract
import com.dicoding.millatip.footballapps.presentation.ui.favoriteteam.FavoriteTeamPresenter
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailContract
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailPresenter
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchPresenter
import com.dicoding.millatip.footballapps.presentation.ui.prevmatch.PrevMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.prevmatch.PrevMatchPresenter
import com.dicoding.millatip.footballapps.presentation.ui.searchmatch.SearchMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.searchmatch.SearchMatchPresenter
import com.dicoding.millatip.footballapps.presentation.ui.splash.SplashContract
import com.dicoding.millatip.footballapps.presentation.ui.splash.SplashPresenter
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailContract
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailPresenter
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player.TeamPlayerContract
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player.TeamPlayerPresenter
import com.dicoding.millatip.footballapps.presentation.ui.teamlist.TeamListContract
import com.dicoding.millatip.footballapps.presentation.ui.teamlist.TeamListPresenter
import com.dicoding.millatip.footballapps.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private fun createRetrofit(): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
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

    val builder = Retrofit.Builder()
    builder.client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())

    return builder.build()
}

private fun createNetworkService(retrofit: Retrofit): NetworkService {
    return retrofit.create(NetworkService::class.java)
}

val networkModule = module {
    single { createRetrofit() }
    single { createNetworkService(get()) }

}

val appModule = module {
    factory<MatchRepository> { MatchDataStore(get(), androidContext()) }
    factory<LeagueRepository> { LeagueDataStore(get(), androidContext()) }
    factory<TeamRepository> { TeamDataStore(get(), androidContext()) }
    factory<PlayerRepository> { PlayerDataStore(get()) }

    factory { SplashPresenter<SplashContract.View>(get()) }
    factory { NextMatchPresenter<NextMatchContract.View>(get(), get()) }
    factory { PrevMatchPresenter<PrevMatchContract.View>(get(), get()) }
    factory { MatchDetailPresenter<MatchDetailContract.View>(get(), get()) }
    factory { FavoriteMatchPresenter<FavoriteMatchContract.View>(get()) }
    factory { TeamListPresenter<TeamListContract.View>(get(), get()) }
    factory { TeamDetailPresenter<TeamDetailContract.View>(get()) }
    factory { TeamPlayerPresenter<TeamPlayerContract.View>(get()) }
    factory { FavoriteTeamPresenter<FavoriteTeamContract.View>(get()) }
    factory { SearchMatchPresenter<SearchMatchContract.View>(get()) }

}

