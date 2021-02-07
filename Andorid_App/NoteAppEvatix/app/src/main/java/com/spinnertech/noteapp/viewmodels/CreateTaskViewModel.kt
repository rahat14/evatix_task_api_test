package com.spinnertech.noteapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinnertech.noteapp.models.CateogryResp
import com.spinnertech.noteapp.models.CreateResp
import com.spinnertech.noteapp.models.TaskItem
import com.spinnertech.noteapp.repository.DashboardRepository
import com.spinnertech.noteapp.utils.Resource
import com.spinnertech.noteapp.utils.Utils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CreateTaskViewModel(): ViewModel()  {

    val createTaskResp = MutableLiveData<Resource<CreateResp>>()
    private val repository = DashboardRepository()
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
    fun createTask(taskModel: TaskItem) {
        viewModelScope.launch {
                repository.createTask(taskModel)
                    .collect { data ->
                        createTaskResp.value = data
                    }
        }
    }
}
