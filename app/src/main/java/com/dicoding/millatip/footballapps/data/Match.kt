package com.dicoding.millatip.footballapps.data

import com.squareup.moshi.Json

data class Match(

    @field:Json(name = "idEvent")
    var matchId: String? = null,

    @field:Json(name = "strEvent")
    var matchName: String? = null,

    @field:Json(name = "strLeague")
    var league: String? = null,

    @field:Json(name = "idHomeTeam")
    var homeTeamId: String? = null,

    @field:Json(name = "idAwayTeam")
    var awayTeamId: String? = null,

    @field:Json(name = "strHomeTeam")
    var homeTeamName: String? = null,

    @field:Json(name = "strAwayTeam")
    var awayTeamName: String? = null,

    @field:Json(name = "dateEvent")
    var matchDate: String? = null,

    @field:Json(name = "strTime")
    var matchTime: String? = null,

    @field:Json(name = "intHomeScore")
    var homeScore: String? = null,

    @field:Json(name = "intAwayScore")
    var awayScore: String? = null,

    @field:Json(name = "strHomeGoalsDetails")
    var homeGoals: String? = null,

    @field:Json(name = "strAwayGoalsDetails")
    var awayGoals: String? = null,

    @field:Json(name = "strHomeRedCards")
    var homeRedCards: String? = null,

    @field:Json(name = "strAwayRedCards")
    var awayRedCards: String? = null,

    @field:Json(name = "strHomeYellowCards")
    var homeYellowCards: String? = null,

    @field:Json(name = "strAwayYellowCards")
    var awayYellowCards: String? = null,

    @field:Json(name = "strHomeFormation")
    var homeFormation: String? = null,

    @field:Json(name = "strAwayFormation")
    var awayFormation: String? = null,

    @field:Json(name = "strHomeLineupGoalkeeper")
    var homeGK: String? = null,

    @field:Json(name = "strHomeLineupDefense")
    var homeDefense: String? = null,

    @field:Json(name = "strHomeLineupMidfield")
    var homeMidfield: String? = null,

    @field:Json(name = "strHomeLineupForward")
    var homeForward: String? = null,

    @field:Json(name = "strHomeLineupSubstitutes")
    var homeSubs: String? = null,

    @field:Json(name = "strAwayLineupGoalkeeper")
    var awayGK: String? = null,

    @field:Json(name = "strAwayLineupDefense")
    var awayDefense: String? = null,

    @field:Json(name = "strAwayLineupMidfield")
    var awayMidfield: String? = null,

    @field:Json(name = "strAwayLineupForward")
    var awayForward: String? = null,

    @field:Json(name = "strAwayLineupSubstitutes")
    var awaySubs: String? = null,

    @field:Json(name = "strSport")
    var sport:String? = null
    )