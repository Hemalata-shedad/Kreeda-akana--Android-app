package com.kreedaankana.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.kreedaankana.data.local.AppDatabase
import com.kreedaankana.data.local.MatchHistoryEntity
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.dataStore
import com.kreedaankana.utils.getString
import kotlinx.coroutines.flow.combine

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val dataStore = app.dataStore
    private val matchHistoryDao = AppDatabase.getInstance(app).matchHistoryDao()

    data class ProfileData(
        val teamName: String,
        val captain: String,
        val sport: String
    )

    val profile: LiveData<ProfileData> = combine(
        dataStore.getString(Constants.PREF_TEAM_NAME),
        dataStore.getString(Constants.PREF_CAPTAIN),
        dataStore.getString(Constants.PREF_SPORT)
    ) { teamName, captain, sport ->
        ProfileData(teamName, captain, sport)
    }.asLiveData()

    fun getMatchHistory(teamName: String): LiveData<List<MatchHistoryEntity>> =
        matchHistoryDao.getMatchesForTeam(teamName)
}
