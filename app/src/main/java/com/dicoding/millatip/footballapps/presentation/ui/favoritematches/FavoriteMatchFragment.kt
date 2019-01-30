package com.dicoding.millatip.footballapps.presentation.ui.favoritematches


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailActivity
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class FavoriteMatchFragment : Fragment(),
    FavoriteMatchContract.View {

    private val presenter: FavoriteMatchPresenter<FavoriteMatchContract.View> by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onAttachView()

        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), android.R.color.holo_blue_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
            ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        )

        swipeRefreshLayout.onRefresh {
            EspressoIdlingResource.increment()
            presenter.getFavoriteMatchList()
        }

    }


    override fun showLoading() {
        pbFavoriteMatch.show()
    }

    override fun hideLoading() {
        pbFavoriteMatch.hide()
    }

    override fun displayFavoriteMatchList(matchList: List<FavoriteMatch>) {

        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }

        swipeRefreshLayout.isRefreshing = false

        val adapter =
            FavoriteMatchAdapter(matchList) {
                startActivity<MatchDetailActivity>(
                    MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                    MatchDetailActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                    MatchDetailActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
                )
            }

        rvFavoriteMatch.adapter = adapter
        rvFavoriteMatch.layoutManager = LinearLayoutManager(context)
    }

    override fun displayErrorMessage(message: String) {
        rvFavoriteMatch.snackbar(message)
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteMatchList()
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroyView() {
        onDetachView()
        super.onDestroyView()
    }
}
