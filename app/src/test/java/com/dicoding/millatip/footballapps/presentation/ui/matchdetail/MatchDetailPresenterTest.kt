package com.dicoding.millatip.footballapps.presentation.ui.matchdetail

import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.data.model.MatchResponse
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.data.repository.team.TeamRepository
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MatchDetailPresenterTest {
    @Mock
    private lateinit var matchRepository: MatchRepository
    @Mock
    private lateinit var teamRepository: TeamRepository
    @Mock
    private lateinit var view: MatchDetailContract.View
    @Mock
    private lateinit var matchResponse: Response<MatchResponse>

    private lateinit var presenter: MatchDetailPresenter<MatchDetailContract.View>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter = MatchDetailPresenter(matchRepository, teamRepository, TestContextProvider())
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayMatchListWhenGetDataSuccess() {
        val matchId = "3242"

        runBlocking {
            `when`(matchRepository.getMatchDetail(matchId)).thenReturn(matchResponse)
            `when`(matchResponse.isSuccessful).thenReturn(true)

            `when`(matchRepository.isFavorite(matchId)).thenReturn(false)
            presenter.getMatchDetail(matchId)

            verify(view).showLoading()
            matchResponse.body()?.events?.get(0)?.let { verify(view).displayMatch(it, false) }
            verify(view).hideLoading()
        }
    }

    @Test
    fun shouldSaveFavoriteMatchData() {
        val match = Match(
            matchId = "441613", matchName = "Liverpool vs Swansea",
            league = "English Premier League", homeTeamId = "133602",
            homeTeamName = "Liverpool", awayTeamId = "133614",
            awayTeamName = "Swansea", matchDate = "29/12/14", matchTime = "20:00:00+00:00",
            homeScore = "4", awayScore = "1"
        )

        presenter.addToFavorite(match)
        verify(view).onAddToFavorite()

    }

    @Test
    fun shouldRemoveFavoriteMatchData() {
        val match = Match(
            matchId = "441613"
        )

        presenter.removeFromFavorite(match)
        verify(view).onRemoveFromFavorite()
    }

    @Test
    fun shouldDisplayError() {
        val matchId = "1"

        runBlocking {
            `when`(matchRepository.getMatchDetail(matchId)).thenReturn(matchResponse)
            `when`(matchResponse.isSuccessful).thenReturn(false)
            `when`(matchRepository.isFavorite(matchId)).thenReturn(false)

            presenter.getMatchDetail(matchId)
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
