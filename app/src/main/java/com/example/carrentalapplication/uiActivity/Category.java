package com.example.carrentalapplication.uiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.carrentalapplication.R;
import com.example.carrentalapplication.Support.MyFirebase;
import com.example.carrentalapplication.adapter.CategoryAdapter;
import com.example.carrentalapplication.model.CategoryModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    //For interacting with design ID in XML file
    Button btnSkip;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;
    FirebaseFirestore db;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        //Initialize UI elements in the layout
        recyclerView = findViewById(R.id.category_menu);
        btnSkip = findViewById(R.id.skipButton);

        //Initialize the skip button
        btnSkip.setOnClickListener(v -> {
            Intent skip = new Intent(Category.this, MainActivity.class);
            startActivity(skip);
            finish();
        });
        //Firebase connect
        db = MyFirebase.getInstance();

        //connect data in Firebase and show list of category
        categoryModels = new ArrayList<>();

        //Initialize the adapter
        categoryAdapter = new CategoryAdapter(this, categoryModels);

        //Set Layout manager and adapter for RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        //fetch the data and update in adapter
        db.collection("Category").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot qds : task.getResult()) {
                    CategoryModel categoryModel = qds.toObject(CategoryModel.class);
                    categoryModels.add(categoryModel);
                }
                categoryAdapter.notifyDataSetChanged();
            } else {
                Log.w("", "Error getting documents", task.getException());
            }
        });

    }
}