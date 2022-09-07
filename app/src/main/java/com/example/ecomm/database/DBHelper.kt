package com.example.ecomm.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

//    override fun DataBaseH

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " + TITLE_COL + " TEXT, " + IMAGE_COL + " TEXT, " + NAME_COL + " TEXT, "+ CATEGORY_COL + " TEXT, " + QUANTITY_COL + " TEXT, " + PRICE_COL + " TEXT " + ");")
        println(query)
        Log.e("Response", query)
        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
//        val query = ("DELETE FROM" + TABLE_NAME + "WHERE" + ID_COL)
//        db.execSQL("DELETE FROM" + TABLE_NAME + "WHERE"  )
        onCreate(db)
    }

    @SuppressLint("Range")
    fun onDelete(cartList : List<String>, title : String,){
        val db = this.writableDatabase
//        db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id))
//        db.rawQuery("DELETE FROM $TABLE_NAME " +
//                "WHERE $NAME_COl IN (\n" +
//                "   SELECT $NAME_COl FROM\n" +
//                "   $TABLE_NAME WHERE $NAME_COl=$id LIMIT 1\n" +
//                ")", null)]
        var id = ""
        val query = "SELECT $QUANTITY_COL FROM " + TABLE_NAME + " WHERE $TITLE_COL = '$title' "
        val c:Cursor = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            id = c.getString(0)
            Log.e("id value", id)
        }
            if(id.toInt()>1){
                db.execSQL("UPDATE $TABLE_NAME SET $QUANTITY_COL = $QUANTITY_COL -1 WHERE $TITLE_COL = '$title';")
            }
            else{
                db.execSQL("DELETE FROM $TABLE_NAME WHERE $TITLE_COL='$title'")
            }

        var tableString = java.lang.String.format("Table %s:\n", TABLE_NAME)
        val allRows = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (allRows.moveToFirst()) {
            val columnNames = allRows.columnNames
            do {
                for (name in columnNames) {
                    tableString += String.format(
                        "%s: %s\n", name,
                        allRows.getString(allRows.getColumnIndex(name))
                    )
                }
                tableString += "\n"
            } while (allRows.moveToNext())
        }

        Log.e("Table",tableString)
    }

    // This method is for adding data in our database
    fun addName(title : String, image: String, name: String, category: String, age : String, quantity : String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(TITLE_COL, title)
        values.put(IMAGE_COL, image)
        values.put(QUANTITY_COL, quantity)
        values.put(PRICE_COL, age)
        values.put(CATEGORY_COL, category)
        values.put(NAME_COL, name)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    @SuppressLint("Range")
    fun addQuantity(cartList : List<String>,image : String,name : String,category : String, title : String, price : String, quantity: String){
        val values = ContentValues()
        var id = ""
        val db = this.writableDatabase
        // we are inserting our values
        // in the form of key-value pair
        val query = "SELECT $QUANTITY_COL FROM " + TABLE_NAME + " WHERE $TITLE_COL = '$title' "
        val c:Cursor = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            id = c.getString(0)
            Log.e("Quantity", id)
            Log.e("Quantity", "${id.toInt()+1}")
        }
        val query2 = "SELECT $ID_COL FROM " + TABLE_NAME + " WHERE $TITLE_COL = '$title' "
        val c2:Cursor = db.rawQuery(query, null)
        if (c.moveToFirst()) {
            id = c.getString(0)
            Log.e("Quantity", id)
//            Log.e("Quantity", "${id.toInt()+1}")
        }
        val strSQL =
            "UPDATE $TABLE_NAME SET $QUANTITY_COL = 2 WHERE $TITLE_COL = '$title'"
        for(i in cartList){
            if(title==i){
                Log.e("Run", "I have run")
                db.execSQL("UPDATE $TABLE_NAME SET $QUANTITY_COL = $QUANTITY_COL +1 WHERE $TITLE_COL = '$title';")
                val query = "SELECT $QUANTITY_COL FROM " + TABLE_NAME + " WHERE $TITLE_COL = '$title' "
                println(db.rawQuery(query, null))
                val c:Cursor = db.rawQuery(query, null)
                if (c.moveToFirst()) {
                    var valuw = c.getString(0)
                    Log.e("Quantity", valuw.toString())
                }
            }
            else{
                values.put(TITLE_COL, title)
                values.put(IMAGE_COL, image)
                values.put(PRICE_COL, price)
                values.put(QUANTITY_COL, quantity)
                values.put(CATEGORY_COL, category)
                values.put(NAME_COL, name)
            }
        }
        var tableString = java.lang.String.format("Table %s:\n", TABLE_NAME)
        val allRows = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (allRows.moveToFirst()) {
            val columnNames = allRows.columnNames
            do {
                for (name in columnNames) {
                    tableString += String.format(
                        "%s: %s\n", name,
                        allRows.getString(allRows.getColumnIndex(name))
                    )
                }
                tableString += "\n"
            } while (allRows.moveToNext())
        }

        Log.e("Table",tableString)



        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database


        // all values are inserted into database
//        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "ECOMM2"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "cart_table"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val TITLE_COL = "productTitle"
        val NAME_COL = "userName"
        val IMAGE_COL = "profuctImage"
        val CATEGORY_COL = "profuctCategory"

        // below is the variable for age column
        val PRICE_COL = "productPrice"
        val QUANTITY_COL = "productQuantity"
    }
}