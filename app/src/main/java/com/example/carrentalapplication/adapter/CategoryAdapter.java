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
import com.example.carrentalapplication.model.CategoryModel;
import com.example.carrentalapplication.uiActivity.Category;
import com.example.carrentalapplication.uiActivity.EBike;
import com.example.carrentalapplication.uiActivity.ElectricCar;
import com.example.carrentalapplication.uiActivity.HybridCar;
import com.example.carrentalapplication.uiActivity.Motorcycle;
import com.example.carrentalapplication.uiActivity.NormalCar;
import com.example.carrentalapplication.uiActivity.SportCar;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> list;

    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel categoryModel = list.get(position);
        Glide.with(context).load(list.get(position).getImage()).timeout(6000).into(holder.imageView);
        holder.textView.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (categoryModel.getName()) {
                    case "Normal":
                        intent = new Intent(context, NormalCar.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    case "Hybrid":
                        intent = new Intent(context, HybridCar.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    case "Electric":
                        intent = new Intent(context, ElectricCar.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    case "E-Bike":
                        intent = new Intent(context, EBike.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    case "Sport":
                        intent = new Intent(context, SportCar.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    case "Motorcycle":
                        intent = new Intent(context, Motorcycle.class);
                        intent.putExtra("type",list.get(holder.getAdapterPosition()));
                        break;
                    default:
                        intent = null;
                        break;
                }

                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image);
            textView = itemView.findViewById(R.id.category_text);
        }

    }
}
