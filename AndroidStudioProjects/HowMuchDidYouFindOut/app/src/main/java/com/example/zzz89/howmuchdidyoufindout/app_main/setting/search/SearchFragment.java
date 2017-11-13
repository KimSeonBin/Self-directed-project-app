package com.example.zzz89.howmuchdidyoufindout.app_main.setting.search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.Dialog;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SearchFragment extends Fragment {
    private MaterialEditText editTextitem;
    private MaterialEditText editTextprice;
    private ButtonRectangle buttonRectangle;
    private String item_name;
    private String price_count;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editTextitem = (MaterialEditText)view.findViewById(R.id.main_search_keyword);
        editTextprice = (MaterialEditText)view.findViewById(R.id.main_search_price);
        buttonRectangle = (ButtonRectangle)view.findViewById(R.id.main_search_button);


        buttonRectangle.setOnClickListener(new ButtonRectangle.OnClickListener(){
            @Override
            public void onClick(View v) {
                item_name = editTextitem.getText().toString();
                price_count = editTextprice.getText().toString();
                if(item_name.equals("") || price_count.equals("")){
                    SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                    stringBuilder.append("주의");
                    stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    Dialog dialog = new Dialog(getContext(), "주의", "상품 또는 가격이 공란입니다.");

                    stringBuilder.clear();

                    //dialog.getMessageTextView().append("상품 또는 가격이 공란입니다");
                    dialog.show();
                    return;
                }
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("item", item_name);
                intent.putExtra("price", price_count);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}
