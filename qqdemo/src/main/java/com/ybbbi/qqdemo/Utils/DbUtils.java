package com.ybbbi.qqdemo.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ybbbi.qqdemo.database.ContactOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ybbbi
 * 2020-01-12 15:48
 */
public class DbUtils {
    private static Context context;

    /**
     * 需要上下文在init获取
     */
    public static void init(Context context) {
        DbUtils.context = context.getApplicationContext();
    }

    /**
     * 查询数据库
     *
     * @param username
     * @return
     */
    public static List<String> initContact(String username) {
        List<String> list = new ArrayList<>();
        if (context == null) {
            throw new RuntimeException("dbutils must be init");
        } else {
            //执行查询操作
            ContactOpenHelper contactOpenHelper = new ContactOpenHelper(context);
            SQLiteDatabase database = contactOpenHelper.getReadableDatabase();
            Cursor cursor = database.query("contact_info", new String[]{"contact"}, "username=?", new String[]{username}, null, null, null);
            while (cursor.moveToNext()) {
                String contact = cursor.getString(0);
                list.add(contact);
            }
            cursor.close();
            database.close();

        }

        return list;
    }

    /**
     * 更新数据库
     */
    public static void updateContacts(String username, List<String> contacts) {
        if (context == null) {

            throw new RuntimeException("dbutils must be init");
        } else {
            ContactOpenHelper contactOpenHelper = new ContactOpenHelper(context);
            SQLiteDatabase database = contactOpenHelper.getReadableDatabase();
            database.beginTransaction();
            try {
                //删除旧的数据
                database.execSQL("delete from contact_info where username=" + username);
                //添加新的数据

                for (String contact : contacts) {
                    //将传递的数据更新
                    database.execSQL("insert into contact_info (contact)values (" +contact +")");
                }

                database.setTransactionSuccessful();

            } finally {
                database.endTransaction();
            }
        }
    }
}
