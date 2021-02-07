package com.spinnertech.noteapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinnertech.noteapp.models.CateogryResp
import com.spinnertech.noteapp.models.TaskResp
import com.spinnertech.noteapp.models.UserModel
import com.spinnertech.noteapp.repository.DashboardRepository
import com.spinnertech.noteapp.utils.Resource
import com.spinnertech.noteapp.utils.SharedPrefManager
import com.spinnertech.noteapp.utils.Utils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardViewModel() : ViewModel() {
    private val repository = DashboardRepository()

    val today_taskList = MutableLiveData<Resource<TaskResp>>()
    val UserData = MutableLiveData<UserModel>()
    val categoryList = MutableLiveData<Resource<CateogryResp>>()
    fun getCat() {
        // convert it to the sha256
        viewModelScope.launch {
            repository.getCategories()
                .collect { data ->
                    categoryList.value = data
                }
        }
    }

    fun getTodayTask( date: String) {
        // convert it to the sha256
        val  user_id = Utils.getUserData()?.user_id
        viewModelScope.launch {
            if (user_id != null) {
                repository.getTodayTaskList(user_id, date)
                    .collect { data ->
                        today_taskList.value = data
                    }

            }
        }
    }

    fun  getProfileData(){

        UserData.value = SharedPrefManager.get("user_data")
        Log.d("TAG", "setUpObservers: "+ SharedPrefManager.get("user_data"))

    }


}
