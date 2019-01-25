package com.dicoding.millatip.footballapps.presentation.ui.teamdetail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.overview.OverviewFragment
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.player.TeamPlayerFragment
import com.dicoding.millatip.footballapps.utils.ViewPagerAdapter
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject

class TeamDetailActivity : AppCompatActivity(), TeamDetailContract.View {

    val presenter: TeamDetailPresenter<TeamDetailContract.View> by inject()

    private lateinit var team: Team
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_TEAM = "teamId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        onAttachView()
        setupViewPager(viewPagerTeam)
        tabLayoutTeam.setupWithViewPager(viewPagerTeam)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu

        return true
    }

    override fun displayFavoriteStatus(favorite: Boolean) {
        isFavorite = favorite
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_fav)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_fav)
    }

    override fun onAddToFavorite() {
        pbTeamDetail.snackbar("Added to Favorite")
    }

    override fun onRemoveFromFavorite() {
        pbTeamDetail.snackbar("Removed from Favorite")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            R.id.add_to_fav ->{
                if (isFavorite) presenter.removeFromFavorite(team)
                else presenter.addToFavorite(team)

                isFavorite = !isFavorite
                displayFavoriteStatus(isFavorite)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        presenter.getTeamDetail(intent.getStringExtra(EXTRA_TEAM))
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OverviewFragment(), "OVERVIEW")
        adapter.addFragment(TeamPlayerFragment(), "PLAYER")
        viewPager.adapter = adapter
    }

    override fun showLoading() {
        pbTeamDetail.show()
    }

    override fun hideLoading() {
        pbTeamDetail.hide()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    override fun displayTeam(team: Team) {
        this.team = team
        tvTeamName.text = team.teamName
        tvFormedYear.text = team.teamFormedYear
        tvStadium.text = team.teamStadium
        Glide.with(this).load(team.teamBadge).into(ivTeamBadge)
        teamDataListener?.onDataReceived(team)
        playerDataListener?.onDataReceived(team)
    }

    override fun displayErrorMessage(message: String) {
        pbTeamDetail.snackbar(message)
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    private var teamDataListener: DataListener? = null
    private var playerDataListener: DataListener? = null

    override fun onDetachView() {
        presenter.onDetach()
    }

    interface DataListener {
        fun onDataReceived(team: Team)
    }

    fun setTeamDataListener(listener: DataListener) {
        teamDataListener = listener
    }

    fun setPlayerDataListener(listener: DataListener) {
        playerDataListener = listener
    }
}
