package com.example.zzz89.howmuchdidyoufindout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-10-31.
 */

public class SettingListAdapter extends BaseAdapter {
    private View view;
    private ArrayList<SettingListItem> arrayList;
    private static LayoutInflater inflater;
    private Context context;
    private int resource;
    String primary[];
    String secondary[];

    public SettingListAdapter(Context context, int resource, String primary[], String secondary[]){
        this.context = context;
        this.resource = resource;
        this.primary = primary;
        this.secondary = secondary;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayList = new ArrayList<SettingListItem>();
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
        /*
        Context context = parent.getContext();
        if(context == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.setting_listview_item, parent, false);
        }
        */
        TextView textView = (TextView)view.findViewById(R.id.setting_listview_string1);
        TextView textView1= (TextView)view.findViewById(R.id.setting_listview_string2);

        SettingListItem settingListItem = arrayList.get(position);
        textView.setText(settingListItem.getPrimary_sentence());
        textView1.setText(settingListItem.getSecondary_sentence());

        return view;
    }
    public void addItem(String primary, String secondary){
        SettingListItem settingListItem = new SettingListItem(primary, secondary);
        arrayList.add(settingListItem);

    }
}
