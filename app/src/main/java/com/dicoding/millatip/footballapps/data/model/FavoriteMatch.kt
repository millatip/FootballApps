package com.dicoding.millatip.footballapps.data.model

data class FavoriteMatch(
    val id: Long?,
    val matchId: String?,
    val matchName: String?,
    val homeTeamId: String?,
    val homeTeam: String?,
    val awayTeamId: String?,
    val awayTeam: String?,
    val matchDate: String?,
    val matchTime: String?,
    val homeScore: String?,
    val awayScore: String?
    ){
    companion object {
        const val TABLE_MATCH_FAVORITE: String = "TABLE_MATCH_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val MATCH_NAME: String = "MATCH_NAME"
        const val MATCH_LEAGUE: String = "MATCH_LEAGUE"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val MATCH_DATE: String = "MATCH_DATE"
        const val MATCH_TIME: String = "MATCH_TIME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}
