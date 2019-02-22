@file:Suppress("UNUSED_EXPRESSION")

package com.josamuna.smartmanagerest.fragment

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.communicator.Communicator
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.capture_layout.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class FragmentCapture: Fragment(), ZXingScannerView.ResultHandler, ISharedFragment {

    private val myCameraRequestCode: Int = 6515
    //Object to ne use for SharedData between Fragment or Activity througt ViewModel Class
    private var model: Communicator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container?.context).inflate(R.layout.capture_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_capture_qrcode)

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.Capture

        //Instanciate model using with Communicator (ViewModel Class)
        model = ViewModelProviders.of(activity!!).get(Communicator::class.java)

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

    /**
     * Start QRCode Scanner when resume fragment
     */
    override fun onResume() {
        super.onResume()

        //Set FragmentCapture value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.Capture

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity!!.applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                this@FragmentCapture.requestPermissions(arrayOf(Manifest.permission.CAMERA), myCameraRequestCode)
                return
            }
        }

        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this@FragmentCapture)
    }

    /**
     * Stop QRCode scanner on when pause fragment
     */
    override fun onPause() {
        super.onPause()
        qrCodeScanner.stopCamera()
    }

    /**
     * Performed action after scanned QRCode and save result in Result object
     */
    override fun handleResult(result: Result?) {
        if(result != null){
            //Affecte value captured by QrCode reader in model
            model!!.setMessage(result.text)

//            resumeCamera()

            //Allow to open a new Fragment through another
            val fragment = FragmentCaptureMain()
            openFragment(fragment, R.id.framelayout)
        }
    }

    /**
     * Resume the camera after 2 seconds when qr code successfully scanned through bar code reader.
     */
//    private fun resumeCamera() {
//        Toast.LENGTH_LONG
//        val handler = Handler()
//        handler.postDelayed({qrCodeScanner.resumeCameraPreview { this@FragmentCapture }}, 2000)
//    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager!!.beginTransaction().replace(fragment_id, fragment).addToBackStack(null).commit()
    }

    /**
     * Call when detach Fragment
     */
    override fun onDetach() {
        super.onDetach()

        val fragmentCaptureMain = FragmentCaptureMain()
        openFragment(fragmentCaptureMain, R.id.framelayout)
    }
}