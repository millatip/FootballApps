package com.dicoding.millatip.footballapps.presentation.ui.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.millatip.footballapps.R
import com.dicoding.millatip.footballapps.data.model.League
import com.dicoding.millatip.footballapps.presentation.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val presenter: SplashPresenter<SplashContract.View> by inject()

    override fun openActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun displayErrorMessage(message: String) {
        pbSplash.snackbar(message)
    }

    override fun displayLeagueList(leagues: List<League>) {
    }

    override fun onAttachView() {
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }
    override fun onDetachView() {
        presenter.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        onAttachView()
        presenter.getLeagueList()
    }
}
