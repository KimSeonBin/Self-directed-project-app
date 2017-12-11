package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;
import com.example.zzz89.howmuchdidyoufindout.db.SaveSharedPreference;
import com.example.zzz89.howmuchdidyoufindout.server_api.search_info;

/**
 * Created by zzz89 on 2017-10-31.
 */

public class AlarmFragment extends Fragment {
    private ListView listView;
    private AlarmlistAdapter adapter;
    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        assign_value(view);
        set_click_listener();
        loadData();
        return view;
    }

    private void assign_value(View view){
        listView = (ListView)view.findViewById(R.id.alarm_list_listview);
        adapter = new AlarmlistAdapter(getActivity(), R.layout.alarm_item);
        listView.setAdapter(adapter);
    }


    private void set_click_listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.white));
                SaveSharedPreference.setClickItem(getActivity().getApplicationContext(), adapter.getItem(position).getOrigin_keyword(), false);
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                intent.putExtra("keyword", adapter.getItem(position).getOrigin_keyword());
                getActivity().startActivity(intent);
            }
        });
    }

    private void loadData(){
        HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getActivity().getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        String orikeywords[] = sqlHelper.select_ori_keyword_from_FOUND_ITEM();
        if(orikeywords == null){
            Log.d("orikey", "null");
            return;
        }
        for(int i = 0; i < orikeywords.length; i++){
            Alarm_First_Item temp = sqlHelper.select_Alarm_First(orikeywords[i]);
            Log.d("alarm items", temp.getOrigin_keyword());
            if (temp != null){
                Log.d("add alarm", temp.getOrigin_keyword());
                adapter.addItem(temp);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
