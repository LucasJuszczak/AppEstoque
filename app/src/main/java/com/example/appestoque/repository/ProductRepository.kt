package com.example.appestoque.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.appestoque.constants.DataBaseConstants
import com.example.appestoque.model.ProductModel

class ProductRepository private constructor(context: Context) {

    private val productDataBase = ProductDataBase(context)

    companion object {
        private lateinit var repository: ProductRepository

        fun getInstance(context: Context): ProductRepository {
            if (!Companion::repository.isInitialized){
                repository = ProductRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: ProductModel): Boolean {
        return try{
            val db = productDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.QUANTITY, guest.quantity)
            values.put(DataBaseConstants.GUEST.COLUMNS.VALUE, guest.value)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun update(guest: ProductModel): Boolean{
        return try{
            val db = productDataBase.writableDatabase

            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.QUANTITY, guest.quantity)
            values.put(DataBaseConstants.GUEST.COLUMNS.VALUE, guest.value)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun delete(id: Int): Boolean {
        return try{
            val db = productDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int): ProductModel? {

        var guest: ProductModel? = null
        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.QUANTITY,
                DataBaseConstants.GUEST.COLUMNS.VALUE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args, null, null, null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.VALUE))

                    guest = ProductModel(id, name, presence == 1, quantity, value)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return guest
        }
        return guest
    }

    @SuppressLint("Recycle", "Range")
    fun getAll(): List<ProductModel> {

        val list = mutableListOf<ProductModel>()

        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.QUANTITY,
                DataBaseConstants.GUEST.COLUMNS.VALUE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection, null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.VALUE))

                    //construindo o modelo
                    val guest = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(guest)
                }
            }
            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getPresence(): List<ProductModel> {

        val list = mutableListOf<ProductModel>()

        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.QUANTITY,
                DataBaseConstants.GUEST.COLUMNS.VALUE
            )

            //Recuperando com SQL
            val cursor =
                db.rawQuery("SELECT id, name, presence, quantity, value FROM guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.VALUE))

                    //Construindo o modelo
                    val guest = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(guest)
                }
            }
            cursor.close()
        }catch (e: Exception){
            return list
        }
        return list
    }

    @SuppressLint("Recycle", "Range")
    fun getAbsent(): List<ProductModel> {

        val list = mutableListOf<ProductModel>()

        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE,
                DataBaseConstants.GUEST.COLUMNS.QUANTITY,
                DataBaseConstants.GUEST.COLUMNS.VALUE
            )

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence == 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.VALUE))

                    //Construindo o modelo
                    val guest = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(guest)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
}