package com.spinnertech.noteapp.models

data class TaskResp(
    val `data`: List<TaskItem>,
    val error: Boolean,
    val msg: String
)
