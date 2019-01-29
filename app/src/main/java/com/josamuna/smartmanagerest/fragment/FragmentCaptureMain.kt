package com.josamuna.smartmanagerest.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.fragment_capture_main.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentCaptureMain : Fragment(), ISharedFragment {

    private var stringValue: String = "QrCode Has not been scanned !!"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capture_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_ID = 1

        qrCodeCaptured.text = stringValue

        btn_capture.setOnClickListener {
            val fragmentCapture = FragmentCapture()
            openFragment(fragmentCapture, R.id.framelayout)
        }

        btn_save_captured.setOnClickListener {
            //Save captured QRCode
//            saveQRCodeCaptured(stringValue)
        }

        arguments?.getString("string_qr_code").let {
            if (it != null) {
                stringValue = it

                qrCodeCaptured.setText(it.toString())
                Toast.makeText(context, qrCodeCaptured.text, Toast.LENGTH_LONG).show()
                Log.e("Affiche", qrCodeCaptured.text.toString())
            } else
                qrCodeCaptured.setText("QrCode Has not been scanned !!")
        }
//
//
//            Toast.makeText(context, qrCodeCaptured.text, Toast.LENGTH_LONG).show()
//            Log.e("Affiche", qrCodeCaptured.text.toString())
//        }

        //Retrieve data
//        val bundle = arguments
//        if(arguments != null){
//            qrCodeCaptured.text = bundle!!.getString("k1")
//            Toast.makeText(context, qrCodeCaptured.text, Toast.LENGTH_LONG).show()
//            Log.e("Affiche", qrCodeCaptured.text.toString())
//        }

//        val bundle = arguments
//
//        val value = FragmentCaptureMain.retriveValueBundle(bundle, "k1")
//        if(!value.isNullOrEmpty()){
//            qrCodeCaptured.text = value
//            Toast.makeText(context, qrCodeCaptured.text, Toast.LENGTH_LONG).show()
//            Log.e("Affiche", qrCodeCaptured.text.toString())
//
//            btn_save_captured.text = qrCodeCaptured.text
//        }

//    companion object {
//        @JvmStatic
//        fun retriveValueBundle(bundle: Bundle?, strigKey: String): String?{
//            if(bundle != null)
//                return bundle.getString(strigKey)
//            else
//                return null
//        }
//    }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        arguments?.getString("string_qr_code").let {
//            if (it != null) {
//                stringValue = it
//
//                qrCodeCaptured.text = it.toString()
//                Toast.makeText(context, qrCodeCaptured.text, Toast.LENGTH_LONG).show()
//                Log.e("Affiche", qrCodeCaptured.text.toString())
//            } else
//                qrCodeCaptured.text = "QrCode Has not been scanned !!"
//        }
    }

    private fun saveQRCodeCaptured(stringValue: String) {
        println("stringValue = $stringValue")
    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_ID = 1
    }
}
