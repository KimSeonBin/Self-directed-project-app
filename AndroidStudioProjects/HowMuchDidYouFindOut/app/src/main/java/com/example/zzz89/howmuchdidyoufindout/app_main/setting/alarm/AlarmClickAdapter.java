package com.example.zzz89.howmuchdidyoufindout.app_main.setting.alarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zzz89.howmuchdidyoufindout.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzz89 on 2017-12-08.
 */

public class AlarmClickAdapter extends RecyclerView.Adapter<AlarmClickAdapter.ViewHolder> {
    private List<Alarm_Click_Item> items;
    private Context context;
    private String textview_append = "가격: ";
    public AlarmClickAdapter(Context context, List<Alarm_Click_Item> items) {
        super();
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_click_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Alarm_Click_Item item = items.get(position);
        holder.textView_name.setText(item.getSearched_name());
        holder.textView_price.setText(textview_append + Integer.toString(item.getPrice()));
        holder.textView_mall.setText(item.getMall());
        Picasso.with(context).load(item.getItem_image_url()).fit().centerCrop().into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.getItem_url().startsWith("http")){
                    item.setItem_url("http://"+item.getItem_url());
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getItem_url()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView_name;
        TextView textView_price;
        TextView textView_mall;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView)itemView.findViewById(R.id.alarm_click_image);
            this.textView_name = (TextView)itemView.findViewById(R.id.alarm_click_item_name);
            this.textView_price = (TextView)itemView.findViewById(R.id.alarm_click_item_price);
            this.textView_mall = (TextView)itemView.findViewById(R.id.alarm_click_item_mall);
            cardView = (CardView)itemView.findViewById(R.id.alarm_click_cardview);
        }
    }
}
