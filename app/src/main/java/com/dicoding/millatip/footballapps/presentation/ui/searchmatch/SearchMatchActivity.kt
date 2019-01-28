package com.dicoding.millatip.footballapps.presentation.ui.searchmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.utils.show
import kotlinx.android.synthetic.main.activity_search_match.*
import org.koin.android.ext.android.inject

class SearchMatchActivity : AppCompatActivity() {

    val presenter: SearchMatchPresenter<SearchMatchContract.View> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        pbSearchMatch.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = MenuItemCompat.getActionView(menu.findItem(R.id.action_search)) as SearchView
        searchView.queryHint = "Search match..."
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()){
                    presenter.searchMatch("null")
                }else{

                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
