package com.dicoding.millatip.footballapps.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dicoding.millatip.footballapps.data.model.FavoriteMatch
import com.dicoding.millatip.footballapps.data.model.League
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoriteMatch.TABLE_MATCH_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.MATCH_ID to TEXT + UNIQUE,
            FavoriteMatch.MATCH_NAME to TEXT,
            FavoriteMatch.MATCH_LEAGUE to TEXT,
            FavoriteMatch.HOME_TEAM_ID to TEXT,
            FavoriteMatch.HOME_TEAM to TEXT,
            FavoriteMatch.AWAY_TEAM_ID to TEXT,
            FavoriteMatch.AWAY_TEAM to TEXT,
            FavoriteMatch.MATCH_DATE to TEXT,
            FavoriteMatch.MATCH_TIME to TEXT,
            FavoriteMatch.HOME_SCORE to TEXT,
            FavoriteMatch.AWAY_SCORE to TEXT
        )

        db.createTable(
            League.TABLE_LEAGUE, true,
            League.LEAGUE_ID to TEXT + PRIMARY_KEY,
            League.LEAGUE_NAME to TEXT,
            League.LEAGUE_SPORT to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_MATCH_FAVORITE, true)
        db.dropTable(League.TABLE_LEAGUE, true)
    }

}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)
