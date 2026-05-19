package com.kreedaankana.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kreedaankana.databinding.ActivityOnboardingBinding
import com.kreedaankana.ui.main.MainActivity
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.showToast
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

        // Check if already onboarded
        lifecycleScope.launch {
            viewModel.isOnboarded().collect { done ->
                if (done) navigateToMain()
            }
        }

        // Populate sport spinner
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Constants.SPORTS
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSport.adapter = adapter

        binding.btnGetStarted.setOnClickListener {
            val teamName = binding.etTeamName.text.toString().trim()
            val captain = binding.etCaptain.text.toString().trim()
            val sport = binding.spinnerSport.selectedItem.toString()

            if (teamName.isEmpty()) {
                showToast("Please enter your team name!")
                return@setOnClickListener
            }
            if (captain.isEmpty()) {
                showToast("Please enter captain's name!")
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE
            binding.btnGetStarted.isEnabled = false

            lifecycleScope.launch {
                viewModel.saveProfile(teamName, captain, sport)
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
