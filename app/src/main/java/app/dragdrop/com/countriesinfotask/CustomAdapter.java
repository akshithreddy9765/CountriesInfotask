package app.dragdrop.com.countriesinfotask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    public Context mContext;
    public ArrayList<Object> globalArray;

    public CustomAdapter(Context context, ArrayList<Object> globalArray) {
        this.mContext = context;
        this.globalArray = globalArray;
    }


    @Override
    public int getCount() {
        return globalArray.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewHolder holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.list_item, null);

            holder.nameTextView = (TextView) rowView.findViewById(R.id.nameTextView);
            holder.capitalTextView = (TextView) rowView.findViewById(R.id.capitalTextView);
            holder.populationTextView = (TextView) rowView.findViewById(R.id.populationTextView);


            rowView.setTag(holder);
        } else {
            rowView = convertView;
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        Region region=(Region) globalArray.get(position);
        holder.nameTextView.setText("Name : "+region.getName());
        holder.capitalTextView.setText("Capital : "+region.getCapital());
        holder.populationTextView.setText("Population : "+region.getPopulation());

        holder.nameTextView.setTag(region.getLanguages());

        return rowView;
    }

    public class ViewHolder {
        TextView nameTextView, capitalTextView, populationTextView;


    }

}
