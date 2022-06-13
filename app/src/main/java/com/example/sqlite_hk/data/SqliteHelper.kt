package com.example.sqlite_hk.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite_hk.cmmvariable.Cmmvariable

class SqliteHelper(context: Context) :
    SQLiteOpenHelper(context, Cmmvariable.DB_Name, null, Cmmvariable.DB_Version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = String.format(
            "Create table %s(" +
                    "%s integer primary key," +
                    "%s text," +
                    "%s text",
            Student.TableDB_Name,
            Student.Column_ID,
            Student.Column_FullName,
            Student.Column_Address
        )
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(String.format("drop table if exists %s", Student.TableDB_Name))
        onCreate(db)
    }

    fun getStudents(): ArrayList<Student> {
        val result: ArrayList<Student> = ArrayList()
        val db = this.readableDatabase
        val cursor: Cursor =
            db.rawQuery(String.format("select * frorm %s", Student.TableDB_Name), null)
        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(Student.Column_ID)
                val fullNameIndex = cursor.getColumnIndex(Student.Column_FullName)
                val addressIndex = cursor.getColumnIndex(Student.Column_Address)

                val curItem = Student(
                    cursor.getInt(idIndex),
                    cursor.getString(fullNameIndex),
                    cursor.getString(addressIndex)
                )
                result.add(curItem)
            } while (cursor.moveToNext())

        }
        return result
    }

    fun insertStudent(ob: Student): Long {
        val cv = ContentValues()
        cv.put(Student.Column_ID, ob.id)
        cv.put(Student.Column_FullName, ob.address)
        cv.put(Student.Column_Address, ob.address)
        val db = this.writableDatabase
        val result = db.insert(Student.TableDB_Name, null, cv)
        db.close()
        return result
    }

    fun updateStudent(obj: Student): Int {
        val cv = ContentValues()
        cv.put(Student.Column_ID, obj.id)
        cv.put(Student.Column_FullName, obj.fullName)
        cv.put(Student.Column_Address, obj.address)

        val db = this.writableDatabase
        val whereClause = String.format("%s = %s", Student.Column_ID, obj.id)
        val result = db.update(Student.TableDB_Name, cv, whereClause, null)
        db.close()
        return result
    }

    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase
        val whereClause = String.format("%s =%s", Student.Column_ID, id)
        val result = db.delete(Student.TableDB_Name, whereClause, null)
        db.close()
        return result
    }
}