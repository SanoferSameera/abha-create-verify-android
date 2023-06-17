package com.example.abha_create_verify_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abha_create_verify_android.databinding.FragmentCreateAbhaBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateABHAFragment : Fragment() {

    private var _binding: FragmentCreateAbhaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreateAbhaBinding.inflate(inflater, container, false)

        val includedTextViewLayoutBinding = binding.includedTextViewLayout
        includedTextViewLayoutBinding.headerText.text = "New Text"

        val includedLayoutBinding = binding.includedButtonLayout
        includedLayoutBinding.buttonText.text = "Proceed"

        return binding.root

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}