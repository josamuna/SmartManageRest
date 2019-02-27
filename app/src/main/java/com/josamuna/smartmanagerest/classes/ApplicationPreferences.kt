package com.josamuna.smartmanagerest.classes

import android.content.Context
import android.content.SharedPreferences

/**
 * Static Class for manage application preferences
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
object ApplicationPreferences {
    private const val MODE: Int = Context.MODE_PRIVATE
    lateinit var preferences: SharedPreferences

    fun init(context: Context, default_pref: String) {
        preferences = context.getSharedPreferences(default_pref, MODE)
    }
}