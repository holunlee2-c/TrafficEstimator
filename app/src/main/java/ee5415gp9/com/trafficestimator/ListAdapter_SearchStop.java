package ee5415gp9.com.trafficestimator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by leeholun on 10/4/2018.
 */

public class ListAdapter_SearchStop extends BaseAdapter
{
    Context context;
    private final String [] values_stop;
    private final String [] values_stopseq;

    private final String [] values_first_times;
    private final String [] values_first_mins;
//    private final String [] values_first_dest_chi;
    private final String [] values_first_dest_eng;

    private final String [] values_second_times;
    private final String [] values_second_mins;
//    private final String [] values_second_dest_chi;
    private final String [] values_second_dest_eng;

    private final String [] values_third_times;
    private final String [] values_third_mins;
//    private final String [] values_third_dest_chi;
    private final String [] values_third_dest_eng;



    public ListAdapter_SearchStop(Context context, String [] values_stopseq, String [] values_stop,
                                  String [] values_first_times, String [] values_first_mins, String [] values_first_dest_eng,
                                  String [] values_second_times, String [] values_second_mins, String [] values_second_dest_eng,
                                  String [] values_third_times, String [] values_third_mins, String [] values_third_dest_eng)
//    public ListAdapter_SearchStop(Context context, String [] values_stop,
//                                  String [] values_first_times, String [] values_first_mins, String [] values_first_dest_chi, String [] values_first_dest_eng,
//                                  String [] values_second_times, String [] values_second_mins, String [] values_second_dest_chi, String [] values_second_dest_eng,
//                                  String [] values_third_times, String [] values_third_mins, String [] values_third_dest_chi, String [] values_third_dest_eng)


    {

//super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;

        this.values_stopseq = values_stopseq;
        this.values_stop = values_stop;

        this.values_first_times = values_first_times;
        this.values_first_mins = values_first_mins;
//        this.values_first_dest_chi = values_first_dest_chi;
        this.values_first_dest_eng = values_first_dest_eng;

        this.values_second_times = values_second_times;
        this.values_second_mins = values_second_mins;
//        this.values_second_dest_chi = values_second_dest_chi;
        this.values_second_dest_eng = values_second_dest_eng;

        this.values_third_times = values_third_times;
        this.values_third_mins = values_third_mins;
//        this.values_third_dest_chi = values_third_dest_chi;
        this.values_third_dest_eng = values_third_dest_eng;


//
//        System.out.println("this.values_stopseq" + this.values_stopseq);
//        System.out.println("this.values_first_times" + this.values_first_times);
    }

    @Override
    public int getCount() {
        return values_stop.length;
    }
    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_stops, parent, false);

            viewHolder.txtStopSeq = (TextView) convertView.findViewById(R.id.StopSeq);
            viewHolder.txtStop = (TextView) convertView.findViewById(R.id.Stop);

            viewHolder.txtFirst_time = (TextView) convertView.findViewById(R.id.first_time);
            viewHolder.txtFirst_min = (TextView) convertView.findViewById(R.id.first_min);
            viewHolder.txtFirst_dest = (TextView) convertView.findViewById(R.id.first_destination);
            viewHolder.txtFirst_min_desc = (TextView) convertView.findViewById(R.id.first_min_desc);
            viewHolder.txtFirst_to = (TextView) convertView.findViewById(R.id.first_to);

            viewHolder.txtSecond_time = (TextView) convertView.findViewById(R.id.second_time);
            viewHolder.txtSecond_min = (TextView) convertView.findViewById(R.id.second_min);
            viewHolder.txtSecond_dest = (TextView) convertView.findViewById(R.id.second_destination);
            viewHolder.txtSecond_min_desc = (TextView) convertView.findViewById(R.id.second_min_desc);
            viewHolder.txtSecond_to = (TextView) convertView.findViewById(R.id.second_to);

            viewHolder.txtThird_time = (TextView) convertView.findViewById(R.id.third_time);
            viewHolder.txtThird_min = (TextView) convertView.findViewById(R.id.third_min);
            viewHolder.txtThird_dest = (TextView) convertView.findViewById(R.id.third_destination);
            viewHolder.txtThird_min_desc = (TextView) convertView.findViewById(R.id.third_min_desc);
            viewHolder.txtThird_to = (TextView) convertView.findViewById(R.id.third_to);



