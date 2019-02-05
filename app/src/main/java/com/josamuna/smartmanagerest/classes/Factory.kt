package com.josamuna.smartmanagerest.classes

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager

//Declare Factory with one instance like Signleton
object Factory {
    var FRAGMENT_VALUE_ID : Int = 0
    var CONN_VALUE : Connection? = null
    fun executeConnection(connectionClass:ConnectionClass) : Connection?{
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        val conn : Connection = DriverManager.getConnection(String.format("jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s",
            connectionClass.serverName, connectionClass.database, connectionClass.userID, connectionClass.password))

        return conn
    }

    /**
     * Allow to close connection to Database
     */
    fun closeConnection(conn: Connection?){
        if(conn != null){
            if(!conn.isClosed)
                conn.close()
        }
    }
}