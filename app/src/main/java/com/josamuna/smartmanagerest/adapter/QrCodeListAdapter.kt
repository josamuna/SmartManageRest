package com.josamuna.smartmanagerest.adapter

import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteTableLockedException
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.classes.Factory.FRAGMENTMANAGER
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.fragment.FragmentListSavedQrCode
import com.josamuna.smartmanagerest.fragment.FragmentSearch
import com.josamuna.smartmanagerest.fragment.FragmentShowContentListItem
import com.josamuna.smartmanagerest.helper.SqliteDBHelper
import com.josamuna.smartmanagerest.model.QrCodeData
import kotlinx.android.synthetic.main.qrcode_item_row.view.*

class QrCodeListAdapter(context: Context, private val qrObject: ArrayList<QrCodeData>): RecyclerView.Adapter<QrCodeListAdapter.ViewHolder>() {

    private val ctx: Context = context
    private var idRecord: Int = 0
    private var deleteAllRecord: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.qrcode_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return qrObject.size
    }

    /**
     * Bind ViewHolder
     */
    override fun onBindViewHolder(holder: QrCodeListAdapter.ViewHolder, position: Int) {
        val data: QrCodeData = qrObject[position]

        holder.txtIdSaved.text = data.intId.toString()
        holder.txtQrCodeContent.text = data.qrcodeContent
        holder.dateSaved.text = data.strDate
        holder.hourSaved.text = data.strTime

        holder.itemView.setOnLongClickListener {
            //Use this three lines that show popup without Custom style
//            val popupMenu = PopupMenu(ctx, holder.buttonMenuSelect)
//            popupMenu.inflate(R.menu.option_menu_list)

            //Use this five line to support popup with Custom style
            val contextWrapper = ContextThemeWrapper(ctx, R.style.CustomContextMenu)
            val popupMenu = PopupMenu(contextWrapper, holder.buttonMenuSelect)

            Factory.setForceShowIcon(popupMenu)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.option_menu_list, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener{item ->
                when(item.itemId){
                    R.id.menuView ->{
                        Factory.TEXT_MESSAGE = data.qrcodeContent

                        val fragment = FragmentShowContentListItem()

                        FRAGMENTMANAGER?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()
                    }
                    R.id.menuSearch ->{
                        //Here we must open a new fragment (Fragment Search and set Text value that allow to make search)
                        Factory.TEXT_MESSAGE = data.qrcodeContent

                        val fragment = FragmentSearch()

                        FRAGMENTMANAGER?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()
                    }
                    R.id.menuCopy ->{
                        Factory.CLIPDATA = ClipData.newPlainText("Texte", data.qrcodeContent)
                        Factory.CLIPBOARDMANAGER?.primaryClip = Factory.CLIPDATA
                        Factory.makeToastMessage(ctx, "Text copied", ToastType.Long)
                    }
                    R.id.menuDelete ->{
                        //Get id Selected
                        idRecord = data.intId
                        deleteAllRecord = false
                        //Use Dialog to ask question for confirmation deletion
                        buildDialogOkCancel(ctx, "Delete item", "Do you want to delete this item ?")
                    }
                    R.id.menuDeleteAll ->{
                        deleteAllRecord = true
                        //Use Dialog to ask question for confirmation deletion
                        buildDialogOkCancel(ctx, "Delete items", "Do you want to delete  all saved items ?")
                    }
                }
                false
            }

            //How popup Menu
            popupMenu.show().let { false }
        }

        holder.buttonMenuSelect.setOnClickListener {
            //Use this three lines that show popup without Custom style
//            val popupMenu = PopupMenu(ctx, holder.buttonMenuSelect)
//            popupMenu.inflate(R.menu.option_menu_list)

            //Use this five lines to support popup with Custom style
            val contextWrapper = ContextThemeWrapper(ctx, R.style.CustomContextMenu)
            val popupMenu = PopupMenu(contextWrapper, holder.buttonMenuSelect)

            Factory.setForceShowIcon(popupMenu)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.option_menu_list, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener{item ->
                when(item.itemId){
                    R.id.menuView ->{
                        Factory.TEXT_MESSAGE = data.qrcodeContent

                        val fragment = FragmentShowContentListItem()

                        FRAGMENTMANAGER?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()
                    }
                    R.id.menuSearch ->{
                        //Here we must open a new fragment (Fragment Search and set Text value that allow to make search)
                        Factory.TEXT_MESSAGE = data.qrcodeContent

                        val fragment = FragmentSearch()

                        FRAGMENTMANAGER?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()
                    }
                    R.id.menuCopy ->{
                        Factory.CLIPDATA = ClipData.newPlainText("Texte", data.qrcodeContent)
                        Factory.CLIPBOARDMANAGER?.primaryClip = Factory.CLIPDATA
                        Factory.makeToastMessage(ctx, "Text copied", ToastType.Long)
                    }
                    R.id.menuDelete ->{
                        //Get id Selected
                        idRecord = data.intId
                        deleteAllRecord = false
                        //Use Dialog to ask question for confirmation deletion
                        buildDialogOkCancel(ctx, "Delete item", "Do you want to delete this item ?")
                    }
                    R.id.menuDeleteAll ->{
                        deleteAllRecord = true
                        //Use Dialog to ask question for confirmation deletion
                        buildDialogOkCancel(ctx, "Delete items", "Do you want to delete  all saved items ?")
                    }
                }
                false
            }

            //Show popup Menu
            popupMenu.show()
        }
    }

    /**
     * Alow to show DialogBox to show message
     */
    private fun buildDialogOkCancel(context: Context, title: String, message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener(positiveButtonClic))
        builder.setNegativeButton(android.R.string.no, negativeButtonClic)
