package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.utils.Constants
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var leagueRepository: LeagueRepository

    @Mock
    private lateinit var view: NextMatchContract.View

    private lateinit var leagueMock: League

    private lateinit var presenter: NextMatchPresenter<NextMatchContract.View>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = NextMatchPresenter(matchRepository, leagueRepository, TestContextProvider())
        presenter.onAttach(view)

        leagueMock = League(leagueId = Constants.LEAGUE_ID)
    }

    @Test
    fun shouldDisplayMatchListWhenGetDataSuccess(){
        val response: MutableList<Match> = mutableListOf()

        `when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            `when`(matchRepository.getNextMatch(Constants.LEAGUE_ID)).thenReturn(response)
        }

        presenter.getMatchList()
        verify(view).showLoading()
        verify(view).displayMatchList(response)
        verify(view).hideLoading()
    }

    @After
    fun tearDown(){
        presenter.onDetach()
    }
}