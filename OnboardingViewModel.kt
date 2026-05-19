package com.kreedaankana.ui.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.dataStore
import com.kreedaankana.utils.getBoolean
import com.kreedaankana.utils.saveBoolean
import com.kreedaankana.utils.saveString
import kotlinx.coroutines.flow.Flow

class OnboardingViewModel(app: Application) : AndroidViewModel(app) {

    private val dataStore = app.dataStore

    fun isOnboarded(): Flow<Boolean> = dataStore.getBoolean(Constants.PREF_ONBOARDED)

    suspend fun saveProfile(teamName: String, captain: String, sport: String) {
        dataStore.saveString(Constants.PREF_TEAM_NAME, teamName)
        dataStore.saveString(Constants.PREF_CAPTAIN, captain)
        dataStore.saveString(Constants.PREF_SPORT, sport)
        dataStore.saveBoolean(Constants.PREF_ONBOARDED, true)
    }
}
