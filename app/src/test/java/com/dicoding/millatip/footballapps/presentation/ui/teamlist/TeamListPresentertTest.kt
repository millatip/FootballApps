package com.dicoding.millatip.footballapps.presentation.ui.teamlist

import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.TeamResponse
import com.dicoding.millatip.footballapps.data.repository.league.LeagueRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
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

class TeamListPresentertTest {

    @Mock
    private lateinit var teamRepository: TeamRepository

    @Mock
    private lateinit var leagueRepository: LeagueRepository

    @Mock
    private lateinit var view: TeamListContract.View

    @Mock
    private lateinit var teamResponse: Response<TeamResponse>

    private lateinit var leagueMock: League

    private lateinit var presenter: TeamListPresenter<TeamListContract.View>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = TeamListPresenter(leagueRepository, teamRepository, TestContextProvider())
        presenter.onAttach(view)

        leagueMock = League(leagueId = Constants.LEAGUE_ID)
    }

    @Test
    fun shouldDisplayTeamListWhenGetDataSuccess(){
        Mockito.`when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            `when`(teamRepository.getTeamList(Constants.LEAGUE_ID)).thenReturn(teamResponse)
            `when`(teamResponse.isSuccessful).thenReturn(true)
            `when`(teamResponse.code()).thenReturn(200)

            presenter.getTeamList()

            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayTeamList(teamResponse.body()?.teams ?: mutableListOf())
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun shouldDisplayErrorWhenGetDataFailed(){
        `when`(view.selectedLeague).thenReturn(leagueMock)

        runBlocking {
            `when`(teamRepository.getTeamList(Constants.LEAGUE_ID)).thenReturn(teamResponse)
            `when`(teamResponse.isSuccessful).thenReturn(false)

            presenter.getTeamList()

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
