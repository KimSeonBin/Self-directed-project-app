package com.example.zzz89.howmuchdidyoufindout.app_main.setting.saved;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.example.zzz89.howmuchdidyoufindout.app_main.setting.search.SearchResultCardItem;
import com.example.zzz89.howmuchdidyoufindout.db.HowMuchSQLHelper;

/**
 * Created by zzz89 on 2017-10-31.
 */

public class SavedlistFragment extends Fragment {
    private ListView listView;
    private SavedlistAdapter adapter;

    public static SavedlistFragment newInstance() {
        SavedlistFragment fragment = new SavedlistFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_savedlist, container, false);
        listView = (ListView)view.findViewById(R.id.savedlist_listview);
        adapter = new SavedlistAdapter(getActivity(), R.layout.search_item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SavedlistModify.class);
                SearchResultCardItem item = (SearchResultCardItem)adapter.getItem(position);
                intent.putExtra("item_name", item.getItemName());
                intent.putExtra("item_price", item.getPriceInfo());
                intent.putExtra("img_url", item.getImageReso());
                intent.putExtra("item_index", position);
                startActivityForResult(intent, 3);
            }
        });
        loadData();
        listView.setAdapter(adapter);
        return view;
    }

    private void loadData(){
        HowMuchSQLHelper sqlHelper = new HowMuchSQLHelper(getActivity().getApplicationContext(), HowMuchSQLHelper.DB_name, null, HowMuchSQLHelper.versionNumber);
        SearchResultCardItem select[] = sqlHelper.selectITEM_INFO();
        for(int i = 0; i < select.length; i++){
            adapter.addItem(select[i]);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.d("request", String.valueOf(requestCode));
        Log.d("result", String.valueOf(resultCode));
        if(resultCode == 0){
            return;
        }

        String updated_item_price = data.getStringExtra("item_price2");
        int item_pos = data.getIntExtra("item_index2", 0);

        Log.d("item_price2", updated_item_price);
        if(resultCode == 1){   //update item info
            adapter.updateItem(item_pos, updated_item_price);
            adapter.notifyDataSetChanged();
        }
        else if(resultCode == 2){  //delete item info
            adapter.deleteItem(item_pos);
            adapter.notifyDataSetChanged();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

}
