package com.josamuna.smartmanagerest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.josamuna.smartmanagerest.classes.ApplicationPreferences
import kotlinx.android.synthetic.main.activity_database.*
import kotlin.system.exitProcess

class DatabaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        btn_save_database_params.setOnClickListener {
            if(edtDatabaseName.text.isNullOrEmpty() ||
                    edtServerName.text.isNullOrEmpty()){
                Toast.makeText(applicationContext, "Please set valide Database name and Server Name (IP Address) !!!",
                    Toast.LENGTH_LONG).show()
            }else{
                ApplicationPreferences.preferences.edit().putString("database_pref", edtDatabaseName.text.toString()).apply()
                ApplicationPreferences.preferences.edit().putString("server_pref", edtServerName.text.toString()).apply()

                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
