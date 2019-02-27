package com.josamuna.smartmanagerest.communicator

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Communicator Class for easy sharing data
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class Communicator : ViewModel() {
    val modelMessage = MutableLiveData<Any>()

    /**
     * Allow to set message to be shared in all other Fragments or Activity
     */
    fun setMessage(strMessage: String) {
        modelMessage.value = strMessage
    }
}