package com.dicoding.millatip.footballapps.data.model

data class FavoriteTeam(val id: Long?,
                        val teamId: String? = null,
                        val teamName: String? = null,
                        val teamBadge: String? = null) {
    companion object {
        const val TABLE_TEAM_FAVORITE: String = "TABLE_TEAM_FAVORITE"
        const val ID: String = "ID_"
        const val TEAM_ID = "TEAM_ID"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_BADGE = "TEAM_BADGE"
    }
}
