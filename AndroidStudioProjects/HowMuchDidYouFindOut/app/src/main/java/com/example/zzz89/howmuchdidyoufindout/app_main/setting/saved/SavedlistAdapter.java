package com.example.zzz89.howmuchdidyoufindout.app_main.setting.saved;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.app_main.setting.search.SearchResultCardItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-11-09.
 */

public class SavedlistAdapter extends BaseAdapter {
    private ArrayList<SearchResultCardItem> arrayList;
    private View view;
    private static LayoutInflater inflater;
    private Context context;
    private int resource;

    public SavedlistAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayList = new ArrayList<SearchResultCardItem>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = inflater.inflate(resource, parent, false);

        ImageView imageView = (ImageView)view.findViewById(R.id.search_result_card_image);
        TextView textView1 = (TextView)view.findViewById(R.id.search_result_card_text);
        TextView textView2 = (TextView)view.findViewById(R.id.search_result_card_text_price);

        Picasso.with(context).load(arrayList.get(position).getImageReso()).fit().centerCrop().into(imageView);
        textView1.setText(arrayList.get(position).getItemName());
        textView2.setText(arrayList.get(position).getPriceInfo());
        return view;
    }

    public void addItem(SearchResultCardItem item){
        arrayList.add(item);
    }
    public void deleteItem(int item_position){
        arrayList.remove(item_position);
    }
    public void updateItem(int item_position, String item_price){
        arrayList.get(item_position).setPriceInfo(item_price);
    }
}
