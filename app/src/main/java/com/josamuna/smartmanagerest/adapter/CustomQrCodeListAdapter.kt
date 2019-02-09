package com.josamuna.smartmanagerest.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.model.QrCodeData
import java.util.*

class CustomQrCodeListAdapter(context: Context, qrObject: ArrayList<QrCodeData>): BaseAdapter() {

    //Local variables
    var ctx = context
    var codeQr = qrObject

    //Store array in temp Array to be use later
    private var tempArrayQr = ArrayList(codeQr)

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var myview = view
        val holder: ViewHolder

        if(view == null){
            val myInflater = (ctx as Activity).layoutInflater
            myview = myInflater!!.inflate(R.layout.qrcode_item_row, parent, false)

            holder = ViewHolder()
            holder.txtQr = myview.findViewById(R.id.txtQrCodeContent) as TextView
            holder.txtDate = myview.findViewById(R.id.txtDateSaved) as TextView
            holder.txtTime = myview.findViewById(R.id.txtTimeSaved) as TextView

            myview.tag = holder
        }else{
            holder = myview!!.tag as ViewHolder
        }

        val dataPosition = codeQr[position]

        holder.txtQr!!.text = dataPosition.qrcodeContent
        holder.txtDate!!.text = dataPosition.strDate
        holder.txtTime!!.text = dataPosition.strTime

        return myview!!
    }

    override fun getItem(position: Int): Any {
        return codeQr[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return codeQr.size
    }

    class ViewHolder{
        var txtQr: TextView? = null
        var txtDate: TextView? = null
        var txtTime: TextView? = null
    }

    fun filter(textFilter: String?){
        val text = textFilter!!.toLowerCase(Locale.getDefault())

        codeQr.clear()

        if(text.isEmpty()){
            codeQr.addAll(tempArrayQr)
        }else{
            for (i in 0 until tempArrayQr.size){
                if(tempArrayQr[i].qrcodeContent.toLowerCase(Locale.getDefault()).contains(text)){
                    codeQr.add(tempArrayQr[i])
                }
            }
        }

        //Notify datachanged
        notifyDataSetChanged()
    }
}