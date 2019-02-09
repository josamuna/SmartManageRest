package com.josamuna.smartmanagerest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.josamuna.smartmanagerest.classes.ApplicationPreferences
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.ToastType
import kotlinx.android.synthetic.main.activity_database.*
import kotlin.system.exitProcess

class DatabaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        //Save Database preferences (Server Name or IP Address and Database Name)
        btn_save_database_params.setOnClickListener {
            if(edtDatabaseName.text.isNullOrEmpty() ||
                    edtServerName.text.isNullOrEmpty()){
                Factory.makeToastMessage(applicationContext,"Please set a valide Database name and Server Name (IP Address) !!!", ToastType.Long)
            }else{
                ApplicationPreferences.preferences.edit().putString("database_pref", edtDatabaseName.text.toString()).apply()
                ApplicationPreferences.preferences.edit().putString("server_pref", edtServerName.text.toString()).apply()

                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Perform Action when press Back Button : Quit Application
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
