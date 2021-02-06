package com.spinnertech.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spinnertech.noteapp.R
import com.spinnertech.noteapp.databinding.FragmentResetPassBinding
import com.spinnertech.noteapp.utils.Status
import com.spinnertech.noteapp.viewmodels.AuthViewModel
import com.spinnertech.noteapp.viewmodels.RecoverPassViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResetPassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetPassFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private  lateinit var binding: FragmentResetPassBinding
    private lateinit var  resetViewModel: RecoverPassViewModel
    var mail : String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPassBinding.inflate(inflater , container, false)
        setUpViewModel()
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changePassTv.setOnClickListener {
            val email = arguments?.getString("MAIL").toString()
            val pass = binding.passordEt.text.toString()
            val conPass = binding.confirmPasswordEt.text.toString() ;
            val resetCode = binding.resetCode.text.toString()

            if(resetCode.isNotEmpty() || pass.isNotEmpty()){
                if(conPass == pass){

                    // call view model
                    resetViewModel.resetUser(resetCode , email , pass)

                }else {
                    Toast.makeText( context , "Password Did not match" , Toast.LENGTH_SHORT).show()
                }

            }else {
                Toast.makeText( context , "Fill The From " , Toast.LENGTH_SHORT).show()
            }



        }
    }


    private fun setUpViewModel(){
        resetViewModel = ViewModelProvider(
            requireActivity()).get(
            RecoverPassViewModel::class.java
        )
    }

    private fun setUpObservers() {
        resetViewModel.resetData.observe(requireActivity() , {
                it->
            it?.let {
                    resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        Toast.makeText(context , "Success : ${resource.data?.msg}" , Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_resetPassFragment_to_successfulFragment)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context , "Error : ${resource.message}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        activity?.viewModelStore?.clear()
//    }
}