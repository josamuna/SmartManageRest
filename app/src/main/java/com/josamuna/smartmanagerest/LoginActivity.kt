package com.josamuna.smartmanagerest

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.josamuna.smartmanagerest.classes.ApplicationPreferences
import com.josamuna.smartmanagerest.classes.ConnectionClass
import com.josamuna.smartmanagerest.classes.Factory
import kotlinx.android.synthetic.main.activity_login.*
import java.sql.SQLException
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private var user = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Initialise preferences to default valueA
        ApplicationPreferences.init(applicationContext, "default_pref")

        //Verifie preferences
        if(verifiePreferences()) {

            //Handle acton on clic button Login
            btnLogin.setOnClickListener {
                user = edtUserName.text.toString()
                password = edtPassword.text.toString()

                val connect = ConnectionClass()
                connect.serverName = ApplicationPreferences.preferences.getString("server_pref", null)
                connect.database = ApplicationPreferences.preferences.getString("database_pref", null)
                connect.userID = user
                connect.password = password

                //Execute connexion to DataBase
                try {
                    if (callConnection(connect)) {
                        //Open Main Activity
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                } catch (e: SQLException) {
                    Log.e("Error Connection", "Unable to connect to Database,\nCheck username and password ${e.message}")
                    Toast.makeText(
                        applicationContext, "Unable to connect to Database,\nCheck username and password",
                        Toast.LENGTH_LONG
                    ).show()
//                buildDialog(context, "Connection to Database",
//                    "Enable to open connection to Database,\n " + e.message)
                } catch (e: ClassNotFoundException) {
                    Log.e("Error Connection", "Unable to connect to Database,\nDriver not found ${e.message}")
                    Toast.makeText(
                        applicationContext, "Unable to connect to Database,\nDriver not found",
                        Toast.LENGTH_LONG
                    ).show()
                } catch (e: Exception) {
                    Log.e("Error", "Unable to connect to Database,\n ${e.message}")
                    Toast.makeText(
                        applicationContext, "Unable to connect to Database,\n ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }else{
            //Open Activity Preferences
            val intent = Intent(applicationContext, DatabaseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun verifiePreferences(): Boolean{
        if(ApplicationPreferences.preferences.getString("database_pref", null).isNullOrEmpty() &&
            ApplicationPreferences.preferences.getString("server_pref", null).isNullOrEmpty())
            return false
        return true
    }

    private var positiveButtonClic = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()
    }

    private var negativeButtonClic = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
    }

    private var neutralButtonClic = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, "No action", Toast.LENGTH_SHORT).show()
    }

    /**
     * Perform action when clic on back Button : Exit application
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }

    /**
     * Persorm Action on resume application
     */
    override fun onResume() {
        super.onResume()
        //Clear text
        edtUserName.setText("")
        edtPassword.setText("")
        edtUserName.isFocusable = true
    }

    /**
     * Execute connexion to DataBase when Connections Parameters are valides
     */
    private fun callConnection(connectionClass : ConnectionClass) : Boolean
    {
        var isConnect = false
        val connect = Factory.executeConnection(connectionClass)
        Factory.CONN_VALUE = connect

        if (connect != null)
            isConnect = true
        return isConnect
    }

    private fun buildDialog(context: Context, title: String, message: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok",DialogInterface.OnClickListener(function = positiveButtonClic))
        builder.setNegativeButton(android.R.string.no, negativeButtonClic)
        builder.setNeutralButton("No action", neutralButtonClic)
        builder.show()
    }
}
