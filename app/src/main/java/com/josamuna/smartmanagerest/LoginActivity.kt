package com.josamuna.smartmanagerest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.josamuna.smartmanagerest.classes.ApplicationPreferences
import com.josamuna.smartmanagerest.classes.ConnectionClass
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import kotlinx.android.synthetic.main.activity_login.*
import java.sql.Connection
import java.sql.SQLException
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity(){

    private var user: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pgProgress: ProgressBar = findViewById<View>(R.id.pgbStatus) as ProgressBar

        //Initialise preferences to default valueA
        ApplicationPreferences.init(applicationContext, "default_pref")

        //Verifie preferences
        if(verifiePreferences()) {

            //Handle acton on clic button Login
            btnLogin.setOnClickListener {

                //Activate progressbar
                pgProgress.visibility = View.VISIBLE
//                pgbStatus.isIndeterminate = true

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
                        //When connection to Database success, disable progressbar after open Activity
                        pgProgress.visibility = View.GONE

                        //Open Main Activity
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        pgProgress.visibility = View.GONE
                    }
                } catch (e: SQLException) {
                    Factory.makeLogMessage("Error Connection", "Unable to connect to Database,\nCheck username and password ${e.message}", LogType.Error)
                    Factory.makeToastMessage(applicationContext,"Unable to connect to Database,\nCheck username and password", ToastType.Long)
                    pgProgress.visibility = View.GONE
                } catch (e: ClassNotFoundException) {
                    Factory.makeLogMessage("Error Connection", "Unable to connect to Database,\nDriver not found ${e.message}", LogType.Error)
                    Factory.makeToastMessage(applicationContext,"Unable to connect to Database,\nDriver not found", ToastType.Long)
                    pgProgress.visibility = View.GONE
                } catch (e: Exception) {
                    Factory.makeLogMessage("Error", "Unable to connect to Database,\n ${e.message}", LogType.Error)
                    Factory.makeToastMessage(applicationContext,"Unable to connect to Database,\n ${e.message}", ToastType.Long)
                    pgProgress.visibility = View.GONE
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

    /**
     * Perform action when clic on back Button : Exit application
     */
    override fun onBackPressed() {
        try {
            Factory.closeConnection(Factory.CONN_VALUE)
        }catch (e: IllegalArgumentException){
            Factory.makeLogMessage("Error", "Error when close connection ${e.message}", LogType.Error)
        }catch (e: Exception){
            Factory.makeLogMessage("Error", "Error not managed when close connection ${e.message}", LogType.Error)
        }

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
        val connect: Connection? = Factory.executeConnection(connectionClass)

//        if(Factory.CONN_VALUE != null) {
//            Factory.CONN_VALUE?.close()
//
//            connect = Factory.executeConnection(connectionClass)
//            Factory.CONN_VALUE = connect
//
//            if (connect != null)
//                isConnect = true
//        }else{
//            connect = Factory.executeConnection(connectionClass)
//            Factory.CONN_VALUE = connect
//
//            if (connect != null)
//                isConnect = true
//        }
        Factory.CONN_VALUE = connect

        if (connect != null)
            isConnect = true

        return isConnect
    }
}
