package com.dicoding.millatip.footballapps.presentation.ui.teamlist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView

import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.data.model.Team
import com.dicoding.millatip.footballapps.presentation.ui.teamdetail.TeamDetailActivity
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import com.dicoding.millatip.footballapps.utils.hide
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.fragment_team_list.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import org.koin.android.ext.android.inject

class TeamListFragment : Fragment(), TeamListContract.View {

    val presenter: TeamListPresenter<TeamListContract.View> by inject()

    override var selectedLeague: League
        get() = spTeamList.selectedItem as League
        set(value) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onAttachView()
        setHasOptionsMenu(true)
        spTeamList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedLeague = parent?.getItemAtPosition(position) as League
                EspressoIdlingResource.increment()
                presenter.getTeamList()
            }
        }
        presenter.getLeagueList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        searchView.queryHint = "Search team..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()){
                    val kata = ""
                    presenter.searchTeam(kata)
                    EspressoIdlingResource.increment()
                    presenter.getTeamList()
                    spTeamList.show()
                }else{
                    spTeamList.hide()
                    EspressoIdlingResource.increment()
                    presenter.searchTeam(query.toString())
                }
                return true
            }

        })
    }

    override fun showLoading() {
        pbTeamList.show()
    }

    override fun hideLoading() {
        pbTeamList.hide()
    }

    override fun displayTeamList(teams: List<Team>) {
        if (!EspressoIdlingResource.idlingResource.isIdleNow){
            EspressoIdlingResource.decrement()
        }
        rvTeamList.adapter = TeamAdapter(requireContext(), teams) {
            startActivity<TeamDetailActivity>(TeamDetailActivity.EXTRA_TEAM to it.teamId)
        }
        rvTeamList.layoutManager = LinearLayoutManager(context)
    }

    override fun displayErrorMessage(message: String) {
        pbTeamList.snackbar(message)
    }

    override fun displayLeagueList(leagues: List<League>) {
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, leagues)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTeamList.adapter = spinnerAdapter
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
