package cp.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Chantal on 19.09.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "message.db";
    private static final String TABLE_MESSAGE = "message";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_SENDER = "sender";
    private static final String COLUMN_ID = "_id";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("test", "createdb");
        String query = "CREATE TABLE " + TABLE_MESSAGE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + ", " +
                COLUMN_DATE + " TEXT "+ ", " +
                COLUMN_MESSAGE + " TEXT "+ ", " +
                COLUMN_SENDER + " INTEGER "+
                ");";
        Log.i("test", "createdb: "+query);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int neu) {
        Log.i("test", "upgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    public void addMessage(Message message){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, message.get_date());
        values.put(COLUMN_MESSAGE, message.get_text());
        values.put(COLUMN_SENDER, message.get_sender());
        Log.i("test", "valuesDate: " + values.toString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_MESSAGE, null, values);
        db.close();
    }

    public String dbToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Log.i("test", "toString");

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("date"))!=null){
                dbString += c.getString(c.getColumnIndex("date")) + ", ";
                dbString += c.getString(c.getColumnIndex("message")) + ", ";
                dbString += c.getInt(c.getColumnIndex("sender"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public Cursor getCursor(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        return c;
    }
}
