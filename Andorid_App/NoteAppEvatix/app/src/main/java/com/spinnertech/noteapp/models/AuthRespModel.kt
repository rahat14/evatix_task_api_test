package com.spinnertech.noteapp.models

data class AuthRespModel(
    val `data`: List<UserModel>,
    val error: Boolean,
    val msg: String
)