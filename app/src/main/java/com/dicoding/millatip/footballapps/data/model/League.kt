package com.dicoding.millatip.footballapps.data.model

import com.squareup.moshi.Json

data class League (
    @field:Json(name = "idLeague")
    var leagueId: String? = null,

    @field:Json(name = "strLeague")
    var leagueName: String? = null,

    @field:Json(name = "strSport")
    var sport: String? = null
){
    override fun toString(): String {
        return if (leagueName == null) "" else leagueName.toString()
    }

}
