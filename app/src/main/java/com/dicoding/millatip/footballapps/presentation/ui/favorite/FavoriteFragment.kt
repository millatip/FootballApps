package com.dicoding.millatip.footballapps.presentation.ui.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.presentation.ui.favoritematches.FavoriteMatchFragment
import com.dicoding.millatip.footballapps.presentation.ui.favoriteteam.FavoriteTeamFragment
import com.dicoding.millatip.footballapps.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager(viewPagerFavorite)
        tabLayoutFav.setupWithViewPager(viewPagerFavorite)
    }

    private fun setupViewPager(viewPagerFavorite: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteMatchFragment(), "Matches")
        adapter.addFragment(FavoriteTeamFragment(), "Teams")
        viewPagerFavorite.adapter = adapter
    }

}

