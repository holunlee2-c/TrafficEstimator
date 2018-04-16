package ee5415gp9.com.trafficestimator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;                  //Test View For Netvigation page
   // private ListView mListViewSearchResult;       //List View for Output of Search Result
    private ListView mListView;
    public ListAdapter mListAdapter;

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
                    mTextMessage.setText(R.string.title_nearest_station);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_favourite);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_search);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        final String[] destinations = getResources().getStringArray(R.array.destination_array);
        final String[] startpoints = getResources().getStringArray(R.array.stoppoint_array);
        final String[] datareturn = getResources().getStringArray(R.array.datareturn_array);
        final int[] mtsicon = {
                R.drawable.mtr_logo, R.drawable.kmb_logo, R.drawable.lwb_logo,
                R.drawable.citybus_logo, R.drawable.kmb_logo, R.drawable.citybus_logo };

        mListView = (ListView) findViewById(R.id.listview);
        mListAdapter = new ee5415gp9.com.trafficestimator.ListAdapter(MainActivity.this, destinations, startpoints, datareturn, mtsicon);
        mListView.setAdapter(mListAdapter);




        //*******************************************************************************
        //************* define the Click Action of List Item (of list View) *************
        //*******************************************************************************


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //****  Display  Toast Message  ****
                Toast.makeText(MainActivity.this, destinations[i]+" "+startpoints[i],
                        Toast.LENGTH_SHORT).show();

                //****  DEFINE THE CLICK ACTION HERE  ****


            }
        });




        //*****************************************************************************
        //                      Listener for Naviagation View
        //List View For Output of Search Result
        //*****************************************************************************
//        mTextMessage = (TextView) findViewById(R.id.nearest_station);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
