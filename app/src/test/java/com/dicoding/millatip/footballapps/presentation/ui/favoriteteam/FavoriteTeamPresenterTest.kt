package com.dicoding.millatip.footballapps.presentation.ui.favoriteteam

import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteTeamPresenterTest {

    @Mock
    private lateinit var teamRepository: TeamRepository

    @Mock
    private lateinit var view: FavoriteTeamContract.View

    @Mock
    private lateinit var team: List<FavoriteTeam>

    private lateinit var presenter: FavoriteTeamPresenter<FavoriteTeamContract.View>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = FavoriteTeamPresenter(teamRepository, TestContextProvider())
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayFavoriteMatchListWhenGetDataSuccess(){
        runBlocking {
            Mockito.`when`(teamRepository.getFavoriteTeamList()).thenReturn(team)

            presenter.getFavoriteTeamList()

            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayFavoriteTeamList(team)
            Mockito.verify(view).hideLoading()
        }
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }

}
