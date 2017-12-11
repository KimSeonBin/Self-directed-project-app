package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.search_info;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-12-01.
 */

public class AlarmlistAdapter extends BaseAdapter {
    private ArrayList<Alarm_First_Item> arrayList;
    private View view;
    private static LayoutInflater inflater;
    private Context context;
    private int resource;

    public AlarmlistAdapter(Context context, int resource){
        this.context = context;
        this.resource = resource;
        arrayList = new ArrayList<Alarm_First_Item>();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Alarm_First_Item getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = inflater.inflate(resource, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.alarm_item_imageview);
        TextView textView1 = (TextView) view.findViewById(R.id.alarm_item_orikeyword);
        TextView textView2 = (TextView) view.findViewById(R.id.alarm_item_count);

        Alarm_First_Item temp = arrayList.get(position);
        if(!temp.getImg_url().startsWith("http")){
            temp.setImg_url("http://"+temp.getImg_url());
        }
        Picasso.with(context).load(temp.getImg_url()).fit().centerCrop().into(imageView);
        textView1.setText(temp.getOrigin_keyword());
        textView2.setText(temp.getCount() + "개 찾았습니다");
        if(SaveSharedPreference.getClickItem(context, temp.getOrigin_keyword()) == true){
            view.setBackgroundColor(context.getColor(R.color.not_click));
        }
        else{
            view.setBackgroundColor(context.getColor(R.color.white));
        }
        return view;
    }

    public void addItem(Alarm_First_Item item){
        arrayList.add(item);
    }

    public void deleteItem(int item_position){
        arrayList.remove(item_position);
    }

    public void updateItemCount(int item_position){
        arrayList.get(item_position).setCount(1);
    }
}
