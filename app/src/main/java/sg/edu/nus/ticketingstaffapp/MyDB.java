package sg.edu.nus.ticketingstaffapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {

    DBHelper DBHelper;
    SQLiteDatabase db;
    final Context context;

    public MyDB(Context ctx) {
        this.context = ctx;
        DBHelper = new DBHelper(this.context);
    }

    public MyDB open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertRecord(String qrcode) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBHelper.columnName2, qrcode);
        System.out.println("DB: " + qrcode);

        return db.insert(DBHelper.tableName, null, initialValues);
    }

    public Cursor getAllRecords() {
        return db.query(DBHelper.tableName, new String[] {DBHelper.columnName1, DBHelper.columnName2},
                null, null, null, null, null);
    }
}
