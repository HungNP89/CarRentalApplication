package com.example.carrentalapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrentalapplication.R;
import com.example.carrentalapplication.model.PopularHybridModel;
import com.example.carrentalapplication.uiActivity.CarDetail;

import java.util.List;

public class PopularHybridAdapter extends RecyclerView.Adapter<PopularHybridAdapter.ViewHolder> {
    Context context;
    List<PopularHybridModel> list;

    public PopularHybridAdapter(Context context, List<PopularHybridModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularHybridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_popular_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularHybridAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).timeout(6000).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.edition.setText(String.valueOf(list.get(position).getEdition()));
        holder.rating.setText(String.valueOf(list.get(position).getRating()));
        holder.price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, CarDetail.class);
            intent.putExtra("detailed", list.get(holder.getAdapterPosition()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
       int limit = 5;
       return Math.min(list.size(), limit);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, edition, rating, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_popular);
            name = itemView.findViewById(R.id.text_popular);
            edition = itemView.findViewById(R.id.text_popular3);
            rating = itemView.findViewById(R.id.text_popular2);
            price = itemView.findViewById(R.id.text_popular4);
        }
    }
}
