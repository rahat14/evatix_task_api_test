package com.spinnertech.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.FragmentWalkthroughBinding


class WalkthroughFragment : Fragment() {


    private lateinit var binding: FragmentWalkthroughBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWalkthroughBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getStartedTv.setOnClickListener {
            findNavController().navigate(
                R.id.action_walkthroughFragment_to_loginFragment
            )
        }
    }
}