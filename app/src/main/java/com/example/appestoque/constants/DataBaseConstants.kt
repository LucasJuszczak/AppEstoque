package com.example.appestoque.constants

class DataBaseConstants {

    object PRODUCT{
        const val ID = "productid"
        const val TABLE_NAME = "product"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
            const val QUANTITY = "quantity"
            const val VALUE = "value"
        }
    }
}