//        builder.setNeutralButton("No action", neutralButtonClic)
        builder.show()
    }


    /**
     * Allow to open a new Fragment
     */
    private fun openFragment(idFragment: Int, fragment: Fragment){
        Factory.FRAGMENTMANAGER?.beginTransaction()?.replace(idFragment, fragment)?.commit()
    }

    //Perform action on clic ok
    private val positiveButtonClic = { _: DialogInterface, _: Int ->
        try {
            val dbHel = SqliteDBHelper(ctx)

            if(deleteAllRecord){
                val record: Int = dbHel.deleteAllQrCode()


                if (record > 0) {
                    //After deletion reopen the same Fragment to make data up to date

                    val fragment = FragmentListSavedQrCode()
                    openFragment(R.id.framelayout, fragment)
                } else {
                    Factory.makeToastMessage(ctx, "No deletion performed !!!", ToastType.Long)
                }
            }else {
                val record: Int = dbHel.deleteQrCode(idRecord)

                if (record > 0) {
                    //After deletion reopen the same Fragment to make data up to date

                    val fragment = FragmentListSavedQrCode()
                    openFragment(R.id.framelayout, fragment)
                } else {
                    Factory.makeToastMessage(ctx, "No deletion performed !!!", ToastType.Long)
                }
            }
        }catch (e: SQLiteTableLockedException){
            Factory.makeLogMessage("Error", "Unable to delete QrCode, table is locked\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(ctx,"Unable to delete QrCode, table is locked", ToastType.Long )
        }catch (e: SQLiteException){
            Factory.makeLogMessage("Error", "Unable to delete QrCode, Database is not ready\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(ctx,"Unable to delete QrCode, Database is not ready", ToastType.Long)
        }catch (e:Exception){
            Factory.makeLogMessage("Error", "Unable to delete QrCode\n ${e.message}", LogType.Error)
            Factory.makeToastMessage(ctx,"Unable to delete QrCode", ToastType.Long )
        }
//        Factory.makeToastMessage(ctx, android.R.string.yes.toString(), ToastType.Long)
    }

    private val negativeButtonClic = { _: DialogInterface, _: Int ->
        Factory.makeToastMessage(ctx, "Canceled action", ToastType.Long)
    }

//    private val neutralButtonClic = { _: DialogInterface, _: Int ->
//        Factory.makeToastMessage(ctx, "No action", ToastType.Long)
//    }

    /**
     * A ViewHolder class for Adapter
     */
    class ViewHolder(items: View): RecyclerView.ViewHolder(items){
        var txtIdSaved: TextView = items.txtIdSaved!!
        val txtQrCodeContent: TextView = items.txtQrCodeContent!!
        val dateSaved: TextView = items.txtDateSaved!!
        val hourSaved: TextView = items.txtTimeSaved!!
        val buttonMenuSelect: TextView = items.txtButtonOptionMenu!!
    }
}