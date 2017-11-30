package com.example.zzz89.howmuchdidyoufindout.app_main.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;

import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-11-02.
 */

public class SettingFilterAdapter extends BaseAdapter {
    private ArrayList<SettingFilterItem> arrayList;

    public SettingFilterAdapter(){
        arrayList = new ArrayList<SettingFilterItem>();
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
        Context context = parent.getContext();
        //if(context == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.setting_filter_layout_item, parent, false);
        //}
        TextView textView = (TextView)convertView.findViewById(R.id.setting_filter_text);
        final Switch swit = (Switch)convertView.findViewById(R.id.setting_filter_layout_switch);

        final SettingFilterItem settingFilterItem = arrayList.get(position);

        textView.setText(settingFilterItem.getOnlinemall());
        swit.setChecked(settingFilterItem.getistrue());
        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setChecked(true);
                    settingFilterItem.setIstrue(true);
                }
                else{
                    buttonView.setChecked(false);
                    settingFilterItem.setIstrue(false);
                }
            }
        });
        return convertView;
    }

    public void addItem(String sentence, boolean check){
        SettingFilterItem item = new SettingFilterItem(sentence, check);
        arrayList.add(item);
    }

    public boolean[] getSwitchValue(){
        boolean temp[] = new boolean[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            temp[i] = arrayList.get(i).getistrue();
        }
        return temp;
    }
}
