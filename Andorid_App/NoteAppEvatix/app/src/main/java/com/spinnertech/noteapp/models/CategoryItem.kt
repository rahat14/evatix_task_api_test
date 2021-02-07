package com.spinnertech.noteapp.models

data class CategoryItem(
    val cat_id: Int,
    val color: String,
    val name: String,
    var counter: Int = 0
){
    override fun toString(): String {
        return name.toString()
    }
}
