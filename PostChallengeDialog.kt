package com.kreedaankana.ui.challenge

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kreedaankana.data.model.Challenge
import com.kreedaankana.databinding.DialogPostChallengeBinding
import com.kreedaankana.utils.Constants

class PostChallengeDialog(
    private val teamName: String,
    private val onPost: (Challenge) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogPostChallengeBinding.inflate(layoutInflater)

        // Sport spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Constants.SPORTS
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSport.adapter = adapter

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("🏟 Post a Challenge")
            .setView(binding.root)
            .setPositiveButton("Post!") { _, _ ->
                val challenge = Challenge(
                    teamName = teamName,
                    sport = binding.spinnerSport.selectedItem.toString(),
                    date = binding.etDate.text.toString().trim(),
                    time = binding.etTime.text.toString().trim(),
                    location = binding.etLocation.text.toString().trim(),
                    message = binding.etMessage.text.toString().trim()
                )
                onPost(challenge)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
