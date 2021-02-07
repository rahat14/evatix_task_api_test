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

class RecoverPassViewModel() : ViewModel() {
    private val repository = AuthRepository()
    val resetData = MutableLiveData<Resource<AuthRespModel>>()

    fun resetUser(  resetCode : String ,  mail : String , pass : String ){
        // convert it to the sha256
        val   newPass = Utils.hash(pass)
        Log.d("TAG", "loginUser: $newPass")
        viewModelScope.launch {
            repository.resetUser( mail , newPass)
                .collect {
                        data-> resetData.value = data

                }
        }
    }
}