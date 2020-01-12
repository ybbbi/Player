package com.ybbbi.qqdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * ybbbi
 * 2020-01-12 15:44
 */
public class ContactOpenHelper extends SQLiteOpenHelper {
    public ContactOpenHelper(@Nullable Context context){
        super(context,"contact",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table contact_info(_id integer primary key autoincrement,username varchar(20),contact varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
