package com.josamuna.smartmanagerest.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.interfaces.ISharedFragment

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

        val btnCapture = view.findViewById<View>(R.id.btn_capture) as Button
        val btnSave = view.findViewById<View>(R.id.btn_save_captured) as Button
        val edtViewQrCode = view.findViewById<View>(R.id.qrCodeCaptured) as TextView //Use EditText Like TextView

        edtViewQrCode.text = stringValue

        btnCapture.setOnClickListener {
            val fragmentCapture = FragmentCapture()
            openFragment(fragmentCapture, R.id.framelayout)
        }

        btnSave.setOnClickListener {
            //Save captured QRCode
            saveQRCodeCaptured(stringValue)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getString("string_qr_code").let {
            if (it != null)
                stringValue = it
        }
    }

    private fun saveQRCodeCaptured(stringValue: String) {

    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
