package com.example.sladamiprzygod

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sladamiprzygod.databinding.FragmentStoperBinding


class StoperFragment : Fragment() {

    private var _binding: FragmentStoperBinding? = null
    private val binding get() = _binding!!

    private var running = false
    private var startTime = 0L
    private var elapsedTime = 0L
    private var pauseTime = 0L

    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                val currentTime = System.currentTimeMillis()
                elapsedTime = currentTime - startTime
                updateTimerText()
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            if (!running) {
                running = true
                binding.btnStart.isEnabled = false
                startTime = System.currentTimeMillis() - elapsedTime
                handler.post(runnable)
            }
        }

        binding.btnPause.setOnClickListener {
            if (running) {
                running = false
                pauseTime = System.currentTimeMillis()
                handler.removeCallbacks(runnable)
            }
        }

        binding.btnResume.setOnClickListener {
            if (!running) {
                running = true
                startTime += System.currentTimeMillis() - pauseTime
                handler.post(runnable)
            }
        }

        binding.btnStop.setOnClickListener {
            running = false
            binding.btnStart.isEnabled = false
            binding.btnPause.isEnabled = false
            binding.btnResume.isEnabled = false
        }

        binding.btnReset.setOnClickListener {
            running = false
            startTime = 0L
            elapsedTime = 0L
            pauseTime = 0L
            binding.tvTimer.text = "00:00:00"
            binding.btnStart.isEnabled = true
            binding.btnPause.isEnabled = true
            binding.btnResume.isEnabled = true
            handler.removeCallbacks(runnable)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("running", running)
        outState.putLong("startTime", startTime)
        outState.putLong("elapsedTime", elapsedTime)
        outState.putLong("pauseTime", pauseTime)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            running = it.getBoolean("running", false)
            startTime = it.getLong("startTime", 0L)
            elapsedTime = it.getLong("elapsedTime", 0L)
            pauseTime = it.getLong("pauseTime", 0L)
            updateTimerText()
            if (running) {
                handler.post(runnable)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (running) {
            handler.post(runnable)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        handler.removeCallbacks(runnable)
        _binding = null
    }

    private fun updateTimerText() {
        val seconds = (elapsedTime / 1000).toInt()
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        val time = String.format("%02d:%02d:%02d", hours, minutes, secs)
        binding.tvTimer.text = time
    }
}