package com.example.zzz89.howmuchdidyoufindout.app_main.setting.search;

import android.content.Context;
import android.content.Intent;
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
 * Created by zzz89 on 2017-11-07.
 */

public class SearchResultCardAdapter extends RecyclerView.Adapter<SearchResultCardAdapter.ViewHolder> {
    private Context context;
    private List<SearchResultCardItem> items;
    private int item_layout;
    private int price;
    private String price_sentence = "최저가: ";
    private String price_won = "원";
    public SearchResultCardAdapter(Context context, List<SearchResultCardItem> items, int item_layout, int price) {
        super();
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.price = price;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_card, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SearchResultCardItem item = items.get(position);
        holder.textView.setText(item.getItemName());
        holder.textView2.setText(price_sentence + item.getPriceInfo() + price_won);
        Picasso.with(context).load(item.getImageReso()).fit().centerCrop().into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchResultPriceTooHigh.class);

                intent.putExtra("item_name", item.getItemName());
                intent.putExtra("img_url", item.getImageReso());
                intent.putExtra("wanted_price", price);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.search_result_card_image);
            this.textView = (TextView) itemView.findViewById(R.id.search_result_card_text);
            this.textView2 = (TextView) itemView.findViewById(R.id.search_result_card_text_price);
            this.cardView = (CardView) itemView.findViewById(R.id.search_result_card);
        }
    }
}
