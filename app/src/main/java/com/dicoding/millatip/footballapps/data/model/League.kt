package com.dicoding.millatip.footballapps.data.model

import com.squareup.moshi.Json

data class League(
    @field:Json(name = "idLeague")
    var leagueId: String? = null,

    @field:Json(name = "strLeague")
    var leagueName: String? = null,

    @field:Json(name = "strSport")
    var sport: String? = null
) {

    companion object {
        const val TABLE_LEAGUE = "TABLE_LEAGUE"
        const val LEAGUE_ID = "ID_"
        const val LEAGUE_NAME = "LEAGUE_NAME"
        const val LEAGUE_SPORT = "LEAGUE_SPORT"
    }

    override fun toString(): String {
        return if (leagueName == null) "" else leagueName.toString()
    }

}
