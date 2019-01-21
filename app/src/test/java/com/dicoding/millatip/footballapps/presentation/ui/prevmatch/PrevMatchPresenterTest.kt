package com.dicoding.millatip.footballapps.presentation.ui.prevmatch

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.utils.Constants
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

class PrevMatchPresenterTest {

    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var leagueRepository: LeagueRepository

    @Mock
    private lateinit var view: PrevMatchContract.View

    @Mock
    private lateinit var matchResponse: Response<MatchResponse>

    private lateinit var leagueMock: League

    private lateinit var presenter: PrevMatchPresenter<PrevMatchContract.View>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = PrevMatchPresenter(matchRepository, leagueRepository, TestContextProvider())
        presenter.onAttach(view)

        leagueMock = League(leagueId = Constants.LEAGUE_ID)
    }

    @Test
    fun shouldDisplayMatchListWhenGetDataSuccess() {

        Mockito.`when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            `when`(matchRepository.getPreviousMatch(Constants.LEAGUE_ID)).thenReturn(matchResponse)
            `when`(matchResponse.isSuccessful).thenReturn(true)
            `when`(matchResponse.code()).thenReturn(200)

            presenter.getMatchList()

            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayMatchList(matchResponse.body()?.events ?: mutableListOf())
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun shouldDisplayErrorWhenDetDataFailed() {
        `when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            `when`(matchRepository.getPreviousMatch(Constants.LEAGUE_ID)).thenReturn(matchResponse)
            `when`(matchResponse.isSuccessful).thenReturn(false)

            presenter.getMatchList()

            verify(view).showLoading()
            verify(view).hideLoading()
            verify(view).displayErrorMessage(ArgumentMatchers.anyString())
        }
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }
}
