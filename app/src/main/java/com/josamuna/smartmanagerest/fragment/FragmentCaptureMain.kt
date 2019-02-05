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
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.fragment_capture_main.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentCaptureMain : Fragment(), ISharedFragment {

    //Default value for QrCode Scanned
    private var stringValue: String = "QrCode Has not been scanned !!"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capture_main, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set Fragment Title
        val supportAct = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_capture_main)

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_ID = 1

        qrCodeCaptured.text = stringValue

        //Perform Action when clic on capture button
        btn_capture.setOnClickListener {
            val fragmentCapture = FragmentCapture()
            openFragment(fragmentCapture, R.id.framelayout)
        }

        //Perform Action when saving sacnned QrCode
        btn_save_captured.setOnClickListener {
            //Save captured QRCode
            saveQRCodeCaptured(stringValue)
        }

        //Using Shared Data througt ViewModel Class
        val model = ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.modelMessage.observe(this, Observer<Any> { t ->
            if(t.toString().isNotEmpty())
                qrCodeCaptured.text = t.toString()
        })
    }

    private fun saveQRCodeCaptured(stringValue: String) {
        println("stringValue = $stringValue")
        Log.e("Error", "Connection ${Factory.CONN_VALUE.toString()}")
    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    override fun onResume() {
        super.onResume()

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_ID = 1
    }
}
