package ee5415gp9.com.trafficestimator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

        public static ProgressDialog pDialog;

        private TextView emptyView;
        private ListView mListView;
        public ListAdapter_SearchRoute mListAdapter;

        DatabaseHelper eta_Db;

        List<MasterRoutes> mr_list;
        String[] companies;
        String[] lines;
        String[] dests;
        String[] sources;

        //New Add
        String[] bounds;
        String[] service_types;
        String[] rdvs;
        String[] eta_ids;


        EditText inputRoute;
        Button searchButton;


        public SearchFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//                List<MasterRoutes> mr_list = viewAllRecords();

//                String[] companies = new String[mr_list.size()];
//                String[] lines = new String[mr_list.size()];
//                String[] dests = new String[mr_list.size()];
//                String[] sources = new String[mr_list.size()];

                mr_list = viewAllRecords();
                companies = new String[mr_list.size()];
                lines = new String[mr_list.size()];
                dests = new String[mr_list.size()];
                sources = new String[mr_list.size()];

                //New Add
                bounds = new String[mr_list.size()];
                service_types = new String[mr_list.size()];
                rdvs = new String[mr_list.size()];
                eta_ids = new String[mr_list.size()];


//                String[] datareturn = new String[mr_list.size()];
                int[] mtsicon = new int[mr_list.size() + 0];
                int[] mtbg = new int[mr_list.size() + 0];

                for(int i = 0; i < mr_list.size(); i++) {
                        companies[i] = mr_list.get(i).getCOMPANY();
                        lines[i] = mr_list.get(i).getROUTE();
                        bounds[i] = mr_list.get(i).getBOUND();
                        service_types[i] = mr_list.get(i).getSERVICE_TYPE();
                        rdvs[i] = mr_list.get(i).getRdv();
                        eta_ids[i] = mr_list.get(i).getETA_ID();

                        dests[i] = mr_list.get(i).getDest_eng();
                        sources[i] = mr_list.get(i).getSource_eng();
//                        datareturn[i] = "1";

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

                FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_search, container, false);

                emptyView = (TextView) view.findViewById(R.id.emptyView);
                mListView = (ListView) view.findViewById(R.id.listview);
                mListAdapter = new ListAdapter_SearchRoute(getActivity(), lines, dests, mtsicon, mtbg);
                mListView.setAdapter(mListAdapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        //call Route_station Activity
                            Intent intent = new Intent(getActivity(), RouteActivity.class);

                            Bundle bundle = new Bundle();

                            bundle.putString("company", companies[i]);
                            bundle.putString("line", lines[i]);
                            bundle.putString("bound", bounds[i]);
                            bundle.putString("service_type", service_types[i]);
                            bundle.putString("rdv", rdvs[i]);
                            bundle.putString("eta_id", eta_ids[i]);
                            bundle.putString("dest", dests[i]);
                            bundle.putString("source", sources[i]);

                            System.out.println("165 : " + lines[i] + " " + bounds[i] + " " + rdvs[i]);


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
                        Toast.makeText(getActivity(), companies[i] + " " + lines[i] + ", To: " + dests[i],
                                Toast.LENGTH_SHORT).show();

                        //****  DEFINE THE CLICK ACTION HERE  ****
                    }
                });


                inputRoute = (EditText) view.findViewById(R.id.input_route);
                inputRoute.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                //Clear Arraylist before refreshing data

                                Spinner input_company = (Spinner) getActivity().findViewById(R.id.company);
                                String in_Company = input_company.getSelectedItem().toString();

                                if(in_Company.equals("All"))
                                        in_Company = "";

                                updatedData(in_Company);

                                System.out.println("testing....");


                        }
                });

                String[] arrayCompany = new String[] {"All","KMB","LWB","CB","NWFB","MTR","NLB"};

                Spinner spinnerCompany = (Spinner) view.findViewById(R.id.company);
                ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, arrayCompany);
                spinnerCompany.setAdapter(adapterCurrency);

                searchButton = (Button) view.findViewById(R.id.searchButton);
                searchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                //Clear Arraylist before refreshing data

                                Spinner input_company = (Spinner) getActivity().findViewById(R.id.company);
                                String in_Company = input_company.getSelectedItem().toString();

                                if(in_Company.equals("All"))
                                        in_Company = "";

                                updatedData(in_Company);
                        }
                });



            return view;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                initDatabaseHelper();
//                viewAllRecords();

        }

        private void initDatabaseHelper(){
                if(eta_Db == null){
                        eta_Db = new DatabaseHelper(getActivity());
                }
        }


        public List<MasterRoutes> viewAllRecords() {

                List<MasterRoutes> mr_list = new ArrayList<MasterRoutes>();

                Cursor res = eta_Db.getRouteData();

                if (res.getCount() == 0) {
                        return null;
                }
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
                return mr_list;
        }


        public List<MasterRoutes> viewSearchRoutes(String route, String company) {

                List<MasterRoutes> mr_list = new ArrayList<MasterRoutes>();

                Cursor res = eta_Db.getRouteData_Search(route, company);

                System.out.println("test3: " + route);

                if (res.getCount() == 0) {
                        return null;
                }
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

                        System.out.println("test4: " + ROUTE);


                        mr_list.add(mr);
                }
                return mr_list;
        }


        public void updatedData(String input_company)
        {
                //Clear Arraylist before refreshing data
                if(mr_list != null) {
                        mr_list.clear();
                }

                String in_route = inputRoute.getText().toString();

                mr_list = viewSearchRoutes(input_company,in_route);

                if(mr_list != null) {
                        mListView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);

                        companies = new String[mr_list.size()];
                        lines = new String[mr_list.size()];
                        dests = new String[mr_list.size()];
                        sources = new String[mr_list.size()];

                        //New Add
                        bounds = new String[mr_list.size()];
                        service_types = new String[mr_list.size()];
                        rdvs = new String[mr_list.size()];
                        eta_ids = new String[mr_list.size()];

                        int[] mtsicon = new int[mr_list.size()];
                        int[] mtbg = new int[mr_list.size()];


//                        String[] datareturn = new String[mr_list.size()];
//                        int[] mtsicon = new int[mr_list.size()];

                        for(int i = 0; i < mr_list.size(); i++) {
                                companies[i] = mr_list.get(i).getCOMPANY();
                                lines[i] = mr_list.get(i).getROUTE();
                                bounds[i] = mr_list.get(i).getBOUND();
                                service_types[i] = mr_list.get(i).getSERVICE_TYPE();
                                rdvs[i] = mr_list.get(i).getRdv();
                                eta_ids[i] = mr_list.get(i).getETA_ID();

                                dests[i] = mr_list.get(i).getDest_eng();
                                sources[i] = mr_list.get(i).getSource_eng();

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



                        //update Data
                        mListAdapter = new ListAdapter_SearchRoute(getActivity(), lines, dests, mtsicon, mtbg);
                        mListView.setAdapter(mListAdapter);
                        mListAdapter.notifyDataSetChanged();
                }
                else {
                        mListView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                }

                System.out.println("testing2....");
        }
}
