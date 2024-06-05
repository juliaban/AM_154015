package com.example.sladamiprzygod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sladamiprzygod.databinding.FragmentSzlakBinding


class SzlakFragment : Fragment() {

    private var _binding: FragmentSzlakBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSzlakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title")
        val image = arguments?.getString("image")
        val description = arguments?.getString("description")
        val time = arguments?.getString("time")

        binding.tvTitle.text = title
        val resourceId = resources.getIdentifier(image, "drawable", requireContext().packageName)
        binding.ivImage.setImageResource(resourceId)
        binding.tvDescription.text = description
        binding.tvTime.text = time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}