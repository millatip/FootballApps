package com.dicoding.millatip.footballapps.presentation.ui.searchmatch

import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository
import com.dicoding.millatip.footballapps.presentation.base.BasePresenter
import com.dicoding.millatip.footballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter<V : SearchMatchContract.View>
constructor(
    private val matchRepository: MatchRepository,
    val context: CoroutineContextProvider = CoroutineContextProvider()
) : BasePresenter<V>(), SearchMatchContract.UserInteractionListener<V> {
    override fun searchMatch(matchName: String) {
        view?.showLoading()
        if (matchName == ""){
            view?.displayErrorMessage("It's empty. We are searching for nothing.")
        }else {
            GlobalScope.launch(context.main) {
                try {
                    val data = matchRepository.getMatchSearchResult(matchName)
                    view?.displayMatch(data.body()?.events ?: mutableListOf())
                    view?.hideLoading()
                } catch (e: Exception) {
                    view?.hideLoading()
                    view?.displayErrorMessage("Unable to load the data")
                }
            }
        }
    }

}
