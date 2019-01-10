package com.dicoding.millatip.footballapps.presentation.ui.nextmatch

import com.dicoding.millatip.footballapps.data.repository.match.MatchRepository

class NextMatchPresenter <V : NextMatchContract.View>
constructor(private matchRepository: MatchRepository, private val ){
}