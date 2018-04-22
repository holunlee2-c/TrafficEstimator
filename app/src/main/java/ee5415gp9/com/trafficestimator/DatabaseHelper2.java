package ee5415gp9.com.trafficestimator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper2 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "local.db";
    public static final String TABLE_HISTORY = "search_history";
    public static final String TABLE_FAVOURITE = "search_favourite";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "pk_from_master";
//    public static final String COL_3 = "LASTNAME";
//    public static final String COL_4 = "MARKS";

    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, 1); // super(context, name, factor, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_HISTORY + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pk_from_master INTEGER)");

        db.execSQL("create table if not exists " + TABLE_FAVOURITE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pk_from_master INTEGER)");
//                "pk_from_master INTEGER, LASTNAME TEXT, MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(db);
    }

    // Method to insert a record to the database
    public boolean insertData(int in_pk_from_master) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,in_pk_from_master);
//        contentValues.put(COL_3,lastname);
//        contentValues.put(COL_4,marks);
        long result = db.insert(TABLE_HISTORY, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Method to insert a record to the database
    public boolean insertData_favourite(int in_pk_from_master) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,in_pk_from_master);
//        contentValues.put(COL_3,lastname);
//        contentValues.put(COL_4,marks);
        long result = db.insert(TABLE_FAVOURITE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    // Method to show all the records
    public Cursor getHistoryAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_HISTORY + "order by ID DESC", null);
        Cursor res = db.rawQuery("select * from " + TABLE_HISTORY + " ORDER BY ID DESC", null);
        return res;
    }

    public Cursor getFavouriteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_HISTORY + "order by ID DESC", null);
        Cursor res = db.rawQuery("select * from " + TABLE_FAVOURITE + " ORDER BY ID DESC", null);
        return res;
    }

    // Method to update a record
    public boolean updateData(String id, int in_pk_from_master)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,in_pk_from_master);
//        contentValues.put(COL_3,lastname);
//        contentValues.put(COL_4,marks);
        db.update(TABLE_HISTORY, contentValues, "ID = ?", new String[] {id});
        return true;
    }
    // Method to delete a record
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_HISTORY, "ID = ?", new String[] {id});
    }
}
