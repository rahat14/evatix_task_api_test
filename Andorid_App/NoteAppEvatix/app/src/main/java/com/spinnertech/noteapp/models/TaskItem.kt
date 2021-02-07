package com.spinnertech.noteapp.models

data class TaskItem(
    val cat_id: Int,
    val category: CategoryItem,
    val created_at: String,
    val date: String,
    val dated: Int,
    val description: String,
    val duration: Int,
    val end_at: String,
    val is_completed: Int,
    val is_whole_day: Int,
    var start_at: String,
    val task_id: Int,
    val task_title: String,
    val updated_at: String,
    val user_id: Int
)
