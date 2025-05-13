package com.intprog32.caterspot30.Connections

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(
    private val context: Context?,
    databaseName: String? = "Users.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, databaseName, null, 1) {

    private var TABLE_NAME = "users"
    private var COLUMN_EMAIL = "email"
    private var COLUMN_PASSWORD = "password"
    private var COLUMN_FNAME = "first_name"
    private var COLUMN_LNAME = "last_name"
    private var COLUMN_CATERER_NAME = "caterer_name"
    private var COLUMN_DESCRIPTION = "description"

    override fun onCreate(db: SQLiteDatabase?) {
        val query: String = "CREATE TABLE" + TABLE_NAME +
                "(" + COLUMN_CATERER_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DESCRIPTION + " TEXT);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, i: Int, i1: Int) {
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }

    fun addBook(cateringName: String,  description: String){
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_CATERER_NAME, cateringName)
        cv.put(COLUMN_DESCRIPTION, description)

        val result: Long = db.insert(TABLE_NAME, null, cv)
        if(result.toInt() == -1){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show()
        }
    }
}