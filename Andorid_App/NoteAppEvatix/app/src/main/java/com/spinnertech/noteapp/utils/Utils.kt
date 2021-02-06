package com.spinnertech.noteapp.utils

import java.security.MessageDigest

class Utils {
    companion object {
        fun hash(text : String ): String {
            val bytes = text.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }
    }
}