package com.kreedaankana.ui.scorewall

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kreedaankana.data.local.AppDatabase
import com.kreedaankana.data.local.MatchHistoryEntity
import com.kreedaankana.data.model.ScoreEntry
import com.kreedaankana.data.remote.FirebaseRepository
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.dataStore
import com.kreedaankana.utils.getString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScoreWallViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = FirebaseRepository()
    private val matchHistoryDao = AppDatabase.getInstance(app).matchHistoryDao()
    private val dataStore = app.dataStore

    val scores: LiveData<List<ScoreEntry>> = repo.getScores()

    fun getMyTeamName(): String = runBlocking {
        dataStore.getString(Constants.PREF_TEAM_NAME).first()
    }

    fun postScore(score: ScoreEntry, onResult: (Boolean) -> Unit) {
        repo.postScore(score) { success ->
            if (success) {
                // Also save to local Room DB as match history
                viewModelScope.launch {
                    matchHistoryDao.insertMatch(
                        MatchHistoryEntity(
                            teamA = score.teamA,
                            teamB = score.teamB,
                            scoreA = score.scoreA,
                            scoreB = score.scoreB,
                            sport = score.sport,
                            date = score.date,
                            timestamp = score.timestamp
                        )
                    )
                }
            }
            onResult(success)
        }
    }
}
