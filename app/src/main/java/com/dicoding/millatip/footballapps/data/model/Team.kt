package com.dicoding.millatip.footballapps.data.model

import com.squareup.moshi.Json

data class Team (
    @field:Json(name = "idTeam")
    var teamId: String? = null,

    @field:Json(name = "strTeam")
    var teamName: String? = null,

    @field:Json(name = "strTeamBadge")
    var teamBadge: String? = null,

    @field:Json(name = "strFormedYear")
    var teamFormedYear: String? = null,

    @field:Json(name = "strStadium")
    var teamStadium: String? = null,

    @field:Json(name = "strDescriptionEN")
    var teamOverview: String? = null

)
