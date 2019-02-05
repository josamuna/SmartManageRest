package com.josamuna.smartmanagerest.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.communicator.Communicator
import java.sql.Connection


/**
 * A simple [Fragment] subclass.
 *
 */
class DefaultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set Fragment Title
        val supportAct = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_default_fragment)

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_ID = 0
    }

    override fun onResume() {
        super.onResume()

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_ID = 0
    }
}
