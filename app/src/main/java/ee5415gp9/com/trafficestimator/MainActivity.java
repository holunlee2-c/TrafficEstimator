package ee5415gp9.com.trafficestimator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
//public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;                  //Test View For Netvigation page
   // private ListView mListViewSearchResult;       //List View for Output of Search Result
    private ListView mListView;
    public ListAdapter mListAdapter;


    DatabaseHelper eta_Db;
    public static final String onlineDB_LINK = "http://antony9655.mynetgear.com/ETA/master_info.db";
    public static final String storedDB_NAME = "master_info.db";
    private ProgressDialog pDialog,mProgressDialog;

    private BottomNavigationView navigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;



    //********************************************************************
    //2018-04-09:
    //Navigation View:
    //3 Buttons: Home, Favourite, Search
    //
    //
    //********************************************************************

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_nearest_station);
//                    return true;
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_searchHistory:
//                    mTextMessage.setText(R.string.navigation_favourite);
//                    return true;
                    fragment = new SearchHistoryFragment();
                    break;
                case R.id.navigation_search:
//                    mTextMessage.setText(R.string.navigation_search);
//                    return true;
                    fragment = new SearchFragment();
                    break;
            }
//            return false;
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
            return true;
        }
    };



//    public List<MasterRoutes> viewAllRecords() {
//
//        List<MasterRoutes> mr_list = new ArrayList<MasterRoutes>();
//
//        Cursor res = eta_Db.getRouteData();
//        if (res.getCount() == 0) {
////            results.setText("No record in the Student Database.");
//            return null;
//        }
//        while (res.moveToNext()) {
////            System.out.println("Company:" + res.getString(0));
//////            System.out.println("Route:" + res.getString(1));
//////            System.out.println("Bound:" + res.getString(2));
//////            System.out.println("Destination:" + res.getString(10));
//            int ID = res.getInt(0);
//            String COMPANY = res.getString(1);
//            String ROUTE = res.getString(2);
//            String BOUND = res.getString(3);
//            String SERVICE_TYPE = res.getString(4);
//            String rdv = res.getString(5);
//            String ETA_ID = res.getString(6);
//            String source_chi = res.getString(7);
//            String source_eng = res.getString(8);
//            String dest_chi = res.getString(9);
//            String dest_eng = res.getString(10);
//
//            MasterRoutes mr = new MasterRoutes();
//
//            mr.setID(ID);
//            mr.setCOMPANY(COMPANY);
//            mr.setROUTE(ROUTE);
//            mr.setBOUND(BOUND);
//            mr.setSERVICE_TYPE(SERVICE_TYPE);
//            mr.setRdv(rdv);
//            mr.setETA_ID(ETA_ID);
//            mr.setSource_chi(source_chi);
//            mr.setSource_eng(source_eng);
//            mr.setDest_chi(dest_chi);
//            mr.setDest_eng(dest_eng);
//
//            mr_list.add(mr);
//        }
////        results.setText(buffer.toString());
//        return mr_list;
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     ActionBar actionBar = getSupportActionBar();
   //     actionBar.hide();
        setContentView(R.layout.activity_main);

//        studentDb = new DatabaseHelper(this);
        eta_Db = new DatabaseHelper(this);
//        viewAllRecords();


/**
        //********************************************************************
        //2018-04-09:
        //List View For Output of Search Result
        //Structure: Single Row i.e. Image,text,text(time)
        //********************************************************************

        //Get Reference to the listView
        mListViewSearchResult = (ListView) findViewById(R.id.listview);

        final Integer[] tspicon = { R.drawable.kmb_logo, R.drawable.mtr_logo, R.drawable.citybus_logo, R.drawable.lwb_logo}; //Icon of Transit Service Provider (TSP)
        final String[] destination = {"Wan Chai", "Tseun Wan", "HK International Airport", "Terminal 1" };
        //final String[] destination = {"Russia", "Canada", "United States", "China" };

        //Initialise the Adapter, and Bind the Adapter to the list View
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, destination);
        mListViewSearchResult.setAdapter(adapter);

        // Set  On Click Listener, for user's trigger
        mListViewSearchResult.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                    Toast.makeText(getApplicationContext(), destination[i], Toast.LENGTH_LONG).show();
                    }
                });
*/

//        final String[] destinations = getResources().getStringArray(R.array.destination_array);
//        final String[] startpoints = getResources().getStringArray(R.array.stoppoint_array);
//        final String[] datareturn = getResources().getStringArray(R.array.datareturn_array);
//        final int[] mtsicon = {
//                R.drawable.mtr_logo, R.drawable.kmb_logo, R.drawable.lwb_logo,
//                R.drawable.citybus_logo, R.drawable.kmb_logo, R.drawable.citybus_logo };
//
//        mListView = (ListView) findViewById(R.id.listview);
//        mListAdapter = new ee5415gp9.com.trafficestimator.ListAdapter(MainActivity.this, destinations, startpoints, datareturn, mtsicon);
//        mListView.setAdapter(mListAdapter);


//
//        List<MasterRoutes> mr_list = viewAllRecords();
//
//        mListView = (ListView) findViewById(R.id.listview);
//        mListAdapter = new ListViewAdapter(this, mr_list);
//        mListView.setAdapter(mListAdapter);
//



        //*******************************************************************************
        //************* define the Click Action of List Item (of list View) *************
        //*******************************************************************************


//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                //****  Display  Toast Message  ****
//                Toast.makeText(MainActivity.this, destinations[i]+" "+startpoints[i],
//                        Toast.LENGTH_SHORT).show();
//
//                //****  DEFINE THE CLICK ACTION HERE  ****
//            }
//        });




        //*****************************************************************************
        //                      Listener for Naviagation View
        //List View For Output of Search Result
        //*****************************************************************************
//        mTextMessage = (TextView) findViewById(R.id.nearest_station);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

//        navigationView.inflateMenu(R.menu.navigation);
        fragmentManager = getSupportFragmentManager();

        Fragment mFragment = null;
        mFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, mFragment).commit();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.findViewById(R.id.navigation_home).setBackgroundResource(R.drawable.home_btn);
        navigation.findViewById(R.id.navigation_search).setBackgroundResource(R.drawable.search_btn);
        navigation.findViewById(R.id.navigation_searchHistory).setBackgroundResource(R.drawable.history_btn);

//        File dbFile = new File(DB_PATH + DATABASE_NAME);
//        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
//        return dbFile.exists();


        //??? Stop if server close
//        new DownloadFileFromURL().execute(onlineDB_LINK);

        //If Connected Wifi, download and update the DB
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            new DownloadFileFromURL().execute(onlineDB_LINK);
        }

        //If no connection, return AlertDialog
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//        if (netInfo == null && !netInfo.isConnectedOrConnecting())
//            checkNetWorkConn();

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
        }
        else
            returnNoNetWorkConn();
    }

    public void returnNoNetWorkConn()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Network Connection");

    // Set up the buttons
        // set dialog message
        builder
                .setMessage("No Network Connection is found,\n" +
                        "Cannot retrieve ETA data!")
                .setCancelable(false)
                .setPositiveButton("Exit App",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Continue",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }


//  Useful, Don't delete
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
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

                String outFileName =  "/data/data/"
                        +getApplicationContext().getPackageName()
                        + "/databases/";

                File dir = new File(outFileName);
                dir.mkdirs();
                File f = new File(outFileName + storedDB_NAME);

                OutputStream output = new FileOutputStream(f);

                System.out.println("Path:" + getFilesDir());

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

            pDialog.dismiss();
        }

    }
}
