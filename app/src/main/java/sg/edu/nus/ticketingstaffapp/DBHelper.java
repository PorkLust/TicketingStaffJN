package sg.edu.nus.ticketingstaffapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int databaseVersion = 1;
    public static final String databaseName = "custDB";

    public static final String tableName = "QRCode";
    public static final String columnName1 = "_id";
    public static final String columnName2 = "imgInfo";

    private static final String SQLite_CREATE =
            "CREATE TABLE " + tableName + "(" + columnName1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName2 + " TEXT NOT NULL);";

    private static final String SQLite_DELETE = "DROP TABLE IF EXISTS" + tableName;

    //constructor
    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //note: our upgrade policy here is simply to discard the data and start all over
        db.execSQL(SQLite_DELETE);
        onCreate(db);
    }
}
