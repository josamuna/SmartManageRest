package com.josamuna.smartmanagerest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.capture_layout.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class CaptureActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private val MY_CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capture_layout)

        //val qrCode = findViewById<ZXingScannerView>(R.id.qrCodeScanner)

        scannerProperties()
    }

    private fun scannerProperties() {
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)

        if (Build.MANUFACTURER.equals("HUAWEI", ignoreCase = true))
            qrCodeScanner.setAspectTolerance(0.5f)
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_REQUEST_CODE)
                return
            }
        }
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }

    override fun onPause() {
        super.onPause()

        qrCodeScanner.stopCamera()
    }

    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            Toast.makeText(this,p0.text, Toast.LENGTH_LONG).show()
        }
    }
}
