package com.josamuna.smartmanagerest.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import kotlinx.android.synthetic.main.fragment_show_content_list_item.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentShowContentListItem : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_content_list_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_show_content)

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.ViewContentSelected

        if(Factory.TEXT_MESSAGE.isNotEmpty())
            edtShowContent.setText(Factory.TEXT_MESSAGE)
    }

    override fun onResume() {
        super.onResume()

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.ViewContentSelected
    }
}
