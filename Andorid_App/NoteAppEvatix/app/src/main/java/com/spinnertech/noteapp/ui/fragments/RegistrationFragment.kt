package com.spinnertech.noteapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.FragmentRegistrationBinding
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.utils.Utils
import com.spinnertech.noteapp.viewmodels.AuthViewModel


class RegistrationFragment(mMockContext: Context?) : Fragment() {


    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        setUpViewModel()
        setUpObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.regBtn.setOnClickListener {
            val user_name = binding.userNameET.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passordEt.text.toString()
            val conPass = binding.conPassordEt.text.toString();


            if (validData(user_name, email, pass, conPass)) {
                authViewModel.regUser(user_name, email, pass)
            }


        }
    }

     fun validData(user_name: String, email: String, pass: String, conPass: String): Boolean {

        if (user_name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
           // Toast.makeText(context, "Fill The From !!!", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (pass == conPass && Utils.isValidEmail(email)) {
                // call the view model
                //  authViewModel.regUser(user_name  , email , pass )
                return true
            } else {
              //  Toast.makeText(context, "Password Did not Match", Toast.LENGTH_SHORT).show()
                return false
            }
        }
    }

    private fun setUpViewModel() {
        authViewModel = ViewModelProvider(
            this
        ).get(
            AuthViewModel::class.java
        )
    }


    private fun setUpObservers() {
        authViewModel.regUserData.observe(requireActivity(), { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(
                            context,
                            "User Registered!! Please Login To Continue",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.loginFragment)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error : ${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

}
