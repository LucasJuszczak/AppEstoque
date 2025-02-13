package com.example.appestoque.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appestoque.constants.DataBaseConstants

class ProductDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION){

    companion object{
        private const val NAME = "estoquedb"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.PRODUCT.TABLE_NAME + " (" +
                DataBaseConstants.PRODUCT.COLUMNS.ID + " integer primary key autoincrement, " +
                DataBaseConstants.PRODUCT.COLUMNS.NAME + " text, " +
                DataBaseConstants.PRODUCT.COLUMNS.PRESENCE + " integer, " +
                DataBaseConstants.PRODUCT.COLUMNS.QUANTITY + " text, " +
                DataBaseConstants.PRODUCT.COLUMNS.VALUE + " text); "
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { }
}