package ee5415gp9.com.trafficestimator;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;


public class HomeFragment extends Fragment {

    DatabaseHelper eta_Db;

    private ListView mListView;
    public ListAdapter mListAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
        final String[] lines = getResources().getStringArray(R.array.line_array);
        final String[] destinations = getResources().getStringArray(R.array.destination_array);
        final String[] startpoints = getResources().getStringArray(R.array.stoppoint_array);
        final String[] datareturn = getResources().getStringArray(R.array.datareturn_array);
        final int[] mtsicon = {
                R.drawable.mtr_logo, R.drawable.kmb_logo, R.drawable.lwb_logo,
                R.drawable.citybus_logo, R.drawable.kmb_logo, R.drawable.citybus_logo };

        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        mListView = (ListView) view.findViewById(R.id.listview);
        mListAdapter = new ListAdapter(getActivity(), lines, destinations, startpoints, datareturn, mtsicon);
        mListView.setAdapter(mListAdapter);
//        return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabaseHelper();


//        viewAllRecords();

    }

    private void initDatabaseHelper(){
        if(eta_Db == null){
            eta_Db = new DatabaseHelper(getActivity());
        }
    }

    public void viewAllRecords() {
        Cursor res = eta_Db.getRouteData();
        if (res.getCount() == 0) {
//            results.setText("No record in the Student Database.");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
//            buffer.append("Id :"+ res.getString(0)+"\n");
//            buffer.append("First Name :"+ res.getString(1)+"\n");
//            buffer.append("Last Name :"+ res.getString(2)+"\n");
//            buffer.append("Marks :"+ res.getString(3)+"\n\n");
            System.out.println("Company:" + res.getString(0));
            System.out.println("Route:" + res.getString(1));
            System.out.println("Bound:" + res.getString(2));
            System.out.println("Destination:" + res.getString(10));
        }
//        results.setText(buffer.toString());
    }


}

