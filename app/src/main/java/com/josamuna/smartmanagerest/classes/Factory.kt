package com.josamuna.smartmanagerest.classes

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager

//Declare Factory with one instance like Signleton
object Factory {

    fun executeConnection(connectionClass:ConnectionClass) : Connection?{
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val conn : Connection

        Class.forName("net.sourceforge.jtds.jdbc.Driver")
        conn = DriverManager.getConnection(String.format("jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s",
            connectionClass.serverName, connectionClass.database, connectionClass.userID, connectionClass.password))
        return conn
    }
}