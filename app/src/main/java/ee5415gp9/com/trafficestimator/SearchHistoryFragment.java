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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchHistoryFragment extends Fragment {

        private TextView emptyView;
        private ListView mListView;
        public ListAdapter_SearchRoute mListAdapter;

        List<MasterRoutes> mr_list;
        int[] ids;

        String[] companies;
        String[] lines;
        String[] dests;
        String[] sources;

        //New Add
        String[] bounds;
        String[] service_types;
        String[] rdvs;
        String[] eta_ids;


        DatabaseHelper eta_Db;
        DatabaseHelper2 history_Db;

        public SearchHistoryFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initDatabaseHelper();
        }

        private void initDatabaseHelper(){
            if(eta_Db == null){
                eta_Db = new DatabaseHelper(getActivity());
            }

            if(history_Db == null){
                history_Db = new DatabaseHelper2(getActivity());
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            mr_list = viewAllRecords();

            if(mr_list.size() > 0) {

                ids = new int[mr_list.size()];
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
                for (int i = 0; i < mr_list.size(); i++) {
                    ids[i] = mr_list.get(i).getID();
                    companies[i] = mr_list.get(i).getCOMPANY();
                    lines[i] = mr_list.get(i).getROUTE();
                    bounds[i] = mr_list.get(i).getBOUND();
                    service_types[i] = mr_list.get(i).getSERVICE_TYPE();
                    rdvs[i] = mr_list.get(i).getRdv();
                    eta_ids[i] = mr_list.get(i).getETA_ID();

                    System.out.println("200: " + lines[i]);

                    dests[i] = mr_list.get(i).getDest_eng();
                    sources[i] = mr_list.get(i).getSource_eng();
//                        datareturn[i] = "1";

                    if (companies[i].equals("KMB"))
                        mtsicon[i] = R.drawable.kmb_logo;
                    else if (companies[i].equals("LWB"))
                        mtsicon[i] = R.drawable.lwb_logo;
                    else if (companies[i].equals("MTR"))
                        mtsicon[i] = R.drawable.mtr_logo;
                    else if (companies[i].equals("NWFB"))
                        mtsicon[i] = R.drawable.newbus_logo;
                    else if (companies[i].equals("NLB"))
                        mtsicon[i] = R.drawable.nlb_logo;
                    else if (companies[i].equals("CB"))
                        mtsicon[i] = R.drawable.citybus_logo;
                }

                FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_search_history, container, false);

                emptyView = (TextView) view.findViewById(R.id.emptyView);

                mListView = (ListView) view.findViewById(R.id.listview);
                mListAdapter = new ListAdapter_SearchRoute(getActivity(), lines, dests, mtsicon);
                mListView.setAdapter(mListAdapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Add into Database
//                    history_Db.insertData(ids[i]);

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


                return view;
            }
            else
            {
                FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_search_history, container, false);
                emptyView = (TextView) view.findViewById(R.id.emptyView);
                mListView = (ListView) view.findViewById(R.id.listview);

                mListView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                return view;
            }
        }


    public List<MasterRoutes> viewAllRecords() {

        Cursor cursor = history_Db.getHistoryAllData();

//        List <History> history_list = new ArrayList<History>();
        List<MasterRoutes> mr_list = new ArrayList<MasterRoutes>();

        while (cursor.moveToNext()) {
            int pk_id = cursor.getInt(1);
            Cursor res = eta_Db.getRouteData_SearchSelectedHistory(pk_id);

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


}