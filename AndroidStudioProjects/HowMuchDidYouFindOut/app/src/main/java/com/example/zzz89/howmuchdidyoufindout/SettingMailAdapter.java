package com.example.zzz89.howmuchdidyoufindout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by zzz89 on 2017-11-03.
 */

public class SettingMailAdapter extends BaseAdapter {
    private ArrayList<SettingMailItem> arrayList;

    public SettingMailAdapter(){
        arrayList = new ArrayList<SettingMailItem>();
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.setting_mail_layout_item, parent, false);

        TextView textView = (TextView)convertView.findViewById(R.id.setting_mail_text);
        final Switch swit = (Switch)convertView.findViewById(R.id.setting_mail_switch);

        final SettingMailItem settingMailItem = arrayList.get(position);

        textView.setText(settingMailItem.getSentence());
        swit.setChecked(settingMailItem.isCheck());

        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    buttonView.setChecked(true);
                    settingMailItem.setCheck(true);
                }
                else{
                    buttonView.setChecked(false);
                    settingMailItem.setCheck(false);
                }
            }
        });
        return convertView;
    }

    public void addItem(String sentence, boolean check){
        arrayList.add(new SettingMailItem(sentence, check));
    }

    public boolean[] getSwitchvalue(){
        boolean temp[] = new boolean[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++){
            temp[i] = arrayList.get(i).isCheck();
        }
        return temp;
    }
}
