package com.kreedaankana.ui.challenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kreedaankana.data.model.Challenge
import com.kreedaankana.data.remote.FirebaseRepository
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.dataStore
import com.kreedaankana.utils.getString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ChallengeViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = FirebaseRepository()
    private val dataStore = app.dataStore

    val challenges: LiveData<List<Challenge>> = repo.getChallenges()

    fun getMyTeamName(): String = runBlocking {
        dataStore.getString(Constants.PREF_TEAM_NAME).first()
    }

    fun postChallenge(challenge: Challenge, onResult: (Boolean) -> Unit) {
        repo.postChallenge(challenge, onResult)
    }

    fun acceptChallenge(challenge: Challenge, onResult: (Boolean) -> Unit) {
        val myTeam = getMyTeamName()
        repo.respondToChallenge(
            challengeId = challenge.id,
            status = Constants.STATUS_ACCEPTED,
            respondingTeam = myTeam,
            onResult = onResult
        )
    }

    fun declineChallenge(challenge: Challenge, onResult: (Boolean) -> Unit) {
        val myTeam = getMyTeamName()
        repo.respondToChallenge(
            challengeId = challenge.id,
            status = Constants.STATUS_DECLINED,
            respondingTeam = myTeam,
            onResult = onResult
        )
    }

    fun counterChallenge(challenge: Challenge, counter: String, onResult: (Boolean) -> Unit) {
        val myTeam = getMyTeamName()
        repo.respondToChallenge(
            challengeId = challenge.id,
            status = Constants.STATUS_COUNTER,
            respondingTeam = myTeam,
            counterProposal = counter,
            onResult = onResult
        )
    }
}
