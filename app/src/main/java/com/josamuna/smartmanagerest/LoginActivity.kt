package com.josamuna.smartmanagerest

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.josamuna.smartmanagerest.classes.ConnectionClass
import com.josamuna.smartmanagerest.classes.Factory
import java.sql.SQLException
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private val database = "gestion_labo_DB"
    private val server = "JOSAM"
    private var user = ""
    private var passwod = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<View>(R.id.btnLogin) as Button
        val btnCancel = findViewById<View>(R.id.btnExit) as Button
        val txtUser = findViewById<View>(R.id.edtUserName) as EditText
        val txtPwd = findViewById<View>(R.id.edtPassword) as EditText

        //Handle acton on button Login
        btnLogin.setOnClickListener{
            user = txtUser.text.toString()
            passwod = txtPwd.text.toString()

            val connect = ConnectionClass()
            connect.serverName = server
            connect.database = database
            connect.userID = user
            connect.password = passwod

            //Execute connexion to DataBase
            try {
                if (callConnection(connect)) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }catch (e : SQLException) {
                Log.e("Error Connection", e.message)
                Toast.makeText(applicationContext,"Enable to connect to Database,\nCheck username and password",
                    Toast.LENGTH_LONG).show()
//                buildDialog(context, "Connection to Database",
//                    "Enable to open connection to Database,\n " + e.message)
            }catch (e : ClassNotFoundException) {
                Log.e("Error Connection", e.message)
                Toast.makeText(applicationContext,"Enable to connect to Database,\nDriver not found",
                    Toast.LENGTH_LONG).show()
            }catch (e : Exception) {
                Log.e("Error", e.message)
                Toast.makeText(applicationContext,"Enable to connect to Database,\n" + e.message,
                    Toast.LENGTH_LONG).show()
            }
        }

        //Handle acton on button Exit
        btnCancel.setOnClickListener{
            onBackPressed()
        }
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

    fun exitLogin() {
        //Close application
        super.onBackPressed()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }

    private fun callConnection(connectionClass : ConnectionClass) : Boolean
    {
        var isConnect = false
        if (Factory.executeConnection(connectionClass) != null)
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
