package com.dicoding.millatip.footballapps.presentation.ui.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchFragment
import com.dicoding.millatip.footballapps.presentation.ui.prevmatch.PrevMatchFragment
import com.dicoding.millatip.footballapps.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupWithViewPager(viewPagerMatch)
        tabLayoutMatch.setupWithViewPager(viewPagerMatch)
    }

    private fun setupWithViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(PrevMatchFragment(), "Last")
        adapter.addFragment(NextMatchFragment(), "Next")
        viewPager.adapter = adapter
    }

}

