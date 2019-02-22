package com.josamuna.smartmanagerest.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.model.QrCodeData
import com.josamuna.smartmanagerest.utils.FieldsContract

class SqliteDBHelper
/**
 * That constructor initialise Database with the current context
 */(context: Context) : SQLiteOpenHelper(context, FieldsContract.database_name, null, 1) {

//    private var myContext: Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(FieldsContract.create_table_qrcode)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(FieldsContract.drop_table_qrcode)
        onCreate(db)
    }


    //**************************************//***************************************************

    /**
     * Allow to save model (QrCodeData object) into SQLite DataBase
     */
    fun insertQrCode(value_model: QrCodeData){
        val db: SQLiteDatabase = this@SqliteDBHelper.writableDatabase
        val value = ContentValues()

        value.put(FieldsContract.field_qrcode_text, value_model.qrcodeContent)
        value.put(FieldsContract.field_date_saved, value_model.strDate)
        value.put(FieldsContract.field_time_saved, value_model.strTime)

        db.insert(FieldsContract.table_qrcode, null, value)
        db.close()
    }

    /**
     * Allow to get one record QrCode item by id passed has parameter, and the context | This methode is not yet used
     */
//    fun getQrCode(context:Context, idKey: Int): QrCodeData {
//        val data = QrCodeData()
//        val db = this@SqliteDBHelper.writableDatabase
//        val query = "select id,qrcode_text,date_saved,time_saved from ${FieldsContract.table_qrcode} where id=$idKey"
//
//        val cursor = db.rawQuery(query, null)
//
//        if(cursor.count > 0){
//            if (cursor.moveToNext()){
//                setQrCodeValue(data, cursor)
//
//                Factory.makeToastMessage(context,"${cursor.count} records found", ToastType.Long)
//            }
//        }else
//            Factory.makeToastMessage(context,"No records found !!!", ToastType.Long)
//
//        cursor.close()
//        db.close()
//        return data
//    }

    /**
     * Factorisation function for multiple use
     */
    private fun setQrCodeValue(data: QrCodeData, cursor: Cursor) {
        data.intId = cursor.getInt(cursor.getColumnIndex(FieldsContract.field_id))
        data.qrcodeContent = cursor.getString(cursor.getColumnIndex(FieldsContract.field_qrcode_text))
        data.strDate = cursor.getString(cursor.getColumnIndex(FieldsContract.field_date_saved))
        data.strTime = cursor.getString(cursor.getColumnIndex(FieldsContract.field_time_saved))
    }

    /**
     * Allow to return all record in table qrcode has a ArrayList
     */
    fun getQrCodes(context: Context): ArrayList<QrCodeData>{
        val listQrCode = ArrayList<QrCodeData>()
        val db: SQLiteDatabase = this@SqliteDBHelper.writableDatabase
        val query = "select id,qrcode_text,date_saved,time_saved from ${FieldsContract.table_qrcode}"

        val cursor: Cursor = db.rawQuery(query, null)

        if(cursor.count > 0){
            Factory.makeToastMessage(context,"${cursor.count} records found", ToastType.Long)

            //Fetch to find record
            while (cursor.moveToNext()){
                val qrcode = QrCodeData()
                setQrCodeValue(qrcode, cursor)
                listQrCode.add(qrcode)
            }
        }else
            Factory.makeToastMessage(context,"No records found !!!", ToastType.Long)

        cursor.close()
        db.close()

        return listQrCode
    }

    fun deleteQrCode(idKey: Int): Int{
        val recordDeleted: Int
        val db: SQLiteDatabase = this@SqliteDBHelper.writableDatabase
        recordDeleted = db.delete(FieldsContract.table_qrcode, "id=$idKey", null)
        db.close()
        return recordDeleted
    }

    /**
     * Allow to delete all records into SQLite Database
     */
    fun deleteAllQrCode(): Int{
        val recordDeleted: Int
        val db: SQLiteDatabase = this@SqliteDBHelper.writableDatabase
        recordDeleted = db.delete(FieldsContract.table_qrcode, "1=1", null)
        db.close()
        return recordDeleted
    }
}