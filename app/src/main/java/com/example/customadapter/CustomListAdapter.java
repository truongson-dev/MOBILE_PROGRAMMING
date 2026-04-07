package com.example.customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private List<Country> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Country> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_flag);
            holder.countryNameView = (TextView) convertView.findViewById(R.id.textView_countryName);
            holder.populationView = (TextView) convertView.findViewById(R.id.textView_population);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Country country = this.listData.get(position);
        holder.countryNameView.setText(country.getCountryName());
        holder.populationView.setText("Population: " + country.getPopulation());

        int imageId = this.getDrawableResIdByName(country.getFlagName());
        if (imageId != 0) {
            holder.flagView.setImageResource(imageId);
        } else {
            // Fallback to mipmap if not in drawable
            imageId = this.getMipmapResIdByName(country.getFlagName());
            if (imageId != 0) {
                holder.flagView.setImageResource(imageId);
            } else {
                holder.flagView.setImageResource(R.mipmap.ic_launcher);
            }
        }

        return convertView;
    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName , "drawable", pkgName);
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName , "mipmap", pkgName);
    }

    static class ViewHolder {
        ImageView flagView;
        TextView countryNameView;
        TextView populationView;
    }
}