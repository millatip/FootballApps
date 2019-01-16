package com.dicoding.millatip.footballapps.presentation.ui.favoriteMatch


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dicoding.millatip.footballapps.R

class FavoriteMatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }


}
