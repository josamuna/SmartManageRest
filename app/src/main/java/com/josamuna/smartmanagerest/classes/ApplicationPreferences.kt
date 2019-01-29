package com.josamuna.smartmanagerest.classes

import android.content.Context
import android.content.SharedPreferences

object ApplicationPreferences {
    private const val MODE = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    fun init(context:Context, default_pref: String){
        preferences = context.getSharedPreferences(default_pref, MODE)
    }
}