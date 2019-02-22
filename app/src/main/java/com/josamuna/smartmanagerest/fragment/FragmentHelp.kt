package com.josamuna.smartmanagerest.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentHelp : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Allow TextView to support scrolling or scrollbar
        val txtContent: TextView = view.findViewById<View>(R.id.txtContentHelp) as TextView
        txtContent.movementMethod = ScrollingMovementMethod()

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_help)

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.Help
    }

    override fun onResume() {
        super.onResume()

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.Help
    }
}
