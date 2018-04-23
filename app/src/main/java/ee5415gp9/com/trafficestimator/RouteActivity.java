package ee5415gp9.com.trafficestimator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RouteActivity extends AppCompatActivity {

    private final String server_link = "http://antony9655.mynetgear.com";
    private final int timeout_sec = 2;

    List<MasterRoutes> mr_list;
    List<MasterStops> ms_list;

    DatabaseHelper eta_Db;
    DatabaseHelper2 history_Db;

    ImageView companyIcon, companyBg;
    TextView line_textView;
    TextView destinationTxt;

    private ListView mListView;
    public ListAdapter_SearchStop mListAdapter;

    String company;
    String line;
    String bound;
    String service_type;
    String rdv;
    String eta_id;
    String dest;
    String source;


    int[] ids;
    String[] stopseqs;
    String[] stops;
    String[] first_times;
    String[] first_mins;
    String[] first_dest_chi;
    String[] first_dest_eng;

    String[] second_times;
    String[] second_mins;
    String[] second_dest_chi;
    String[] second_dest_eng;

    String[] third_times;
    String[] third_mins;
    String[] third_dest_chi;
    String[] third_dest_eng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ActionBar actionBar = getSupportActionBar();
        BitmapDrawable background = new BitmapDrawable (BitmapFactory.decodeResource(getResources(), R.drawable.top_banna));
        actionBar.setBackgroundDrawable(background);
        actionBar.setDisplayShowTitleEnabled(false);
        initDatabaseHelper();

        Bundle bundle = getIntent().getExtras();

        company = bundle.getString("company");
        line = bundle.getString("line");
        bound = bundle.getString("bound");
        service_type = bundle.getString("service_type");
        rdv = bundle.getString("rdv");
        eta_id = bundle.getString("eta_id");
        dest = bundle.getString("dest");
        source = bundle.getString("source");

//        String company = bundle.getString("company");
//        String line = bundle.getString("line");
//        String bound = bundle.getString("bound");
//        String service_type = bundle.getString("service_type");
//        String rdv = bundle.getString("rdv");
//        String eta_id = bundle.getString("eta_id");
//        String dest = bundle.getString("dest");
//        String source = bundle.getString("source");

        System.out.println("166: " + company + " " + line + " " + bound + " " + rdv);


        companyIcon = (ImageView) findViewById(R.id.companyIcon);
        companyBg = (ImageView) findViewById(R.id.companybg);

        if(company.equals("KMB")) {
            companyIcon.setImageResource(R.drawable.kmb_logo);
            companyBg.setBackgroundResource(R.drawable.listitem_kmb);
        }
        else if(company.equals("LWB")) {
            companyIcon.setImageResource(R.drawable.lwb_logo);
            companyBg.setBackgroundResource(R.drawable.listitem_lwb);
        }
        else if(company.equals("MTR")) {
            companyIcon.setImageResource(R.drawable.mtr_logo_wordmark);
            companyBg.setBackgroundResource(R.drawable.listitem_mtr);
        }
        else if(company.equals("NWFB")) {
            companyIcon.setImageResource(R.drawable.newbus_logo);
            companyBg.setBackgroundResource(R.drawable.listitem_newbus);
        }
        else if(company.equals("NLB")) {
            companyIcon.setImageResource(R.drawable.nlb_logo);
            companyBg.setBackgroundResource(R.drawable.listitem_nlb);
        }
        else if(company.equals("CB")) {
            companyIcon.setImageResource(R.drawable.citybus_logo);
            companyBg.setBackgroundResource(R.drawable.listitem_citybus);
        }


        line_textView = (TextView) findViewById(R.id.Line);
        destinationTxt = (TextView) findViewById(R.id.destinationTxt);

        line_textView.setText(line);
        destinationTxt.setText(dest);


        try {
            ms_list = viewSelectedRouteStops(company, line, bound, service_type, rdv, eta_id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ids = new int[ms_list.size()];

        stopseqs = new String[ms_list.size()];
        stops = new String[ms_list.size()];

        first_times = new String[ms_list.size()];
        first_mins = new String[ms_list.size()];
        first_dest_eng = new String[ms_list.size()];

        second_times = new String[ms_list.size()];
        second_mins = new String[ms_list.size()];
        second_dest_eng = new String[ms_list.size()];

        third_times = new String[ms_list.size()];
        third_mins = new String[ms_list.size()];
        third_dest_eng = new String[ms_list.size()];

        for(int i = 0; i < ms_list.size(); i++) {
            ids[i] = ms_list.get(i).getID();
            stopseqs[i] = String.valueOf(i +1) ;
            stops[i] = ms_list.get(i).getEng_name();

            first_times[i] = ms_list.get(i).getFirst_time();
            first_mins[i] = ms_list.get(i).getFirst_min();
            first_dest_eng[i] = ms_list.get(i).getFirst_dest_eng();

            second_times[i] = ms_list.get(i).getSecond_time();
            second_mins[i] = ms_list.get(i).getSecond_min();
            second_dest_eng[i] = ms_list.get(i).getSecond_dest_eng();

            third_times[i] = ms_list.get(i).getThird_time();
            third_mins[i] = ms_list.get(i).getThird_min();
            third_dest_eng[i] = ms_list.get(i).getThird_dest_eng();
        }

        mListView = (ListView) findViewById(R.id.listview);
        mListAdapter = new ListAdapter_SearchStop(this,stopseqs,stops,first_times,first_mins,first_dest_eng,second_times,second_mins,second_dest_eng,third_times,third_mins,third_dest_eng);
        mListView.setAdapter(mListAdapter);

//        if (SearchFragment.pDialog != null) {
//            if (SearchFragment.pDialog.isShowing()) {
//                SearchFragment.pDialog.dismiss();
//            }
//            else {
////                    Log.e(Vars.TAG, "It is NOT SHOWING");
//            }
//        }

        registerForContextMenu(mListView);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> a, View v, int position,
//                                    long id) {
//                this.openCreateContextMenu(this);
//            }
//        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stops, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position; //Returned StopSeq


        switch (item.getItemId()) {
            case R.id.add_favourite:
                System.out.println("202, menuid :" + index);
                System.out.println("203, menu - stopid :" + ids[index]);

                history_Db.insertData_favourite(ids[index]);

                Cursor cursor = history_Db.getFavouriteAllData();

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    int pk_id = cursor.getInt(1);

                    System.out.println("205: " + id + ", " + pk_id);
                }

                return true;
            case R.id.complain:
                sentComplain(index);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void sentComplain(int index){

//        company = bundle.getString("company");
//        line = bundle.getString("line");
//        bound = bundle.getString("bound");
//        service_type = bundle.getString("service_type");
//        rdv = bundle.getString("rdv");
//        eta_id = bundle.getString("eta_id");


        Bundle bundle = getIntent().getExtras();
        company = bundle.getString("company");
        line = bundle.getString("line");
        dest = bundle.getString("dest");
        source = bundle.getString("source");

        String stop = ms_list.get(index).getEng_name();

        String first_time = ms_list.get(index).getFirst_time();
        String first_min = ms_list.get(index).getFirst_min();
        String first_dest_eng = ms_list.get(index).getFirst_dest_eng();

        String second_time = ms_list.get(index).getSecond_time();
        String second_min = ms_list.get(index).getSecond_min();
        String second_dest_eng = ms_list.get(index).getSecond_dest_eng();

        String third_time = ms_list.get(index).getThird_time();
        String third_min = ms_list.get(index).getThird_min();
        String third_dest_eng = ms_list.get(index).getThird_dest_eng();

        String revised_company = company;
        if(company.equals("CB"))
            revised_company = "City Bus";
        else if(company.equals("LWB"))
            revised_company = "Long Win Bus";
        else if(company.equals("NLB"))
            revised_company = "New Lantau Bus";

        String revised_line = line;
        if(line.equals("WRL"))
            revised_company = "West Rail Line";
        else if(line.equals("TCL"))
            revised_company = "Tung Chung Line";
        else if(line.equals("TKL"))
            revised_company = "Tseung Kwan O Line";
        else if(line.equals("AEL"))
            revised_company = "Airport Express";

        String email_title = getString(R.string.complain_email_title) + " " + revised_company  + " - " + revised_line;
        System.out.println(email_title);

        String email_content_pt1 = getString(R.string.complain_email_content_pt1) + " " + revised_company  + "</br>" ;
        String email_content_pt2 = "</br> " + getString(R.string.complain_email_content_pt2) + ". </br>" ;

        String email_content = email_content_pt1 + email_content_pt2;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:tellme@1823.gov.hk"));
        intent.putExtra(Intent.EXTRA_SUBJECT, email_title);
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(email_content));
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        
        if (SearchFragment.pDialog != null) {
            if (SearchFragment.pDialog.isShowing()) {
                SearchFragment.pDialog.dismiss();
            }
            else {
//                    Log.e(Vars.TAG, "It is NOT SHOWING");
            }
        }

        if (HomeFragment.pDialog != null) {
            if (HomeFragment.pDialog.isShowing()) {
                HomeFragment.pDialog.dismiss();
            }
            else {
//                    Log.e(Vars.TAG, "It is NOT SHOWING");
            }
        }


    }


    private void initDatabaseHelper(){
        if(eta_Db == null){
            eta_Db = new DatabaseHelper(this);
        }

        if(history_Db == null){
            history_Db = new DatabaseHelper2(this);
        }
    }

    public List<MasterStops> viewSelectedRouteStops(String in_company, String in_route, String in_bound, String in_service_type, String in_rdv, String in_eta_id) throws ExecutionException, InterruptedException {

        List<MasterStops> ms_list = new ArrayList<MasterStops>();

        Cursor res = eta_Db.getRouteStop_Search(in_company, in_route, in_bound, in_service_type, in_rdv);

        if (res.getCount() == 0) {
            return null;
        }

        HttpAsyncTask2 hat2 = new HttpAsyncTask2();
        boolean connected_Server = hat2.execute(server_link).get();

        while (res.moveToNext()) {

            int ID = res.getInt(0);

            System.out.println("res.getInt(0);" + res.getInt(0));

            String COMPANY = res.getString(1);
            String ROUTE = res.getString(2);
            String BOUND = res.getString(3);
            String SERVICE_TYPE = res.getString(4);
            String rdv = res.getString(5);

            String stop_id = res.getString(6);
            String stop_seq = res.getString(7);
            String chi_name = res.getString(8);
            String eng_name = res.getString(9);
            String eta_para = res.getString(10);

            MasterStops ms = new MasterStops();

            ms.setID(ID);
            ms.setCOMPANY(COMPANY);
            ms.setROUTE(ROUTE);
            ms.setBOUND(BOUND);
            ms.setSERVICE_TYPE(SERVICE_TYPE);
            ms.setRdv(rdv);
            ms.setStopid(stop_id);
            ms.setStopseq(stop_seq);
            ms.setChi_name(chi_name);
            ms.setEng_name(eng_name);
            ms.setEta_para(eta_para);


            //For Adding ETA
            String link = "";

            if (in_company.equals("KMB") || in_company.equals("LWB"))
            {
                link = "http://antony9655.mynetgear.com/ETA/KMB.jsp?key=a77&key2=996b&route=" + ROUTE + "&bound=" + BOUND + "&serviceType=" + 1 + "&stop=" + stop_id + "&stop_seq=" + stop_seq;
                System.out.println("KMB & LWB link : " + link);
            }

            if (in_company.equals("MTR"))
            {
//                http://antony9655.mynetgear.com/ETA/MTR.jsp?key=a77&key2=996b&line=TKL&station=TIK&lang=zh&bound=u
                link = "http://antony9655.mynetgear.com/ETA/MTR.jsp?key=a77&key2=996b&line=" + ROUTE + "&station=" + stop_id + "&lang=zh&bound=" + BOUND.toLowerCase();
                System.out.println("MTR link : " + link);
            }

            if (in_company.equals("NLB"))
            {
//                http://antony9655.mynetgear.com/ETA/NLB.jsp?key=a77&key2=996b&route=30&stop=289
                link = "http://antony9655.mynetgear.com/ETA/NLB.jsp?key=a77&key2=996b&route=" + in_eta_id + "&stop=" + stop_id;
                System.out.println("NLB link : " + link);
            }


            if (in_company.equals("NWFB") || in_company.equals("CB"))
            {
                //http://antony9655.mynetgear.com/ETA/NWFB.jsp?key=a77&key2=996b&stopid=001280&service_no=23&bound=O&stopseq=3&rdv=23-POR-1
                link = "http://antony9655.mynetgear.com/ETA/NWFB.jsp?key=a77&key2=996b&stopid=" + stop_id + "&service_no=" + in_route + "&bound=" + in_bound + "&stopseq=" + stop_seq + "&rdv=" + in_rdv;
                    System.out.println("NWFB & CB link : " + link);
            }


//            HttpAsyncTask2 hat2 = new HttpAsyncTask2();
//            boolean connected_Server = hat2.execute(server_link).get();

            System.out.print(" 290 boolean connected_Server: " + connected_Server);

//            boolean connected_Server = (boolean) new HttpAsyncTask2().get();
            if(connected_Server)
            {
//                    HttpAsyncTask hat = new HttpAsyncTask();
//            ArrayList<MasterStops> ms_AL = hat.execute(link).get();
//            if(isConnectedToServer(link,1000)) {
                HttpAsyncTask hat = new HttpAsyncTask();
                ArrayList<MasterStops> ms_AL = hat.execute(link).get();


//            System.out.println("ms_AL.size()" + ms_AL.size());
//            System.out.println("hat.getStopList2()" + hat.getStopList2());

//            //??? New Add for ETA
                for (int i = 0; i < ms_AL.size(); i++) {
                    System.out.println("ms_AL.size()" + ms_AL.size());
                    System.out.println("eng_name :" + eng_name);
                    System.out.println("ms_AL.get(i).getFirst_min() " + ms_AL.get(i).getFirst_min());

                    ms.setStopseqs(String.valueOf(i + 1));
                    ms.setFirst_time(ms_AL.get(i).getFirst_time());
                    ms.setFirst_min(ms_AL.get(i).getFirst_min());
                    ms.setFirst_dest_chi(ms_AL.get(i).getFirst_dest_chi());
                    ms.setFirst_dest_eng(ms_AL.get(i).getFirst_dest_eng());

                    ms.setSecond_time(ms_AL.get(i).getSecond_time());
                    ms.setSecond_min(ms_AL.get(i).getSecond_min());
                    ms.setSecond_dest_chi(ms_AL.get(i).getSecond_dest_chi());
                    ms.setSecond_dest_eng(ms_AL.get(i).getSecond_dest_eng());

                    ms.setThird_time(ms_AL.get(i).getThird_time());
                    ms.setThird_min(ms_AL.get(i).getThird_min());
                    ms.setThird_dest_chi(ms_AL.get(i).getThird_dest_chi());
                    ms.setThird_dest_eng(ms_AL.get(i).getThird_dest_eng());

                }
            }
            else
            {
                System.out.println("270: Cannot connect server");
            }
            ms_list.add(ms);

            System.out.println("167: " + COMPANY + " " + ROUTE + " " + BOUND);

        }
        return ms_list;
    }

    //Passing value to viewSelectedRouteStops for making ListView
//    private String passValue(Value myValue) {
//        //handle value
//        return myHandledValueType;
//    }


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

                    if (i == 1) {
                        ms.setSecond_time(time);
                        System.out.println("ms.setFirst_time(time);" + time + " " + urls[0]);
                        ms.setSecond_min(min);
                        ms.setSecond_dest_chi(dest_chi);
                        ms.setSecond_dest_eng(dest_eng);
                    }

                    if (i == 2) {
                        ms.setThird_time(time);
                        System.out.println("ms.setFirst_time(time);" + time + " " + urls[0]);
                        ms.setThird_min(min);
                        ms.setThird_dest_chi(dest_chi);
                        ms.setThird_dest_eng(dest_eng);
                    }
                }

                stopList.add(ms);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stopList;
        }

