package com.spinnertech.noteapp.models

data class UserModel(
    val created_at: String,
    val email: String,
    val isActive: Int,
    val password_hash: String,
    val updated_at: String,
    val user_id: Int,
    val user_name: String
)