package com.spinnertech.noteapp.utils

import android.os.Build
import android.util.Log
import android.util.Patterns
import com.google.common.base.Strings.isNullOrEmpty
import com.spinnertech.noteapp.models.UserModel
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        fun hash(text: String): String {
            val bytes = text.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }

        fun gatTodayDate(): String {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                return current.format(formatter)

            } else {
                val date = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val answer: String = formatter.format(date)
                Log.d("answer", answer)
                return answer
            }

        }

        fun getUserData() : UserModel? {

            val UserData: UserModel? = SharedPrefManager.get("user_data")


            return UserData
        }
        fun isValidEmail(email : String ): Boolean{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }


}
