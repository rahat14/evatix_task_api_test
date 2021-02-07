package com.spinnertech.noteapp

import android.app.Application
import android.content.SharedPreferences
import com.spinnertech.noteapp.utils.SharedPrefManager

class Aplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefManager.with(this)

    }
}
