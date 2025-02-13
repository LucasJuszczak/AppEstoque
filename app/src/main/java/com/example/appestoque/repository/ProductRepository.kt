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

    fun insert(product: ProductModel): Boolean {
        return try{
            val db = productDataBase.writableDatabase

            val presence = if (product.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.NAME, product.name)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY, product.quantity)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.VALUE, product.value)

            db.insert(DataBaseConstants.PRODUCT.TABLE_NAME, null, values)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun update(product: ProductModel): Boolean{
        return try{
            val db = productDataBase.writableDatabase

            val presence = if (product.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE, presence)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.NAME, product.name)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY, product.quantity)
            values.put(DataBaseConstants.PRODUCT.COLUMNS.VALUE, product.value)

            val selection = DataBaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(product.id.toString())

            db.update(DataBaseConstants.PRODUCT.TABLE_NAME, values, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    fun delete(id: Int): Boolean {
        return try{
            val db = productDataBase.writableDatabase

            val selection = DataBaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.PRODUCT.TABLE_NAME, selection, args)
            true

        } catch (e: Exception) {
            return false
        }
    }

    @SuppressLint("Recycle", "Range")
    fun get(id: Int): ProductModel? {

        var product: ProductModel? = null
        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.PRODUCT.COLUMNS.ID,
                DataBaseConstants.PRODUCT.COLUMNS.NAME,
                DataBaseConstants.PRODUCT.COLUMNS.PRESENCE,
                DataBaseConstants.PRODUCT.COLUMNS.QUANTITY,
                DataBaseConstants.PRODUCT.COLUMNS.VALUE
            )

            val selection = DataBaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(DataBaseConstants.PRODUCT.TABLE_NAME, projection, selection, args, null, null, null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.VALUE))

                    product = ProductModel(id, name, presence == 1, quantity, value)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return product
        }
        return product
    }

    @SuppressLint("Recycle", "Range")
    fun getAll(): List<ProductModel> {

        val list = mutableListOf<ProductModel>()

        try {
            val db = productDataBase.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.PRODUCT.COLUMNS.ID,
                DataBaseConstants.PRODUCT.COLUMNS.NAME,
                DataBaseConstants.PRODUCT.COLUMNS.PRESENCE,
                DataBaseConstants.PRODUCT.COLUMNS.QUANTITY,
                DataBaseConstants.PRODUCT.COLUMNS.VALUE
            )

            val cursor = db.query(
                DataBaseConstants.PRODUCT.TABLE_NAME, projection, null, null, null, null, null
            )

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.VALUE))

                    //construindo o modelo
                    val product = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(product)
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
                DataBaseConstants.PRODUCT.COLUMNS.ID,
                DataBaseConstants.PRODUCT.COLUMNS.NAME,
                DataBaseConstants.PRODUCT.COLUMNS.PRESENCE,
                DataBaseConstants.PRODUCT.COLUMNS.QUANTITY,
                DataBaseConstants.PRODUCT.COLUMNS.VALUE
            )

            //Recuperando com SQL
            val cursor =
                db.rawQuery("SELECT id, name, presence, quantity, value FROM product WHERE presence == 1", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.VALUE))

                    //Construindo o modelo
                    val product = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(product)
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
                DataBaseConstants.PRODUCT.COLUMNS.ID,
                DataBaseConstants.PRODUCT.COLUMNS.NAME,
                DataBaseConstants.PRODUCT.COLUMNS.PRESENCE,
                DataBaseConstants.PRODUCT.COLUMNS.QUANTITY,
                DataBaseConstants.PRODUCT.COLUMNS.VALUE
            )

            val cursor = db.rawQuery("SELECT id, name, presence, quantity, value FROM product WHERE presence == 0", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.PRESENCE))
                    val quantity = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.QUANTITY))
                    val value = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRODUCT.COLUMNS.VALUE))

                    //Construindo o modelo
                    val product = ProductModel(id, name, presence == 1, quantity, value)
                    list.add(product)
                }
            }
            cursor.close()
        } catch (e: Exception) {
            return list
        }
        return list
    }
}