//        public ArrayList<MasterStops> getStopList() {
//            return stopList;
//        }


//        public String getStopList2() {
//            return stopList.get(0).getFirst_time();
//        }

//        private String GET(String url) {
//            InputStream inputStream = null;
//            String result = "";
//            try {
//                // create HttpClient
//                HttpClient httpclient = new DefaultHttpClient();
//
//                // make GET request to the given URL
//                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
//
//                // receive response as inputStream
//                inputStream = httpResponse.getEntity().getContent();
//
//                // convert inputstream to string
//                if (inputStream != null)
//                    result = convertInputStreamToString(inputStream);
//                else
//                    result = "Did not work!";
//
//            } catch (Exception e) {
//                Log.d("InputStream", e.getLocalizedMessage());
//            }
//
//            return result;
//        }

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

    private class HttpAsyncTask2 extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {
//            return GET(urls[0]);

            System.out.println("HttpAsyncTask2");

            InputStream inputStream = null;
            String result = "";
            try {
                System.out.println("Connecting");
//                URL url = new URL("http://antony9655.mynetgear.com");
                URL url = new URL(urls[0]);

                URLConnection conection = url.openConnection();
                conection.setConnectTimeout(timeout_sec * 1000);
                conection.connect();
                System.out.println("280 : Server connected");

                return true;
            } catch(Exception e)
            {
                System.out.println("280a : cannot connect server");
                return false;
            }

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
}
