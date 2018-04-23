package ee5415gp9.com.trafficestimator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class HomeFragment extends Fragment {

    DatabaseHelper eta_Db;
    DatabaseHelper2 history_Db;

    public static ProgressDialog pDialog;

    List<MasterRoutes> mr_list;
    List<MasterStops> ms_list;

    int[] ids;
    String[] companies;
    String[] lines;
    String[] bounds;
    String[] service_types;
    String[] rdvs;
    String[] eta_ids;

    String[] sources_chi;
    String[] sources_eng;
    String[] dests_chi;
    String[] dests_eng;

    String[] chi_name;
    String[] eng_name;

    String[] first_times;
    String[] first_mins;
    String[] first_dest_chi;
    String[] first_dest_eng;

//    String[] second_times;
//    String[] second_mins;
//    String[] second_dest_chi;
//    String[] second_dest_eng;
//
//    String[] third_times;
//    String[] third_mins;
//    String[] third_dest_chi;
//    String[] third_dest_eng;


    private TextView emptyView;

    private ListView mListView;
    public ListAdapter mListAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_home, container, false);

        mr_list = viewAllFavouriteRecords();
        try {
            ms_list = viewAllFavouriteRecords2();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        emptyView = (TextView) view.findViewById(R.id.emptyView);
        mListView = (ListView) view.findViewById(R.id.listview);



        if(mr_list.size() > 0) {
            System.out.println("208: null" + mr_list.size());
            mListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

        ids = new int[mr_list.size()];
        companies = new String[mr_list.size()];
        lines = new String[mr_list.size()];


        bounds = new String[mr_list.size()];
        service_types = new String[mr_list.size()];
        rdvs = new String[mr_list.size()];
        eta_ids = new String[mr_list.size()];

        sources_chi = new String[mr_list.size()];
        sources_eng = new String[mr_list.size()];
        dests_chi = new String[mr_list.size()];
        dests_eng = new String[mr_list.size()];

        chi_name = new String[ms_list.size()];
        eng_name = new String[ms_list.size()];

        first_times = new String[ms_list.size()];
        first_mins = new String[ms_list.size()];
        first_dest_eng = new String[ms_list.size()];

//        second_times = new String[ms_list.size()];
//        second_mins = new String[ms_list.size()];
//        second_dest_eng = new String[ms_list.size()];
//
//        third_times = new String[ms_list.size()];
//        third_mins = new String[ms_list.size()];
//        third_dest_eng = new String[ms_list.size()];

        int[] mtsicon = new int[mr_list.size()];
        int[] mtbg = new int[mr_list.size()];


        for(int i = 0; i < mr_list.size(); i++) {
            ids[i] = mr_list.get(i).getID();
            companies[i] = mr_list.get(i).getCOMPANY();
            lines[i] = mr_list.get(i).getROUTE();
            bounds[i] = mr_list.get(i).getBOUND();
            service_types[i] = mr_list.get(i).getSERVICE_TYPE();
            rdvs[i] = mr_list.get(i).getRdv();
            eta_ids[i] = mr_list.get(i).getETA_ID();

            sources_chi[i] = mr_list.get(i).getSource_chi();
            sources_eng[i] = mr_list.get(i).getSource_eng();
            dests_chi[i] = mr_list.get(i).getDest_chi();
            dests_eng[i] = mr_list.get(i).getDest_eng();

            if(companies[i].equals("KMB")) {
                mtsicon[i] = R.drawable.kmb_logo;
                mtbg[i] = R.drawable.listitem_kmb;
            }
            else if(companies[i].equals("LWB")) {
                mtsicon[i] = R.drawable.lwb_logo;
                mtbg[i] = R.drawable.listitem_lwb;
            }
            else if(companies[i].equals("MTR")) {
                mtsicon[i] = R.drawable.mtr_logo_wordmark;
                mtbg[i] = R.drawable.listitem_mtr;
            }
            else if(companies[i].equals("NWFB")) {
                mtsicon[i] = R.drawable.newbus_logo;
                mtbg[i] = R.drawable.listitem_newbus;
            }
            else if(companies[i].equals("NLB")) {
                mtsicon[i] = R.drawable.nlb_logo;
                mtbg[i] = R.drawable.listitem_newbus;
            }
            else if(companies[i].equals("CB")) {
                mtsicon[i] = R.drawable.citybus_logo;
                mtbg[i] = R.drawable.listitem_citybus;
            }
        }


        for(int i = 0; i < ms_list.size(); i++) {
//            ids[i] = ms_list.get(i).getID();
//            companies[i] = ms_list.get(i).getCOMPANY();
//            lines[i] = ms_list.get(i).getROUTE();

            chi_name[i] = ms_list.get(i).getChi_name();
            eng_name[i] = ms_list.get(i).getEng_name();

            first_times[i] = ms_list.get(i).getFirst_time();
            first_mins[i] = ms_list.get(i).getFirst_min();
            first_dest_eng[i] = ms_list.get(i).getFirst_dest_eng();

        }


//        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);
//
//        emptyView = (TextView) view.findViewById(R.id.emptyView);
//        mListView = (ListView) view.findViewById(R.id.listview);
      //  mListView.setBackgroundResource(R.drawable.background);

        mListAdapter = new ListAdapter(getActivity(), lines, dests_eng, eng_name, first_times, first_mins, mtsicon, mtbg);
//        mListAdapter = new ListAdapter(getActivity(), lines, destinations, startpoints, datareturn, mtsicon, mtbg);

        mListView.setAdapter(mListAdapter);
//        return inflater.inflate(R.layout.fragment_home, container, false);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Add into Database
                history_Db.insertData(ids[i]);
//                            Cursor cursor = history_Db.getAllData();
//
//                            if (cursor.getCount() == 0) {
//                                    System.out.println("144: No record");
//                                    return;
//                            }
//                            StringBuffer buffer = new StringBuffer();
//                            while (cursor.moveToNext()) {
////                                    buffer.append("Id :"+ res.getString(0)+"\n");
//                                    System.out.println("144 history_db, PK: " + cursor.getString(0));
//                                    System.out.println("144 history_db, PK_ID: " + cursor.getString(1));
//                            }

                System.out.println("201: " + ids[i] + ", " + companies[i] + ", " + lines[i] + ", "+ bounds[i]);


                //call Route_station Activity
                Intent intent = new Intent(getActivity(), RouteActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("company", companies[i]);
                bundle.putString("line", lines[i]);
                bundle.putString("bound", bounds[i]);
                bundle.putString("service_type", service_types[i]);
                bundle.putString("rdv", rdvs[i]);
                bundle.putString("eta_id", eta_ids[i]);
                bundle.putString("dest", dests_eng[i]);
                bundle.putString("source", sources_eng[i]);

                System.out.println("165 : " + ids[i] + ", " + lines[i] + " " + bounds[i] + " " + rdvs[i]);


                //Showing "On Loading" to user
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Loading... Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();


                intent.putExtras(bundle);
                getActivity().startActivity(intent);

//                            Intent intent = new Intent(getApplicationContext(), RouteActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("height", height);
//                            bundle.putString("weight", weight);
//                            intent.putExtras(bundle);
//                            startActivity(intent);

                //****  Display  Toast Message  ****
                Toast.makeText(getActivity(), companies[i] + " " + lines[i] + ", To: " + dests_eng[i],
                        Toast.LENGTH_SHORT).show();

                //****  DEFINE THE CLICK ACTION HERE  ****
            }
        });




            registerForContextMenu(mListView);
            return view;

        }
        else
        {
//            FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_search_history, container, false);
            System.out.println("208a: null" + mr_list.size());
            emptyView = (TextView) view.findViewById(R.id.emptyView);
            mListView = (ListView) view.findViewById(R.id.listview);

            emptyView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);

            return view;
        }



