package com.kreedaankana.ui.scorewall

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kreedaankana.data.model.ScoreEntry
import com.kreedaankana.databinding.DialogPostScoreBinding
import com.kreedaankana.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class PostScoreDialog(
    private val myTeamName: String,
    private val onPost: (ScoreEntry) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogPostScoreBinding.inflate(layoutInflater)

        // Pre-fill team A with our team
        binding.etTeamA.setText(myTeamName)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Constants.SPORTS
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSport.adapter = adapter

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("🏅 Post Match Result")
            .setView(binding.root)
            .setPositiveButton("Post Result") { _, _ ->
                val score = ScoreEntry(
                    teamA = binding.etTeamA.text.toString().trim(),
                    teamB = binding.etTeamB.text.toString().trim(),
                    scoreA = binding.etScoreA.text.toString().toIntOrNull() ?: 0,
                    scoreB = binding.etScoreB.text.toString().toIntOrNull() ?: 0,
                    sport = binding.spinnerSport.selectedItem.toString(),
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                )
                onPost(score)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
