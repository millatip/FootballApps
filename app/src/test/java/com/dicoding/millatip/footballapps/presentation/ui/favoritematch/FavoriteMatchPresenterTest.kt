package com.dicoding.millatip.footballapps.presentation.ui.favoritematch

import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.ui.favoritematches.FavoriteMatchContract
import com.dicoding.millatip.footballapps.presentation.ui.favoritematches.FavoriteMatchPresenter
import com.dicoding.millatip.footballapps.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class FavoriteMatchPresenterTest {

    @Mock
    private lateinit var matchRepository: MatchRepository

    @Mock
    private lateinit var view: FavoriteMatchContract.View

    @Mock
    private lateinit var match: List<FavoriteMatch>

    private lateinit var presenter: FavoriteMatchPresenter<FavoriteMatchContract.View>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        presenter = FavoriteMatchPresenter(
            matchRepository,
            TestContextProvider()
        )
        presenter.onAttach(view)
    }

    @Test
    fun shouldDisplayFavoriteMatchListWhenGetDataSuccess(){
        runBlocking {
            `when`(matchRepository.getFavoriteMatches()).thenReturn(match)

            presenter.getFavoriteMatchList()

            verify(view).showLoading()
            verify(view).displayFavoriteMatchList(match)
            verify(view).hideLoading()
        }
    }

    @After
    fun tearDown() {
        presenter.onDetach()
    }

}
