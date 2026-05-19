package com.kreedaankana.ui.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreedaankana.data.model.Challenge
import com.kreedaankana.databinding.ItemChallengeBinding
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.toDateTimeString

class ChallengeAdapter(
    private val myTeamName: String,
    private val onAccept: (Challenge) -> Unit,
    private val onDecline: (Challenge) -> Unit,
    private val onCounter: (Challenge) -> Unit
) : ListAdapter<Challenge, ChallengeAdapter.ChallengeViewHolder>(DIFF) {

    inner class ChallengeViewHolder(private val b: ItemChallengeBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: Challenge) {
            b.tvTeamName.text = "🏆 ${item.teamName}"
            b.tvSport.text = "⚽ ${item.sport}"
            b.tvDateTime.text = "${item.date}  |  ${item.time}"
            b.tvLocation.text = "📍 ${item.location}"
            b.tvMessage.text = "\"${item.message}\""
            b.tvPostedAt.text = item.timestamp.toDateTimeString()

            // Status badge
            when (item.status) {
                Constants.STATUS_OPEN -> {
                    b.tvStatus.text = "OPEN"
                    b.tvStatus.setBackgroundResource(com.kreedaankana.R.drawable.bg_status_open)
                }
                Constants.STATUS_ACCEPTED -> {
                    b.tvStatus.text = "✅ ACCEPTED by ${item.respondingTeam}"
                    b.tvStatus.setBackgroundResource(com.kreedaankana.R.drawable.bg_status_accepted)
                }
                Constants.STATUS_DECLINED -> {
                    b.tvStatus.text = "❌ DECLINED by ${item.respondingTeam}"
                    b.tvStatus.setBackgroundResource(com.kreedaankana.R.drawable.bg_status_declined)
                }
                Constants.STATUS_COUNTER -> {
                    b.tvStatus.text = "🔄 COUNTER by ${item.respondingTeam}: ${item.counterProposal}"
                    b.tvStatus.setBackgroundResource(com.kreedaankana.R.drawable.bg_status_open)
                }
            }

            // Show action buttons only if this is not our challenge and still OPEN
            val isOurChallenge = item.teamName == myTeamName
            val isOpen = item.status == Constants.STATUS_OPEN

            if (!isOurChallenge && isOpen) {
                b.layoutActions.visibility = View.VISIBLE
                b.btnAccept.setOnClickListener { onAccept(item) }
                b.btnDecline.setOnClickListener { onDecline(item) }
                b.btnCounter.setOnClickListener { onCounter(item) }
            } else {
                b.layoutActions.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder =
        ChallengeViewHolder(
            ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areItemsTheSame(a: Challenge, b: Challenge) = a.id == b.id
            override fun areContentsTheSame(a: Challenge, b: Challenge) = a == b
        }
    }
}
