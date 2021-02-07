package com.spinnertech.noteapp.models

data class CateogryResp(
    val `data`: List<CategoryItem>,
    val error: Boolean,
    val msg: String
)
