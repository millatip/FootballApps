package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.utils.Constants
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {
    @Mock
    private lateinit var matchRepository: MatchRepository
    @Mock
    private lateinit var teamRepository: TeamRepository
    @Mock
    private lateinit var view: MatchDetailContract.View

    private lateinit var presenter: MatchDetailPresenter<MatchDetailContract.View>



    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = MatchDetailPresenter(matchRepository, teamRepository, TestContextProvider())
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayMatchListWhenGetDataSuccess(){
        val response = Match()

        runBlocking {
            Mockito.`when`(matchRepository.getMatchDetail(Constants.MATCH_ID))
                .thenReturn(response)
        }

        presenter.getMatchDetail(Constants.MATCH_ID)
        Mockito.verify(view).showLoading()
        Mockito.verify(view).displayMatch(response)
        Mockito.verify(view).hideLoading()



    }
}