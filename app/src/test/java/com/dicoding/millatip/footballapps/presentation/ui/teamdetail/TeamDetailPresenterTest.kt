package com.dicoding.millatip.footballapps.presentation.ui.teamdetail

import com.dicoding.millatip.footballapps.data.model.TeamResponse
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
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

class TeamDetailPresenterTest {

    @Mock
    private lateinit var teamRepository: TeamRepository

    @Mock
    private lateinit var view: TeamDetailContract.View

    @Mock
    private lateinit var teamResponse: Response<TeamResponse>

    private lateinit var presenter: TeamDetailPresenter<TeamDetailContract.View>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = TeamDetailPresenter(teamRepository, TestContextProvider())
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayTeamListWhenGetDataSuccess() {
        val teamId = "133604"

        runBlocking {
            `when`(teamRepository.getTeamDetail(teamId)).thenReturn(teamResponse)
            `when`(teamResponse.isSuccessful).thenReturn(true)

            presenter.getTeamDetail(teamId)

            verify(view).showLoading()
            teamResponse.body()?.teams?.get(0)?.let { Mockito.verify(view).displayTeam(it) }
            verify(view).hideLoading()
        }
    }

    @Test
    fun shouldDisplayErrorWhenGetDataFailed() {
        val teamId = "1"
        runBlocking {
            Mockito.`when`(teamRepository.getTeamDetail(teamId)).thenReturn(teamResponse)
            Mockito.`when`(teamResponse.isSuccessful).thenReturn(false)

            presenter.getTeamDetail(teamId)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).displayErrorMessage(ArgumentMatchers.anyString())
        }
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }
}
