package com.josamuna.smartmanagerest.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteTableLockedException
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.communicator.Communicator
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.helper.SqliteDBHelper
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import com.josamuna.smartmanagerest.model.QrCodeData
import kotlinx.android.synthetic.main.fragment_capture_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Main Capture Fragment allow to save that or make capture
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class FragmentCaptureMain : Fragment(), ISharedFragment {

    //    private var ctx: Context? = null
    private var sqliteDB: SqliteDBHelper? = null
    private var qrcodeDataObject = QrCodeData()

    //Default value for QrCode Scanned
    private var stringValue: String = "QrCode Has not been scanned !!"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Get current context
//        ctx = container!!.context

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capture_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialise SqliteDBHelper object
        sqliteDB = SqliteDBHelper(context!!)

        //Allow to use scrollbar in TextView
        val qrCapturedCode: TextView = view.findViewById<View>(R.id.qrCodeCaptured) as TextView
        qrCapturedCode.movementMethod = ScrollingMovementMethod()

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_capture_main)

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.CAPTURE_MAIN

        qrCapturedCode.text = stringValue

        //Perform Action when clic on capture button
        btn_capture.setOnClickListener {
            val fragmentCapture = FragmentCapture()
            openFragment(fragmentCapture, R.id.framelayout)
        }

        //Using Shared Data through ViewModel Class
        val model: Communicator = ViewModelProviders.of(activity!!).get(Communicator::class.java)
        model.modelMessage.observe(this, Observer<Any> { t ->
            if (t.toString().isNotEmpty())
                qrCapturedCode.text = t.toString()
        })

        //Perform Action when saving scanned QrCode
        btn_save_captured.setOnClickListener {
            //Save captured QRCode
            try {
                if (qrCapturedCode.text.toString().isNotEmpty()) {
                    qrcodeDataObject.qrcodeContent = qrCapturedCode.text.toString()

                    //Set Date and Time using difference way according Sdk Min Version
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val currentDateTime = LocalDateTime.now()

                        val formatterDate: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY")
                        val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

                        qrcodeDataObject.strDate = currentDateTime.format(formatterDate)
                        qrcodeDataObject.strTime = currentDateTime.format(formatterTime)

                    } else {
                        val currentDateTime = Date()

                        val formatterDate = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                        val formatterTime = SimpleDateFormat("HH:mm:ss", Locale.FRENCH)

                        qrcodeDataObject.strDate = formatterDate.format(currentDateTime)
                        qrcodeDataObject.strTime = formatterTime.format(currentDateTime)
                    }

                    saveQRCodeCaptured()

                    Factory.makeToastMessage(context!!, "QrCode Saved successfully", ToastType.LONG)
                }
            } catch (e: SQLiteTableLockedException) {
                Factory.makeLogMessage("Error", "Unable to save QrCode, table is locked\n ${e.message}", LogType.ERROR)
                Factory.makeToastMessage(context!!, "Unable to save QrCode, table is locked", ToastType.LONG)
            } catch (e: SQLiteException) {
                Factory.makeLogMessage(
                    "Error",
                    "Unable to save QrCode, Database is not ready\n ${e.message}",
                    LogType.ERROR
                )
                Factory.makeToastMessage(context!!, "Unable to save QrCode, Database is not ready", ToastType.LONG)
            } catch (e: Exception) {
                Factory.makeLogMessage("Error", "Unable to save QrCode\n ${e.message}", LogType.ERROR)
                Factory.makeToastMessage(context!!, "Unable to save QrCode", ToastType.LONG)
            }
        }
    }

    //Allow to save scanned QRCode with QRCode Scanner
    private fun saveQRCodeCaptured() {
        sqliteDB!!.insertQrCode(qrcodeDataObject)
//        Factory.makeLogMessage("Error", "Connection Before ${Factory.CONN_VALUE.toString()}", LogType.Error)
    }

    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    override fun onResume() {
        super.onResume()

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.CAPTURE_MAIN
    }
}