//        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position; //Returned StopSeq


        switch (item.getItemId()) {
            case R.id.delete_favourite:
                System.out.println("202, menuid :" + index);
                System.out.println("203, menu - stopid :" + ids[index]);

                int deleted_item = history_Db.deleteFavourite(String.valueOf(ids[index]));
                System.out.println("204 : " + deleted_item);

                updatedData();
                mListAdapter.notifyDataSetChanged();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabaseHelper();
    }

    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }



    private void initDatabaseHelper(){
        if(eta_Db == null){
            eta_Db = new DatabaseHelper(getActivity());
        }
        if(history_Db == null){
            history_Db = new DatabaseHelper2(getActivity());
        }
    }

//    public void viewAllRecords() {
//        Cursor res = eta_Db.getRouteData();
//        if (res.getCount() == 0) {
////            results.setText("No record in the Student Database.");
//            return;
//        }
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()) {
////            buffer.append("Id :"+ res.getString(0)+"\n");
////            buffer.append("First Name :"+ res.getString(1)+"\n");
////            buffer.append("Last Name :"+ res.getString(2)+"\n");
////            buffer.append("Marks :"+ res.getString(3)+"\n\n");
//            System.out.println("Company:" + res.getString(0));
//            System.out.println("Route:" + res.getString(1));
//            System.out.println("Bound:" + res.getString(2));
//            System.out.println("Destination:" + res.getString(10));
//        }
////        results.setText(buffer.toString());
//    }





    public List<MasterRoutes> viewAllFavouriteRecords() {


        List<MasterRoutes> mr_list = new ArrayList<MasterRoutes>();
        Cursor cursor = history_Db.getFavouriteAllData();

        while (cursor.moveToNext()) {
            int pk_id = cursor.getInt(1);
            Cursor res = eta_Db.getRouteData_SearchSelectedFavourite(pk_id);

            while (res.moveToNext()) {

                int ID = res.getInt(0);
                String COMPANY = res.getString(1);
                String ROUTE = res.getString(2);
                String BOUND = res.getString(3);
                String SERVICE_TYPE = res.getString(4);
                String rdv = res.getString(5);
                String ETA_ID = res.getString(6);
                String source_chi = res.getString(7);
                String source_eng = res.getString(8);
                String dest_chi = res.getString(9);
                String dest_eng = res.getString(10);
                String stop_id = res.getString(11);
                String stop_seq = res.getString(12);
                String chi_name = res.getString(13);
                String eng_name = res.getString(14);

                System.out.println("190, route: " + ROUTE);

                MasterRoutes mr = new MasterRoutes();

                mr.setID(ID);
                mr.setCOMPANY(COMPANY);
                mr.setROUTE(ROUTE);
                mr.setBOUND(BOUND);
                mr.setSERVICE_TYPE(SERVICE_TYPE);
                mr.setRdv(rdv);
                mr.setETA_ID(ETA_ID);
                mr.setSource_chi(source_chi);
                mr.setSource_eng(source_eng);
                mr.setDest_chi(dest_chi);
                mr.setDest_eng(dest_eng);

                mr_list.add(mr);
            }

        }
        return mr_list;
    }


    public List<MasterStops> viewAllFavouriteRecords2() throws ExecutionException, InterruptedException {

        List<MasterStops> ms_list = new ArrayList<MasterStops>();
        Cursor cursor = history_Db.getFavouriteAllData();

        while (cursor.moveToNext()) {
            int pk_id = cursor.getInt(1);
            Cursor res = eta_Db.getRouteData_SearchSelectedFavourite(pk_id);

            while (res.moveToNext()) {

                int ID = res.getInt(0);
                String COMPANY = res.getString(1);
                String ROUTE = res.getString(2);
                String BOUND = res.getString(3);
                String SERVICE_TYPE = res.getString(4);
                String rdv = res.getString(5);
                String ETA_ID = res.getString(6);
                String source_chi = res.getString(7);
                String source_eng = res.getString(8);
                String dest_chi = res.getString(9);
                String dest_eng = res.getString(10);
                String stop_id = res.getString(11);
                String stop_seq = res.getString(12);
                String chi_name = res.getString(13);
                String eng_name = res.getString(14);

                MasterStops ms = new MasterStops();

                ms.setID(ID);
                ms.setCOMPANY(COMPANY);
                ms.setROUTE(ROUTE);
                ms.setBOUND(BOUND);
                ms.setSERVICE_TYPE(SERVICE_TYPE);
                ms.setRdv(rdv);
//                ms.setStopid(stop_id);
//                ms.setStopseq(stop_seq);
                ms.setChi_name(chi_name);
                ms.setEng_name(eng_name);
//                ms.setEta_para(eta_para);


                //For Adding ETA
                String link = "";

                if (COMPANY.equals("KMB") || COMPANY.equals("LWB")) {
                    link = "http://antony9655.mynetgear.com/ETA/KMB.jsp?key=a77&key2=996b&route=" + ROUTE + "&bound=" + BOUND + "&serviceType=" + 1 + "&stop=" + stop_id + "&stop_seq=" + stop_seq;
                    System.out.println("KMB & LWB link : " + link);
                }

                if (COMPANY.equals("MTR")) {
                    //                http://antony9655.mynetgear.com/ETA/MTR.jsp?key=a77&key2=996b&line=TKL&station=TIK&lang=zh&bound=u
                    link = "http://antony9655.mynetgear.com/ETA/MTR.jsp?key=a77&key2=996b&line=" + ROUTE + "&station=" + stop_id + "&lang=zh&bound=" + BOUND.toLowerCase();
                    System.out.println("MTR link : " + link);
                }

                if (COMPANY.equals("NLB"))
                {
//              http://antony9655.mynetgear.com/ETA/NLB.jsp?key=a77&key2=996b&route=30&stop=289
                    link = "http://antony9655.mynetgear.com/ETA/NLB.jsp?key=a77&key2=996b&route=" + ETA_ID + "&stop=" + stop_id;
                    System.out.println("NLB link : " + link);
                }


                if (COMPANY.equals("NWFB") || COMPANY.equals("CB")) {
                    //http://antony9655.mynetgear.com/ETA/NWFB.jsp?key=a77&key2=996b&stopid=001280&service_no=23&bound=O&stopseq=3&rdv=23-POR-1
                    link = "http://antony9655.mynetgear.com/ETA/NWFB.jsp?key=a77&key2=996b&stopid=" + stop_id + "&service_no=" + ROUTE + "&bound=" + BOUND + "&stopseq=" + stop_seq + "&rdv=" + rdv;
                    System.out.println("NWFB & CB link : " + link);
                }

//                ???New Add
                if(isConnectedToServer(link,100))
                {
                    HttpAsyncTask hat = new HttpAsyncTask();
                    ArrayList<MasterStops> ms_AL = hat.execute(link).get();

                    //??? New Add for ETA
                    for (int i = 0; i < ms_AL.size(); i++) {
                        System.out.println("ms_AL.size()" + ms_AL.size());
                        System.out.println("eng_name :" + eng_name);
                        System.out.println("ms_AL.get(i).getFirst_min() " + ms_AL.get(i).getFirst_min());

                        ms.setStopseqs(String.valueOf(i + 1));
                        ms.setFirst_time(ms_AL.get(i).getFirst_time());
                        ms.setFirst_min(ms_AL.get(i).getFirst_min());
                        ms.setFirst_dest_chi(ms_AL.get(i).getFirst_dest_chi());
                        ms.setFirst_dest_eng(ms_AL.get(i).getFirst_dest_eng());

//                    ms.setSecond_time(ms_AL.get(i).getSecond_time());
//                    ms.setSecond_min(ms_AL.get(i).getSecond_min());
//                    ms.setSecond_dest_chi(ms_AL.get(i).getSecond_dest_chi());
//                    ms.setSecond_dest_eng(ms_AL.get(i).getSecond_dest_eng());
//
//                    ms.setThird_time(ms_AL.get(i).getThird_time());
//                    ms.setThird_min(ms_AL.get(i).getThird_min());
//                    ms.setThird_dest_chi(ms_AL.get(i).getThird_dest_chi());
//                    ms.setThird_dest_eng(ms_AL.get(i).getThird_dest_eng());

                    }
                }

                ms_list.add(ms);

                System.out.println("167: " + COMPANY + " " + ROUTE + " " + BOUND);

            }
        }
        return ms_list;
    }




    private class HttpAsyncTask extends AsyncTask<String, Void, ArrayList<MasterStops>> {

//        private final ArrayList<MasterStops> mListener;


        ArrayList<MasterStops> stopList = new ArrayList<MasterStops>();

        @Override
        protected void onPreExecute() {
            stopList.clear();
            super.onPreExecute();
//            list.setAdapter(null);

//            System.out.println("Starting download");
//            pDialog = new ProgressDialog(RouteActivity.this);
//            pDialog.setMessage("Loading... Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected ArrayList<MasterStops> doInBackground(String... urls) {
//            return GET(urls[0]);
            InputStream inputStream = null;
            String result = "";
            try {
                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(urls[0]));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            try {

                String str = "";
//                String result1 = "{\"next\":[{\"dest_chi\":\"香港\",\"min\":4,\"dest_eng\":\"Hong Kong\",\"time\":\"23:47\"},{\"dest_chi\":\"香港\",\"min\":16,\"dest_eng\":\"Hong Kong\",\"time\":\"23:59\"},{\"dest_chi\":\"香港\",\"min\":28,\"dest_eng\":\"Hong Kong\",\"time\":\"00:11\"},{\"dest_chi\":\"香港\",\"min\":41,\"dest_eng\":\"Hong Kong\",\"time\":\"00:24\"}],\"currenttime\":\"2018-04-14 23:42:44\",\"line\":\"TCL\",\"bound\":\"d\",\"station\":\"LAK\",\"company\":\"MTR\",\"lang\":\"zh\"}";

                JSONObject mainObj = new JSONObject(result);
                JSONArray next_array = mainObj.getJSONArray("next");

                String[] time_array = new String[next_array.length()];
                String[] min_array = new String[next_array.length()];
                String[] dest_chi_array = new String[next_array.length()];
                String[] dest_eng_array = new String[next_array.length()];

                MasterStops ms = new MasterStops();

                for (int i = 0; i < next_array.length(); i++) {

                    JSONObject item = next_array.getJSONObject(i);
                    String time = item.getString("time");
                    String min = item.getString("min");

                    String dest_chi = item.getString("dest_chi");
                    String dest_eng = item.getString("dest_eng");

                    //Test
                    System.out.println("item.getString(\"dest_eng\")" + item.getString("dest_eng"));
                    System.out.println("item.getString(\"time\")" + item.getString("time"));
                    System.out.println("item.getString(\"min\")" + item.getString("min"));

//                    time_array[i] = time;
//                    min_array[i] = min;
//                    dest_chi_array[i] = dest_chi;
//                    dest_eng_array[i] = dest_eng;

                    if (i == 0) {
                        ms.setFirst_time(time);
                        System.out.println("ms.setFirst_time(time);" + time + " " + urls[0]);
                        ms.setFirst_min(min);
                        ms.setFirst_dest_chi(dest_chi);
                        ms.setFirst_dest_eng(dest_eng);
                    }

//                    if (i == 1) {
//                        ms.setSecond_time(time);
//                        System.out.println("ms.setFirst_time(time);" + time + " " + urls[0]);
//                        ms.setSecond_min(min);
//                        ms.setSecond_dest_chi(dest_chi);
//                        ms.setSecond_dest_eng(dest_eng);
//                    }
//
//                    if (i == 2) {
//                        ms.setThird_time(time);
//                        System.out.println("ms.setFirst_time(time);" + time + " " + urls[0]);
//                        ms.setThird_min(min);
//                        ms.setThird_dest_chi(dest_chi);
//                        ms.setThird_dest_eng(dest_eng);
//                    }
                }

                stopList.add(ms);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stopList;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();

            System.out.println("convertInputStreamToString : " + result);

            return result;
        }

    }

    public void updatedData()
    {
        //Clear Arraylist before refreshing data
        if(mr_list != null) {
            mr_list.clear();
        }

        if(ms_list != null) {
            ms_list.clear();
        }

        mr_list = viewAllFavouriteRecords();
        try {
            ms_list = viewAllFavouriteRecords2();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mr_list.size() > 0  && ms_list.size() > 0) {
            System.out.println("209: null" + mr_list.size());

            mListView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            ids = new int[mr_list.size()];
            companies = new String[mr_list.size()];
            lines = new String[mr_list.size()];


            bounds = new String[mr_list.size()];
            service_types = new String[mr_list.size()];
            rdvs = new String[mr_list.size()];
            eta_ids = new String[mr_list.size()];

            sources_chi = new String[mr_list.size()];
            sources_eng = new String[mr_list.size()];
            dests_chi = new String[mr_list.size()];
            dests_eng = new String[mr_list.size()];

            chi_name = new String[ms_list.size()];
            eng_name = new String[ms_list.size()];



            first_times = new String[ms_list.size()];
            first_mins = new String[ms_list.size()];
            first_dest_eng = new String[ms_list.size()];

            int[] mtsicon = new int[mr_list.size()];
            int[] mtbg = new int[mr_list.size()];


            for(int i = 0; i < mr_list.size(); i++) {
                ids[i] = mr_list.get(i).getID();
                companies[i] = mr_list.get(i).getCOMPANY();
                lines[i] = mr_list.get(i).getROUTE();
                bounds[i] = mr_list.get(i).getBOUND();
                service_types[i] = mr_list.get(i).getSERVICE_TYPE();
                rdvs[i] = mr_list.get(i).getRdv();
                eta_ids[i] = mr_list.get(i).getETA_ID();

                sources_chi[i] = mr_list.get(i).getSource_chi();
                sources_eng[i] = mr_list.get(i).getSource_eng();
                dests_chi[i] = mr_list.get(i).getDest_chi();
                dests_eng[i] = mr_list.get(i).getDest_eng();

                if(companies[i].equals("KMB")) {
                    mtsicon[i] = R.drawable.kmb_logo;
                    mtbg[i] = R.drawable.listitem_kmb;
                }
                else if(companies[i].equals("LWB")) {
                    mtsicon[i] = R.drawable.lwb_logo;
                    mtbg[i] = R.drawable.listitem_lwb;
                }
                else if(companies[i].equals("MTR")) {
                    mtsicon[i] = R.drawable.mtr_logo_wordmark;
                    mtbg[i] = R.drawable.listitem_mtr;
                }
                else if(companies[i].equals("NWFB")) {
                    mtsicon[i] = R.drawable.newbus_logo;
                    mtbg[i] = R.drawable.listitem_newbus;
                }
                else if(companies[i].equals("NLB")) {
                    mtsicon[i] = R.drawable.nlb_logo;
                    mtbg[i] = R.drawable.listitem_newbus;
                }
                else if(companies[i].equals("CB")) {
                    mtsicon[i] = R.drawable.citybus_logo;
                    mtbg[i] = R.drawable.listitem_citybus;
                }
            }


            for(int i = 0; i < ms_list.size(); i++) {

                chi_name[i] = ms_list.get(i).getChi_name();
                eng_name[i] = ms_list.get(i).getEng_name();

                first_times[i] = ms_list.get(i).getFirst_time();
                first_mins[i] = ms_list.get(i).getFirst_min();
                first_dest_eng[i] = ms_list.get(i).getFirst_dest_eng();

            }


            mListAdapter = new ListAdapter(getActivity(), lines, dests_eng, eng_name, first_times, first_mins, mtsicon, mtbg);
            mListView.setAdapter(mListAdapter);
            mListAdapter.notifyDataSetChanged();
        }
        else {
            System.out.println("209: null" + mr_list.size());
            mListView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

}

