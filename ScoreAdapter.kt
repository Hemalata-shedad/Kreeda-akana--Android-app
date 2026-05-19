package com.kreedaankana.ui.scorewall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreedaankana.data.model.ScoreEntry
import com.kreedaankana.databinding.ItemScoreBinding
import com.kreedaankana.utils.toDateString

class ScoreAdapter : ListAdapter<ScoreEntry, ScoreAdapter.ScoreViewHolder>(DIFF) {

    inner class ScoreViewHolder(private val b: ItemScoreBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: ScoreEntry) {
            b.tvTeamA.text = item.teamA
            b.tvTeamB.text = item.teamB
            b.tvScoreA.text = item.scoreA.toString()
            b.tvScoreB.text = item.scoreB.toString()
            b.tvSport.text = "⚽ ${item.sport}"
            b.tvDate.text = item.timestamp.toDateString()

            // Highlight winner
            when {
                item.scoreA > item.scoreB -> {
                    b.tvTeamA.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.winner_color))
                    b.tvTeamB.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.loser_color))
                }
                item.scoreB > item.scoreA -> {
                    b.tvTeamB.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.winner_color))
                    b.tvTeamA.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.loser_color))
                }
                else -> {
                    b.tvTeamA.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.draw_color))
                    b.tvTeamB.setTextColor(b.root.context.getColor(com.kreedaankana.R.color.draw_color))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder =
        ScoreViewHolder(
            ItemScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ScoreEntry>() {
            override fun areItemsTheSame(a: ScoreEntry, b: ScoreEntry) = a.id == b.id
            override fun areContentsTheSame(a: ScoreEntry, b: ScoreEntry) = a == b
        }
    }
}
