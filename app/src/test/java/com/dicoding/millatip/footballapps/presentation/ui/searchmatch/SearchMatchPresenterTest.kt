package com.dicoding.millatip.footballapps.presentation.ui.searchmatch

import com.dicoding.millatip.footballapps.data.model.MatchResponse
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SearchMatchPresenterTest {

    @Mock
    private lateinit var matchRepository: MatchRepository
    @Mock
    private lateinit var view: SearchMatchContract.View
    @Mock
    private lateinit var matchResponse: Response<MatchResponse>

    private lateinit var presenter: SearchMatchPresenter<SearchMatchContract.View>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = SearchMatchPresenter(matchRepository, TestContextProvider())
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayTeamListWhenSearchTeamSuccess() {
        val teamName = "Esbjerg"
        runBlocking {
            Mockito.`when`(matchRepository.getMatchSearchResult(teamName)).thenReturn(matchResponse)
            presenter.searchMatch(teamName)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).displayMatch(matchResponse.body()?.events ?: mutableListOf())
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun shouldDisplayMessageWhenTextEmpty() {
        val teamName = ""
        runBlocking {
            presenter.searchMatch(teamName)
            verify(view).displayErrorMessage(ArgumentMatchers.anyString())
        }
    }

    @Test
    fun shouldDisplayErrorWhenSearchTeamFailed() {
        val matchName = "1"
        runBlocking {
            Mockito.`when`(matchRepository.getMatchSearchResult(matchName)).thenReturn(matchResponse)
            presenter.searchMatch(matchName)
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
