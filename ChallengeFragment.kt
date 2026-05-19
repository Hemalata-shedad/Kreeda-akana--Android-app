package com.kreedaankana.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.kreedaankana.R
import com.kreedaankana.databinding.FragmentChallengeBinding
import com.kreedaankana.utils.showToast

class ChallengeFragment : Fragment() {

    private var _binding: FragmentChallengeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myTeam = viewModel.getMyTeamName()

        val adapter = ChallengeAdapter(
            myTeamName = myTeam,
            onAccept = { challenge ->
                viewModel.acceptChallenge(challenge) { success ->
                    requireContext().showToast(if (success) "Challenge Accepted! 🤝" else "Error responding")
                }
            },
            onDecline = { challenge ->
                viewModel.declineChallenge(challenge) { success ->
                    requireContext().showToast(if (success) "Challenge Declined" else "Error responding")
                }
            },
            onCounter = { challenge ->
                showCounterDialog(challenge)
            }
        )

        binding.rvChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenges.adapter = adapter

        viewModel.challenges.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabPostChallenge.setOnClickListener {
            PostChallengeDialog(myTeam) { challenge ->
                viewModel.postChallenge(challenge) { success ->
                    requireContext().showToast(
                        if (success) "Challenge posted! 💪" else "Failed to post challenge"
                    )
                }
            }.show(parentFragmentManager, "post_challenge")
        }
    }

    private fun showCounterDialog(challenge: com.kreedaankana.data.model.Challenge) {
        val input = TextInputEditText(requireContext())
        input.hint = "Your counter proposal..."
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Counter Proposal")
            .setView(input)
            .setPositiveButton("Send") { _, _ ->
                val counter = input.text.toString().trim()
                if (counter.isNotEmpty()) {
                    viewModel.counterChallenge(challenge, counter) { success ->
                        requireContext().showToast(
                            if (success) "Counter sent! 🔄" else "Error sending counter"
                        )
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
