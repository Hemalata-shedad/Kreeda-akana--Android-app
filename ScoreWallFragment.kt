package com.kreedaankana.ui.scorewall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kreedaankana.databinding.FragmentScorewallBinding
import com.kreedaankana.utils.showToast

class ScoreWallFragment : Fragment() {

    private var _binding: FragmentScorewallBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScoreWallViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScorewallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ScoreAdapter()
        binding.rvScores.layoutManager = LinearLayoutManager(requireContext())
        binding.rvScores.adapter = adapter

        viewModel.scores.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabPostScore.setOnClickListener {
            PostScoreDialog(viewModel.getMyTeamName()) { score ->
                viewModel.postScore(score) { success ->
                    requireContext().showToast(
                        if (success) "Result posted! 🏆" else "Failed to post result"
                    )
                }
            }.show(parentFragmentManager, "post_score")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
