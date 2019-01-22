package com.dicoding.millatip.footballapps.presentation.ui.favoriteteam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.FavoriteTeam
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailActivity
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class FavoriteTeamFragment : Fragment(), FavoriteTeamContract.View {

    val presenter: FavoriteTeamPresenter<FavoriteTeamContract.View> by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
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
            presenter.getFavoriteTeamList()
        }
    }

    override fun showLoading() {
        pbFavoriteTeam.show()
    }

    override fun hideLoading() {
        pbFavoriteTeam.hide()
    }

    override fun displayFavoriteTeamList(teams: List<FavoriteTeam>) {
        rvTeamFavorite.adapter = FavoriteTeamAdapter(requireContext(), teams) {
            startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to it.teamId)
        }
        rvTeamFavorite.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteTeamList()
    }

    override fun onDestroyView() {
        onDetachView()
        super.onDestroyView()
    }

    override fun displayErrorMessage(message: String) {
        pbFavoriteTeam.snackbar(message)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

}
