package com.example.zzz89.howmuchdidyoufindout.app_main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;
import com.example.zzz89.howmuchdidyoufindout.login.LoginActivity;

/**
 * Created by zzz89 on 2017-10-31.
 */

public class SettingFragment extends Fragment {
    private SettingListAdapter adapter;
    private ListView listView;
    private String primary[] = {"쇼핑몰 필터 변경", "메일 변경", "자동 메일 전송"};
    private String secondary[] = {"검색할 쇼핑몰을 지정합니다", "메일을 변경합니다", "매일 메일을 받을지 선택합니다"};
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        listView = (ListView)view.findViewById(R.id.setting_listview);

        adapter = new SettingListAdapter(getActivity(), R.layout.setting_listview_item, primary, secondary);

        adapter.addItem("쇼핑몰 필터 변경", "검색할 쇼핑몰을 지정합니다");
        adapter.addItem("메일 변경", "메일을 변경합니다.");
        adapter.addItem("자동 메일 전송", "매일 메일을 받을지 선택합니다.");

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingListItem item = (SettingListItem)parent.getItemAtPosition(position);
                Intent intent;
                if(item.getPrimary_sentence().equals("쇼핑몰 필터 변경")){
                    intent = new Intent(getActivity(), Setting_Filter_Change.class);
                    getActivity().startActivity(intent);
                }
                else if(item.getPrimary_sentence().equals("메일 변경")){
                    HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getActivity().getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);

                    String keywords[] = sqlHelper.select_ori_keyword_from_FOUND_ITEM();
                    if (keywords != null) {
                        for (int i = 0; i < keywords.length; i++) {
                            sqlHelper.delete_All_Item(keywords[i]);
                            sqlHelper.deleteSearch_Info(keywords[i]);
                        }
                    }
                    intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 1);
                }
                else if(item.getPrimary_sentence().equals("자동 메일 전송")){
                    intent = new Intent(getActivity(), Setting_Mail.class);
                    getActivity().startActivity(intent);
                }
                else{
                }
            }
        });
        return view;
    }
}
