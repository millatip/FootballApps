package com.dicoding.millatip.footballapps.presentation.ui.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.presentation.ui.favorite.FavoriteFragment
import com.dicoding.millatip.footballapps.presentation.ui.match.MatchFragment
import com.dicoding.millatip.footballapps.presentation.ui.teamlist.TeamListFragment
import com.dicoding.millatip.footballapps.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewPager(viewPager)
        bottomNavListener()
    }

    private fun bottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_match -> viewPager.currentItem = 0
                R.id.action_fav -> viewPager.currentItem = 2
                R.id.action_team -> viewPager.currentItem = 1
            }
            true
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MatchFragment(), "Match")
        adapter.addFragment(TeamListFragment(), "Teams")
        adapter.addFragment(FavoriteFragment(), "Favorite")
        viewPager.adapter = adapter
    }

}

