package com.spinnertech.noteapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinnertech.noteapp.models.AuthRespModel
import com.spinnertech.noteapp.repository.AuthRepository
import com.spinnertech.noteapp.utils.Resource
import com.spinnertech.noteapp.utils.Utils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {
    private val repository = AuthRepository()
    val userData = MutableLiveData<Resource<AuthRespModel>>()
    val regUserData = MutableLiveData<Resource<AuthRespModel>>()

    fun loginUser( mail : String , pass : String ){
        // convert it to the sha256
      val   newPass = Utils.hash(pass)
        Log.d("TAG", "loginUser: $newPass")
        viewModelScope.launch {
            repository.loginUser(mail , newPass)
                .collect {
                    data-> userData.value = data

                }
        }
    }


    fun regUser(  username : String ,  mail : String , pass : String ){
        // convert it to the sha256
        val   newPass = Utils.hash(pass)
        Log.d("TAG", "loginUser: $newPass")
        viewModelScope.launch {
            repository.regUser( username, mail , newPass)
                .collect {
                        data-> regUserData.value = data

                }
        }
    }
}