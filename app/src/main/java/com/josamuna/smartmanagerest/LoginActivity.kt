package com.josamuna.smartmanagerest

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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

/**
 * Activity Login for login user
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pgProgress: ProgressBar = findViewById<View>(R.id.pgbStatus) as ProgressBar

        //Initialise preferences to default valueA
        ApplicationPreferences.init(applicationContext, "default_pref")
        val connect = ConnectionClass()

        //Verifie preferences
        if (verifiePreferences(connect)) {

            //Handle acton on clic button Login
            btnLogin.setOnClickListener {
                connect.userID = edtUserName.text.toString()
                connect.password = edtPassword.text.toString()

                pgProgress.visibility = View.VISIBLE

                //Call connexion in a CountDownTimer with 3 Sec Timeout when user parameters are not null
                if (connect.userID.isNotEmpty() || connect.password.isNotEmpty()) {
                    activateField(false)
                    object : CountDownTimer(4000, 1000) {
                        override fun onFinish() {
                            executeConnection(pgProgress, connect)
                        }

                        override fun onTick(p0: Long) {
                        }
                    }.start()
                } else {
                    executeConnection(pgProgress, connect)
                }
            }
        } else {
            //Open Activity Preferences
            val intent = Intent(applicationContext, DatabaseActivity::class.java)
            startActivity(intent)
        }
    }

    //Allow to check preferences and affect these value to ConnectionClass
    private fun verifiePreferences(connect: ConnectionClass): Boolean {
        val value: Boolean

        if (ApplicationPreferences.preferences.getString("database_pref", null).isNullOrEmpty() &&
            ApplicationPreferences.preferences.getString("server_pref", null).isNullOrEmpty()
        ) {
            value = false
        } else {
            connect.database = ApplicationPreferences.preferences.getString("database_pref", null)
            connect.serverName = ApplicationPreferences.preferences.getString("server_pref", null)
            value = true
        }

        return value
    }

    //Disable connection fields
    private fun activateField(value: Boolean) {
        edtUserName.isEnabled = value
        edtPassword.isEnabled = value
    }

    /**
     * Perform action when clic on back Button : Exit application
     */
    override fun onBackPressed() {
        try {
            Factory.closeConnection(Factory.CONN_VALUE)
        } catch (e: IllegalArgumentException) {
            Factory.makeLogMessage("Error", "Error when close connection ${e.message}", LogType.ERROR)
        } catch (e: Exception) {
            Factory.makeLogMessage("Error", "Error not managed when close connection ${e.message}", LogType.ERROR)
        }

        moveTaskToBack(true)
        exitProcess(-1)
    }

    /**
     * Perform Action on resume application
     */
    override fun onResume() {
        super.onResume()
        //Clear text
        edtUserName.setText("")
        edtPassword.setText("")
        edtUserName.isFocusable = true
    }

    private fun executeConnection(pgBar: ProgressBar, connectionClass: ConnectionClass) {
        //Execute connexion to DataBase
        try {
            if (callConnection(connectionClass)) {
                //Open Main Activity
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        } catch (e: SQLException) {
            Factory.makeLogMessage(
                "Error Connection",
                "Unable to connect to Database,\nCheck username and password ${e.message}",
                LogType.ERROR
            )
            Factory.makeToastMessage(
                applicationContext,
                "Unable to connect to Database,\nCheck username and password",
                ToastType.LONG
            )
        } catch (e: ClassNotFoundException) {
            Factory.makeLogMessage(
                "Error Connection",
                "Unable to connect to Database,\nDriver not found ${e.message}",
                LogType.ERROR
            )
            Factory.makeToastMessage(
                applicationContext,
                "Unable to connect to Database,\nDriver not found",
                ToastType.LONG
            )
        } catch (e: Exception) {
            Factory.makeLogMessage("Error", "Unable to connect to Database,\n ${e.message}", LogType.ERROR)
            Factory.makeToastMessage(
                applicationContext,
                "Unable to connect to Database,\n ${e.message}",
                ToastType.LONG
            )
        }

        activateField(true)
        pgBar.visibility = View.GONE
    }

    /**
     * Execute connexion to DataBase when Connections Parameters are valides
     */
    private fun callConnection(connectionClass: ConnectionClass): Boolean {
        var isConnect = false
        val connect: Connection? = Factory.executeConnection(connectionClass)

        Factory.CONN_VALUE = connect

        if (connect != null)
            isConnect = true

        return isConnect
    }
}
