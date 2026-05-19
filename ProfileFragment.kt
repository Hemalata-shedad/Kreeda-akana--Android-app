package com.kreedaankana.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kreedaankana.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            binding.tvTeamName.text = profile.teamName.ifEmpty { "—" }
            binding.tvCaptain.text = profile.captain.ifEmpty { "—" }
            binding.tvSport.text = profile.sport.ifEmpty { "—" }

            if (profile.teamName.isNotEmpty()) {
                viewModel.getMatchHistory(profile.teamName).observe(viewLifecycleOwner) { matches ->
                    binding.tvMatchCount.text = "${matches.size} matches played"
                    val wins = matches.count { m ->
                        (m.teamA == profile.teamName && m.scoreA > m.scoreB) ||
                        (m.teamB == profile.teamName && m.scoreB > m.scoreA)
                    }
                    binding.tvWins.text = "Wins: $wins"
                    val sb = StringBuilder()
                    matches.take(10).forEach { m ->
                        sb.append("${m.teamA} ${m.scoreA} - ${m.scoreB} ${m.teamB}  (${m.date})\n")
                    }
                    binding.tvHistory.text = sb.toString().ifEmpty { "No matches yet" }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
