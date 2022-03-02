package com.sue.notes.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sue.notes.databinding.ActivityIntroBinding
import com.sue.notes.ui.HostActivity

class IntroActivity: AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).apply {
            postDelayed({ goToMain() }, 1000)
        }
    }

    private fun goToMain() {
        Intent(this, HostActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}