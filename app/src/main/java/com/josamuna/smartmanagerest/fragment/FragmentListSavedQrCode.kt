package com.josamuna.smartmanagerest.fragment


import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteTableLockedException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.adapter.CustomQrCodeListAdapter
import com.josamuna.smartmanagerest.adapter.QrCodeListAdapter
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.helper.SqliteDBHelper
import com.josamuna.smartmanagerest.interfaces.ISharedFragment
import kotlinx.android.synthetic.main.fragment_list_saved_qr_code.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FragmentListSavedQrCode : Fragment(), ISharedFragment {

    private lateinit var sqliteDB: SqliteDBHelper
    private lateinit var adapter: QrCodeListAdapter
    private lateinit var customAdapter: CustomQrCodeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_saved_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialise SqliteDBHelper object
        sqliteDB = SqliteDBHelper(context!!)

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_history)

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.ListQrCode

        //Get the current FragmentManager
        Factory.FRAGMENTMANAGER = fragmentManager

        //Get the current ClipboadManager that enable to copy some text in the clipboard
        Factory.CLIPBOARDMANAGER = activity!!.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        btn_add_qrcode.setOnClickListener {
            val fragment = FragmentCaptureMain()
            openFragment(fragment, R.id.framelayout)
        }

        //Show QrCode List in RecycleView
        try {
            viewQrCodeSaved()
        }catch (e: SQLiteTableLockedException){
            Factory.makeLogMessage("Error", "Unable to load QrCode, table is locked\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(context!!,"Unable to load QrCode informations, table is locked", ToastType.Long )
        }catch (e: SQLiteException){
            Factory.makeLogMessage("Error", "Unable to load QrCode informations, Database is not ready\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(context!!,"Unable to load QrCode informations, Database is not ready", ToastType.Long )
        }catch (e:Exception){
            Factory.makeLogMessage("Error", "Unable to load QrCode informations\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(context!!,"Unable to load QrCode informations", ToastType.Long )
        }

        //Perform action on change text in SearchView Control
        searchListQrCode.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(txt: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(txt: String?): Boolean {

                customAdapter.filter(textFilter = txt)
                return false
            }
        })
    }

    /**
     * Allow to view QrCode
     */
    private fun viewQrCodeSaved(){
        val lstQrCode = sqliteDB.getQrCodes(context!!)
        adapter = QrCodeListAdapter(context!!, lstQrCode)
        rvListQrCode.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rvListQrCode.adapter = adapter

        customAdapter = CustomQrCodeListAdapter(context!!, lstQrCode)
    }

    /**
     * Allow to open a new Fragment from another
     */
    override fun openFragment(fragment: Fragment, fragment_id: Int) {
        fragmentManager?.beginTransaction()?.replace(fragment_id, fragment)?.commit()
    }

    override fun onResume() {
        super.onResume()

        //Set FragmentCaptureMain value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.ListQrCode
    }
}
