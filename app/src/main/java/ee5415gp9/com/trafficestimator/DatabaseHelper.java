package ee5415gp9.com.trafficestimator;

/**
 * Created by anton on 16/4/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String onlineDB_LINK = "http://antony9655.mynetgear.com/ETA/master_info.db";

    public static final String DATABASE_NAME = "master_info.db";
    public static final String TABLE_ROUTES = "master_routes";
    public static final String TABLE_STOPS = "master_stops";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRSTNAME";
    public static final String COL_3 = "LASTNAME";
    public static final String COL_4 = "MARKS";

    private static String DB_PATH = "";
    private final Context mContext;

    private SQLiteDatabase master_database;


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;

        boolean db_exist = checkDataBase();
        if(db_exist)
        {
            System.out.println("Database exists");
            openDataBase();
        }
        else
        {
            System.out.println("Database doesn't exist");
            try {
                createDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table if not exists " + TABLE_ROUTES + "(TASK TEXT)");
//        db.execSQL("create table if not exists " + TABLE_STOPS + "(TASK TEXT)");
        try {
            createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOPS);
        onCreate(db);
    }


    public void openDataBase()
    {
        //Open database
        String mypath = DB_PATH + DATABASE_NAME;
        master_database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    //Check DB exists or not exist
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }


    public void createDatabase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
//            try
//            {
                //Copy the database from assests
//                downloadDatabase();
                new DownloadFileFromURL().execute(onlineDB_LINK);
//            }
//            catch (IOException mIOException)
//            {
//                throw new Error("ErrorCopyingDataBase");
//            }
        }
    }


    //Copy the database from Online Server
//    private void downloadDatabase() throws IOException
//    {
////        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
////        String outFileName = DB_PATH + DATABASE_NAME;
////        FileOutputStream mOutput = new FileOutputStream(outFileName);
////        byte[] mBuffer = new byte[1024];
////        int mLength;
////        while ((mLength = mInput.read(mBuffer))>0)
////        {
////            mOutput.write(mBuffer, 0, mLength);
////        }
////        mOutput.flush();
////        mOutput.close();
////        mInput.close();
//        int count;
//        try {
//            URL url = new URL(onlineDB_link);
//
//            URLConnection conection = url.openConnection();
//            conection.connect();
//            // getting file length
//            int lenghtOfFile = conection.getContentLength();
//
//            // input stream to read file - with 8k buffer
//            InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//            File dir = new File(DB_PATH);
//            dir.mkdirs();
//            File f = new File(DB_PATH + DATABASE_NAME);
//
//            OutputStream output = new FileOutputStream(f);
//
//            byte data[] = new byte[1024];
//
//            long total = 0;
//            while ((count = input.read(data)) != -1) {
//                total += count;
//
//                // writing data to file
//                output.write(data, 0, count);
//            }
//
//            // flushing output
//            output.flush();
//
//            // closing streams
//            output.close();
//            input.close();
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();

                System.out.println("Downloading");
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
//                FileOutputStream output = openFileOutput(storedDB_NAME, MODE_PRIVATE);

                File dir = new File(DB_PATH);
                dir.mkdirs();
                File f = new File(DB_PATH + DATABASE_NAME);

                OutputStream output = new FileOutputStream(f);

                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }



        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");
        }

    }

    // Method to show all the records
    public Cursor getRouteData() {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES, null);
        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES + " where service_type = 1 or service_type IS null ORDER BY COMPANY, ROUTE + 0 , ROUTE", null);
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES + " where service_type = 1 or service_type IS null ORDER BY COMPANY, ROUTE", null);
        return res;
    }

    // Method to show all the records
    public Cursor getRouteData_Search(String company, String route) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES, null);
        String sql = "select * from " + TABLE_ROUTES + " where route LIKE '" + route + "%' AND company LIKE '" + company + "%' AND (service_type = 1 or service_type IS null) ORDER BY COMPANY, ROUTE + 0 , ROUTE";
//        String sql = "select * from " + TABLE_ROUTES + " where route LIKE '" + route + "%' AND company LIKE '" + company + "%' AND (service_type = 1 or service_type IS null) ORDER BY COMPANY, ROUTE";
        System.out.println(sql);
        Cursor res = db.rawQuery(sql,null);
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES + " where route LIKE '" + route + "%' AND company LIKE '" + company + "%' AND (service_type = 1 or service_type IS null) ORDER BY COMPANY, ROUTE", null);
        return res;
    }

    // Method to show all the records
    public Cursor getRouteStop_Search(String company, String route, String bound, String service_type, String rdv) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES, null);
        String sql =
                "select * " +
                "from " + TABLE_STOPS + " " +
                "where company LIKE '" + company + "' " +
                "AND " +
                "(route = '" + route + "' AND bound = '" + bound + "' AND service_type = '" + service_type + "' " +
                "OR " +
                "route = '" + route + "' AND bound = '" + bound + "' " +
                "OR " +
                "rdv = '" + rdv + "') " +
                "ORDER BY CAST(stopseq AS INTEGER)";

        System.out.println("668 :" + sql);

        Cursor res = db.rawQuery(sql,null);

        System.out.println("234");

        return res;
    }

    public synchronized void close(){
        if(master_database != null){
            master_database.close();
        }
        super.close();
    }

//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
////        db.execSQL("create table IF NOT EXISTS " + TABLE_ROUTES + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
////                "FIRSTNAME TEXT, LASTNAME TEXT, MARKS INTEGER)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
//        onCreate(db);
//    }
//
//    // Method to insert a record to the database
//    public boolean insertData(String firstname, String lastname, String marks) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_2,firstname);
//        contentValues.put(COL_3,lastname);
//        contentValues.put(COL_4,marks);
//        long result = db.insert(TABLE_ROUTES, null, contentValues);
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }
//
//    // Method to show all the records
//    public Cursor getAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_ROUTES, null);
//        return res;
//    }
//
//    // Method to update a record
//    public boolean updateData(String id, String firstname, String lastname, String marks)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,firstname);
//        contentValues.put(COL_3,lastname);
//        contentValues.put(COL_4,marks);
//        db.update(TABLE_ROUTES, contentValues, "ID = ?", new String[] {id});
//        return true;
//    }
//
//    // Method to delete a record
//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_ROUTES, "ID = ?", new String[] {id});
//    }
}
