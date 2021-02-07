package com.spinnertech.noteapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinnertech.noteapp.models.CateogryResp
import com.spinnertech.noteapp.models.CreateResp
import com.spinnertech.noteapp.models.TaskResp
import com.spinnertech.noteapp.models.UserModel
import com.spinnertech.noteapp.repository.DashboardRepository
import com.spinnertech.noteapp.utils.Resource
import com.spinnertech.noteapp.utils.SharedPrefManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllListViewModel() : ViewModel() {
    private val repository = DashboardRepository()

    val all_taskList = MutableLiveData<Resource<TaskResp>>()

    val deleteResp  = MutableLiveData<Resource<CreateResp>>()





    fun getAllTaskList(user_id: Int) {
        // convert it to the sha256
        viewModelScope.launch {
            repository.getAllList(user_id)
                .collect { data ->
                    all_taskList.value = data
                }
        }
    }

    fun deleteTask(post_id: Int) {
        // convert it to the sha256
        viewModelScope.launch {
            repository.deleteTask(post_id)
                .collect { data ->
                    deleteResp.value = data
                }
        }
    }





}
