package com.dicoding.millatip.footballapps.presentation.ui.prevmatch

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.utils.Constants
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PrevMatchPresenterTest {

    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var leagueRepository: LeagueRepository

    @Mock
    private lateinit var view: PrevMatchContract.View

    private lateinit var leagueMock: League

    private lateinit var presenter: PrevMatchPresenter<PrevMatchContract.View>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = PrevMatchPresenter(matchRepository, leagueRepository, TestContextProvider())
        presenter.onAttach(view)

        leagueMock = League(leagueId = Constants.LEAGUE_ID)
    }

    @Test
    fun shouldDisplayMatchListWhenGetDataSuccess(){
        val response: MutableList<Match> = mutableListOf()

        Mockito.`when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            Mockito.`when`(matchRepository.getPreviousMatch(Constants.LEAGUE_ID)).thenReturn(response)
        }

        presenter.getMatchList()
        Mockito.verify(view).showLoading()
        Mockito.verify(view).displayMatchList(response)
        Mockito.verify(view).hideLoading()
    }
}