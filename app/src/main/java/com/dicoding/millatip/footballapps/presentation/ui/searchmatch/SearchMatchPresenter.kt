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
            view?.hideLoading()
            view?.displayErrorMessage("It's empty. We are searching for nothing.")
        }else {
            GlobalScope.launch(context.main) {
                try {
                    val data = matchRepository.getMatchSearchResult(matchName)
                    if (data.isSuccessful) {
                        if (data.code() == 200) {
                            view?.hideLoading()
                            view?.displayMatch(data.body()?.event ?: mutableListOf())
                        } else {
                            view?.hideLoading()
                            view?.displayErrorMessage("Unable to load team data")
                        }
                    } else {
                        view?.hideLoading()
                        view?.displayErrorMessage("Unable to load team data")
                    }
                } catch (e: Exception) {
                    view?.hideLoading()
                    view?.displayErrorMessage("Unable to load the data")
                }
            }
        }
    }

}
