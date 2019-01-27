package com.dicoding.millatip.footballapps.presentation.ui.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.presentation.ui.nextmatch.NextMatchFragment
import com.dicoding.millatip.footballapps.presentation.ui.prevmatch.PrevMatchFragment
import com.dicoding.millatip.footballapps.presentation.ui.searchmatch.SearchMatchActivity
import com.dicoding.millatip.footballapps.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.startActivity

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
        setHasOptionsMenu(true)
    }

    private fun setupWithViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(PrevMatchFragment(), "Last")
        adapter.addFragment(NextMatchFragment(), "Next")
        viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search_match, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_search -> {
                startActivity<SearchMatchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

