package com.josamuna.smartmanagerest

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Using a CountDownTimer to wait few second after open Login Activity
        object:CountDownTimer(8000,1000){//(8000,1000){
            override fun onFinish() {
                //Call LoginActivity
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }

            override fun onTick(p0: Long) {
            }
        }.start()
    }
}
