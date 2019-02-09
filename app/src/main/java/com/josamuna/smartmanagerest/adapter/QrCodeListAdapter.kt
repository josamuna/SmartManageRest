package com.josamuna.smartmanagerest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.fragment.FragmentSearch
import com.josamuna.smartmanagerest.model.QrCodeData
import kotlinx.android.synthetic.main.qrcode_item_row.view.*

class QrCodeListAdapter(context: Context, private val qrObject: ArrayList<QrCodeData>): RecyclerView.Adapter<QrCodeListAdapter.ViewHolder>() {

//    private val ctx = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.qrcode_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return qrObject.size
    }

    /**
     * Bind ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: QrCodeData = qrObject[position]
        holder.txtQrCodeContent.text = data.qrcodeContent
        holder.dateSaved.text = data.strDate
        holder.hourSaved.text = data.strTime

        holder.itemView.setOnClickListener {

            //Here we must open a new fragment (Fragment Search and set Text value that allow to make search)
            Factory.TEXT_MESSAGE = data.qrcodeContent

            val fragment = FragmentSearch()

            Factory.FRAGMENTMANAGER?.beginTransaction()?.replace(R.id.framelayout, fragment)?.commit()

//            Factory.makeToastMessage(ctx, "${Factory.TEXT_MESSAGE}", ToastType.Long)
        }
    }

    /**
     * A ViewHolder class for Adapter
     */
    class ViewHolder(items: View): RecyclerView.ViewHolder(items){
        val txtQrCodeContent = items.txtQrCodeContent!!
        val dateSaved = items.txtDateSaved!!
        val hourSaved = items.txtTimeSaved!!
    }

    /**
     * Allow to return view that has contain searched element
     */
}