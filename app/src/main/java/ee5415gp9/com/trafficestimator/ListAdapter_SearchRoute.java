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

public class ListAdapter_SearchRoute extends BaseAdapter
{
    Context context;
    private final String [] values_line;
    private final String [] values_dest;
    private final int [] MTSicon_images;

    public ListAdapter_SearchRoute(Context context, String [] values_line, String [] values_dest, int [] MTSicon_images)
    {

//super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values_line = values_line;
        this.values_dest = values_dest;
        this.MTSicon_images = MTSicon_images;
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
            convertView = inflater.inflate(R.layout.list_routes, parent, false);
            viewHolder.txtLine = (TextView) convertView.findViewById(R.id.Line);
            viewHolder.txtDestination = (TextView) convertView.findViewById(R.id.destinationTxt);
            viewHolder.iconMTS = (ImageView) convertView.findViewById(R.id.aMTSicon);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.txtLine.setText(values_line[position]);
        viewHolder.txtDestination.setText(values_dest[position]);
        viewHolder.iconMTS.setImageResource(MTSicon_images[position]);
        return convertView;
    }
    private static class ViewHolder {
        TextView txtLine;
        TextView txtDestination;
        ImageView iconMTS;
    }
}