package com.developer.nfcproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> implements View.OnClickListener {
    private List<ItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, this);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{
        public TextView countryName;
        public ImageView countryPhoto;

        public RecyclerViewHolders(View itemView, View.OnClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(listener);
            countryName = (TextView)itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, CardDetails.class);
        context.startActivity(intent);
    }
}
