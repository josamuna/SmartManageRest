@file:Suppress("UNUSED_EXPRESSION")

package com.josamuna.smartmanagerest.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.capture_layout.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class FragmentCapture: Fragment(), ZXingScannerView.ResultHandler, ISharedFragment {

    private val myCameraRequestCode = 6515

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.capture_layout,container,false)
    }

    //Create companion objet to get and set values throw bundle
    companion object {
        @JvmStatic
        fun newinstance(stringValue: String)= FragmentCaptureMain().apply {
            arguments = Bundle().apply {
                putString("string_qr_code", stringValue)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_ID = 2

        //Use this line to set parameters for QRCode Scanner
        setQrCodeScannerProperties()
    }

    private fun setQrCodeScannerProperties(){
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)

        if(Build.MANUFACTURER.equals("HUAWAEI",ignoreCase = true))
            qrCodeScanner.setAspectTolerance(0.5f)
    }

    //Start QRCode Scanner when resume fragment
    override fun onResume() {
        super.onResume()

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_ID = 2

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                this@FragmentCapture.requestPermissions(arrayOf(Manifest.permission.CAMERA), myCameraRequestCode)
                return
            }
        }

        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this@FragmentCapture)
    }

    //Stop QRCode scanner on when pause fragment
    override fun onPause() {
        super.onPause()
        qrCodeScanner.stopCamera()
    }

    //Performed action after scanned QRCode and save result in Result object
    override fun handleResult(result: Result?) {
        if(result != null){
            //Allow to open a new Fragment to another

//            var bundle = Bundle()
//            bundle.putString("k1", result.text)
//            val fragment = FragmentCaptureMain()
//            fragment.arguments = bundle
//            openFragment(fragment, R.id.framelayout)

            val fragment = FragmentCapture.newinstance(result.text)
            openFragment(fragment, R.id.framelayout)
//            resumeCamera()
        }
    }

    /**
     * Resume the camera after 2 seconds when qr code successfully scanned through bar code reader.
     */
    private fun resumeCamera() {
        Toast.LENGTH_LONG
        val handler = Handler()
        handler.postDelayed({qrCodeScanner.resumeCameraPreview { this@FragmentCapture }}, 2000)
    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    //Call when detach Fragment
    override fun onDetach() {
        super.onDetach()

        val fragmentCaptureMain = FragmentCaptureMain()
        openFragment(fragmentCaptureMain, R.id.framelayout)
//        val intent = Intent(context, MainActivity::class.java)
//        startActivity(intent)
    }
}