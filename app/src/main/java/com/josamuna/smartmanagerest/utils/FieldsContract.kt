package com.josamuna.smartmanagerest.utils

/**
 * FieldsContract Class that allow to specify all Database Fields and others elements kind off Database name
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class FieldsContract {
    companion object {
        var database_name: String = "gestion_labo_local_DB"

        /**
         * Tables declaration
         * In this project we will use only one table to store scanned QrCode
         */
        const val table_qrcode: String = "qrcode"

        //Here we declare all fields to be use with that table
        const val field_id: String = "id"
        const val field_qrcode_text: String = "qrcode_text"
        const val field_date_saved: String = "date_saved"
        const val field_time_saved: String = "time_saved"

        //Script SQL for table qrcode
        const val create_table_qrcode: String = "create table $table_qrcode" +
                "(" +
                "$field_id integer primary key autoincrement," +
                "$field_qrcode_text varchar(100)," +
                "$field_date_saved varchar(10)," +
                "$field_time_saved varchar(8)" +
                ")"

        //Script drop table qrcode
        const val drop_table_qrcode: String = "drop table if exists $table_qrcode"
    }
}