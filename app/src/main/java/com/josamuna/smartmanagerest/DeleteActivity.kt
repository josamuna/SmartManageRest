package com.josamuna.smartmanagerest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {
    private val MY_CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        generateQrCodeButton.setOnClickListener {
//            if (checkEditText()) {
//                val user = UserObject(fullName = fullNameEditText.text.toString(), age = Integer.parseInt(ageEditText.text.toString()))
//                val serializeString = Gson().toJson(user)
//                val bitmap = QRCodeHelper
//                    .newInstance(this)
//                    .setContent(serializeString)
//                    .setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
//                    .setMargin(2)
//                    .getQRCOde()
//                qrCodeImageView.setImageBitmap(bitmap)
//            }
        }
    }

    private fun checkEditText(): Boolean {
        val user = findViewById<View>(R.id.fullNameEditText) as EditText
        if(user.text.toString().isEmpty())
            return true
        return false
    }
}


