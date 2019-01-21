package com.dicoding.millatip.footballapps.data.model

import com.squareup.moshi.Json

data class Player (
    @field:Json(name = "idPlayer")
    var playerId: String? = null,

    @field:Json(name = "strTeam")
    var team: String? = null,

    @field:Json(name = "strPlayer")
    var playerName: String? = null,

    @field:Json(name = "strNationality")
    var nationality: String? = null,

    @field:Json(name = "dateBorn")
    var bornDate: String? = null,

    @field:Json(name = "strDescriptionEN")
    var description: String? = null,

    @field:Json(name = "strPosition")
    var position: String? = null,

    @field:Json(name = "strHeight")
    var height: String? = null,

    @field:Json(name = "strWeight")
    var weight: String? = null,

    @field:Json(name = "strThumb")
    var picture: String? = null,

    @field:Json(name = "strCutout")
    var imageCutout: String? = null

)
