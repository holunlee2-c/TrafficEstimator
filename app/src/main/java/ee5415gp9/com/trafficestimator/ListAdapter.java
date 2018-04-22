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

public class ListAdapter extends BaseAdapter
{
    Context context;
    private final String [] values_line;
    private final String [] values_dest;
    private final String [] values_startpt;
    private final String [] values_datareturn;
    private final String [] values_minReturn;
    private final int [] MTSicon_images;
    private final int [] MTSbg;
    public ListAdapter(Context context, String [] values_line, String [] values_dest, String [] values_startpt, String [] values_datareturn, String [] values_minReturn, int [] MTSicon_images, int [] MTS_bg)
    {

//super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values_line = values_line;
        this.values_dest = values_dest;
        this.values_startpt = values_startpt;
        this.values_datareturn = values_datareturn;
        this.values_minReturn = values_minReturn;
        this.MTSicon_images = MTSicon_images;
        this.MTSbg = MTS_bg;
    }

    @Override
    public int getCount() {
        return values_dest.length;
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
            convertView = inflater.inflate(R.layout.list_items, parent, false);
            viewHolder.txtLine = (TextView) convertView.findViewById(R.id.Line);
            viewHolder.txtDestination = (TextView) convertView.findViewById(R.id.destinationTxt);
            viewHolder.txtStartpoint = (TextView) convertView.findViewById(R.id.aStartpointtxt);
            viewHolder.textTimeReturn = (TextView) convertView.findViewById(R.id.aDatareturntxt);
            viewHolder.txtMinReturn = (TextView) convertView.findViewById(R.id.min);
            viewHolder.iconMTS = (ImageView) convertView.findViewById(R.id.aMTSicon);
            viewHolder.bgMTS = (ImageView) convertView.findViewById(R.id.aMTSbg);

            viewHolder.txtMinDesc = (TextView) convertView.findViewById(R.id.min_description);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtLine.setText(values_line[position]);
        viewHolder.txtDestination.setText(values_dest[position]);
        viewHolder.txtStartpoint.setText(values_startpt[position]);
        viewHolder.textTimeReturn.setText(values_datareturn[position]);

        if(Integer.parseInt(values_minReturn[position]) <= 0) {
            viewHolder.txtMinReturn.setText(R.string.eta_arr_soon);
            viewHolder.txtMinDesc.setText("");
        }

//        viewHolder.txtMinReturn.setText(values_minReturn[position]);
        viewHolder.iconMTS.setImageResource(MTSicon_images[position]);
        viewHolder.bgMTS.setImageResource(MTSbg[position]);
        return convertView;
    }
    private static class ViewHolder {
        TextView txtLine;
        TextView txtDestination;
        TextView txtStartpoint;
        TextView textTimeReturn;
        TextView txtMinReturn;
        TextView txtMinDesc;
        ImageView iconMTS;
        ImageView bgMTS;
    }
}