//            viewHolder.txtDestination = (TextView) convertView.findViewById(R.id.destinationTxt);
//            viewHolder.iconMTS = (ImageView) convertView.findViewById(R.id.aMTSicon);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Check if useful
            viewHolder.txtFirst_time.setText(values_first_times[position]);
            viewHolder.txtFirst_min.setText(values_first_mins[position]);
            viewHolder.txtFirst_dest.setText(values_first_dest_eng[position]);

            viewHolder.txtSecond_time.setText(values_second_times[position]);
            viewHolder.txtSecond_min.setText(values_second_mins[position]);
            viewHolder.txtSecond_dest.setText(values_second_dest_eng[position]);

            viewHolder.txtThird_time.setText(values_third_times[position]);
            viewHolder.txtThird_min.setText(values_third_mins[position]);
            viewHolder.txtThird_dest.setText(values_third_dest_eng[position]);


        viewHolder.txtFirst_min_desc.setText(R.string.eta_text_min);
        viewHolder.txtFirst_to.setText(R.string.eta_text_to);

        viewHolder.txtSecond_min_desc.setText(R.string.eta_text_min);
        viewHolder.txtSecond_to.setText(R.string.eta_text_to);

        viewHolder.txtThird_min_desc.setText(R.string.eta_text_min);
        viewHolder.txtThird_to.setText(R.string.eta_text_to);


        viewHolder.txtStopSeq.setText(values_stopseq[position]);
        viewHolder.txtStop.setText(values_stop[position]);

        System.out.println("values_stopseq[position] " + values_stopseq[position]);
        System.out.println("values_first_times[position] " + values_first_times[position]);



        if(values_first_times[position] != null) { //??? New Add
            viewHolder.txtFirst_time.setText(values_first_times[position]);
//            viewHolder.txtFirst_min.setText(values_first_mins[position]);

            if(Integer.parseInt(values_first_mins[position]) <= 0) {
                viewHolder.txtFirst_min.setText(R.string.eta_arr_soon);
                viewHolder.txtFirst_min_desc.setText("");
            }
            else
                viewHolder.txtFirst_min.setText(values_first_mins[position]);


            if(!values_first_dest_eng[position].equals(""))
                viewHolder.txtFirst_dest.setText(values_first_dest_eng[position]);
            else
                viewHolder.txtFirst_to.setText("");


//            viewHolder.txtFirst_time.setText(values_first_times[position]);
//            viewHolder.txtFirst_min.setText(values_first_mins[position]);
//            viewHolder.txtFirst_dest.setText(values_first_dest_eng[position]);

//            viewHolder.txtSecond_time.setText(values_second_times[position]);
//            viewHolder.txtSecond_min.setText(values_second_mins[position]);
//            viewHolder.txtSecond_dest.setText(values_second_dest_eng[position]);
//
//            viewHolder.txtThird_time.setText(values_third_times[position]);
//            viewHolder.txtThird_min.setText(values_third_mins[position]);
//            viewHolder.txtThird_dest.setText(values_third_dest_eng[position]);
        }
        else {
            viewHolder.txtFirst_min_desc.setText("");
            viewHolder.txtFirst_to.setText("");

//            viewHolder.txtSecond_min_desc.setText("");
//            viewHolder.txtSecond_to.setText("");
//
//            viewHolder.txtThird_min_desc.setText("");
//            viewHolder.txtThird_to.setText("");
        }



        if(values_second_times[position] != null) { //??? New Add
            viewHolder.txtSecond_time.setText(values_second_times[position]);
//            viewHolder.txtSecond_min.setText(values_second_mins[position]);
//            viewHolder.txtSecond_dest.setText(values_second_dest_eng[position]);

            if (Integer.parseInt(values_second_mins[position]) <= 0) {
                viewHolder.txtSecond_min.setText(R.string.eta_arr_soon);
                viewHolder.txtSecond_min_desc.setText("");
            } else
                viewHolder.txtSecond_min.setText(values_second_mins[position]);

            if (!values_second_dest_eng[position].equals(""))
                viewHolder.txtSecond_dest.setText(values_second_dest_eng[position]);
            else
                viewHolder.txtSecond_to.setText("");
        }
        else {
            viewHolder.txtSecond_min_desc.setText("");
            viewHolder.txtSecond_to.setText("");
        }


        if(values_third_times[position] != null) { //??? New Add
            viewHolder.txtThird_time.setText(values_third_times[position]);
//            viewHolder.txtThird_min.setText(values_third_mins[position]);
//            viewHolder.txtThird_dest.setText(values_third_dest_eng[position]);

            if(Integer.parseInt(values_third_mins[position]) <= 0) {
                viewHolder.txtThird_min.setText(R.string.eta_arr_soon);
                viewHolder.txtThird_min_desc.setText("");
                System.out.println("199: run when mins <= 0 ");
            }
            else {
                viewHolder.txtThird_min.setText(values_third_mins[position]);
                System.out.println("199: run when mins > 0 " + values_third_mins[position]);
            }

            if(!values_third_dest_eng[position].equals(""))
                viewHolder.txtThird_dest.setText(values_third_dest_eng[position]);
            else
                viewHolder.txtThird_to.setText("");

        }
        else {
            viewHolder.txtThird_min_desc.setText("");
            viewHolder.txtThird_to.setText("");
            System.out.println("199: set txtThird_min_desc.setText('')");
        }




