package com.spinnertech.noteapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.FragmentLoginBinding
import com.spinnertech.noteapp.utils.SharedPrefManager
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.viewmodels.AuthViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private  lateinit var  binding: FragmentLoginBinding
    private  lateinit var  authViewModel : AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater , container,false)
        setUpViewModel()
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTv.setOnClickListener {
            val email : String = binding.emailEt.text.toString()
            val pass : String = binding.passEt.text.toString()
            if(email.isEmpty() || pass.isEmpty()){
                // update ui its empty
                Toast.makeText(context , "Email or Password Can't Be Empty " ,Toast.LENGTH_SHORT ).show()
            }else {
                // call the view model
                authViewModel.loginUser(email , pass)
            }
        }
        binding.signUpTv.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registrationFragment
            )
        }

        binding.forgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassFragment)
        }

    }


    private fun setUpViewModel(){
        authViewModel = ViewModelProvider(
            this).get(
            AuthViewModel::class.java
            )
    }

    private fun setUpObservers() {
        authViewModel.userData.observe(requireActivity() , {
            it->
            it?.let {
                resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                     // store the data
                        SharedPrefManager.put(resource.data?.data?.get(0), "user_data")
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context , "Error : ${resource.message}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
