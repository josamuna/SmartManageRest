package com.josamuna.smartmanagerest.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.model.QrCodeData
import java.util.*
import kotlin.collections.ArrayList

/**
 * Custom class Adapter for manipulate ReclyclerView
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class CustomQrCodeListAdapter(context: Context, qrObject: ArrayList<QrCodeData>) : BaseAdapter() {

    //Local variables
    private var ctx: Context = context
    private var codeQr: ArrayList<QrCodeData> = qrObject

    //Store array in temp Array to be use later
    private var tempArrayQr: ArrayList<QrCodeData> = ArrayList(codeQr)

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var myview: View? = view
        val holder: ViewHolder

        if (view == null) {
            val myInflater: LayoutInflater = (ctx as Activity).layoutInflater
            myview = myInflater.inflate(R.layout.qrcode_item_row, parent, false)

            holder = ViewHolder()

            holder.txtId = myview.findViewById(R.id.txtIdSaved) as TextView
            holder.txtQr = myview.findViewById(R.id.txtQrCodeContent) as TextView
            holder.txtDate = myview.findViewById(R.id.txtDateSaved) as TextView
            holder.txtTime = myview.findViewById(R.id.txtTimeSaved) as TextView

            myview.tag = holder
        } else {
            holder = myview!!.tag as ViewHolder
        }

        val dataPosition: QrCodeData = codeQr[position]
        holder.txtId!!.text = dataPosition.intId.toString()
        holder.txtQr!!.text = dataPosition.qrcodeContent
        holder.txtDate!!.text = dataPosition.strDate
        holder.txtTime!!.text = dataPosition.strTime

        return myview!!
    }

    /**
     * Return the current item selected in ReclyclerView
     */
    override fun getItem(position: Int): Any {
        return codeQr[position]
    }

    /**
     * Return Item Id
     */
    override fun getItemId(p0: Int): Long {
        return 0
    }

    /**
     * Allow to return item count in RecyclerView object
     */
    override fun getCount(): Int {
        return codeQr.size
    }

    class ViewHolder {
        var txtId: TextView? = null
        var txtQr: TextView? = null
        var txtDate: TextView? = null
        var txtTime: TextView? = null
    }

    /**
     * Allow to perform quick search in a EditText
     */
    fun filter(textFilter: String?) {
        val text = textFilter!!.toString().toLowerCase()

        codeQr.clear()

        if (text.isEmpty()) {
            codeQr.addAll(tempArrayQr)
        } else {
            for (i: Int in 0 until tempArrayQr.size) {
                if (tempArrayQr[i].qrcodeContent.toLowerCase(Locale.getDefault()).contains(text)) {
                    codeQr.add(tempArrayQr[i])
                }
            }
        }

        //Notify data changed
        notifyDataSetChanged()
    }
}