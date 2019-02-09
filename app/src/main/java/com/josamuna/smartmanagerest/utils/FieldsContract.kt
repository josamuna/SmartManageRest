package com.josamuna.smartmanagerest.utils

/**
 * This class allow to specifiels all Database Fiels ans others elements kind off Database name
 */
class FieldsContract {
    companion object {
        var database_name = "gestion_labo_local_DB"

        /**
         * Tables declaration
         * In this project we will use only one table to store scanned QrCode
         */
        const val table_qrcode: String = "qrcode"

        //Here we declare all fiels to be use with that table
        const val field_id: String = "id"
        const val field_qrcode_text: String = "qrcode_text"
        const val field_date_saved: String = "date_saved"
        const val field_time_saved: String = "time_saved"

        //Script SQL for table qrcode
        const val create_table_qrcode = "create table $table_qrcode" +
                "(" +
                "$field_id integer primary key autoincrement," +
                "$field_qrcode_text varchar(100)," +
                "$field_date_saved varchar(10)," +
                "$field_time_saved varchar(8)" +
                ")"

        //Script drop table qrcode
        const val drop_table_qrcode = "drop table if exists $table_qrcode"
    }
}