//            viewHolder.txtSecond_min_desc.setText("");
//            viewHolder.txtSecond_to.setText("");
//            viewHolder.txtThird_min_desc.setText("");
//            viewHolder.txtThird_to.setText("");


//            viewHolder.txtFirst_min_desc.setVisibility(View.GONE);
//            viewHolder.txtFirst_to.setVisibility(View.GONE);
//            viewHolder.txtSecond_min_desc.setVisibility(View.GONE);
//            viewHolder.txtSecond_to.setVisibility(View.GONE);
//            viewHolder.txtThird_min_desc.setVisibility(View.GONE);
//            viewHolder.txtThird_to.setVisibility(View.GONE);

//            viewHolder.txtFirst_min_desc.setVisibility(View.INVISIBLE);
//            viewHolder.txtFirst_to.setVisibility(View.INVISIBLE);
//            viewHolder.txtSecond_min_desc.setVisibility(View.INVISIBLE);
//            viewHolder.txtSecond_to.setVisibility(View.INVISIBLE);
//            viewHolder.txtThird_min_desc.setVisibility(View.INVISIBLE);
//            viewHolder.txtThird_to.setVisibility(View.INVISIBLE);



        return convertView;
    }
    private static class ViewHolder {

        TextView txtStopSeq;
        TextView txtStop;

        TextView txtFirst_min_desc;
        TextView txtFirst_to;

        TextView txtSecond_min_desc;
        TextView txtSecond_to;

        TextView txtThird_min_desc;
        TextView txtThird_to;


        TextView txtFirst_time;
        TextView txtFirst_min;
        TextView txtFirst_dest;

        TextView txtSecond_time;
        TextView txtSecond_min;
        TextView txtSecond_dest;

        TextView txtThird_time;
        TextView txtThird_min;
        TextView txtThird_dest;

//        TextView txtDestination;
//        ImageView iconMTS;
    }
}