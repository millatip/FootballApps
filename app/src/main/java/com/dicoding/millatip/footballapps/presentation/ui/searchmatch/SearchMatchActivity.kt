package com.dicoding.millatip.footballapps.presentation.ui.searchmatch

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Match
import com.dicoding.millatip.footballapps.presentation.ui.matchdetail.MatchDetailActivity
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class SearchMatchActivity : AppCompatActivity(), SearchMatchContract.View {

    val presenter: SearchMatchPresenter<SearchMatchContract.View> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        onAttachView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        searchView.queryHint = "Search match..."
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                EspressoIdlingResource.increment()
                presenter.searchMatch(query.toString())
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun hideLoading() {
        pbSearchMatch.hide()
    }

    override fun displayMatch(matchList: List<Match>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        rvSearchMatch.adapter = SearchMatchAdapter(matchList) {
            startActivity<MatchDetailActivity>(
                MatchDetailActivity.EXTRA_MATCH_ID to it.matchId,
                MatchDetailActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                MatchDetailActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }
        rvSearchMatch.layoutManager = LinearLayoutManager(this)
    }

    override fun displayErrorMessage(message: String) {
        toast(message)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun showLoading() {
        pbSearchMatch.show()
    }

}
