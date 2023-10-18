package com.example.carrentalapplication.uiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.carrentalapplication.R;
import com.example.carrentalapplication.Support.MyFirebase;
import com.example.carrentalapplication.adapter.AvailableAdapter;
import com.example.carrentalapplication.adapter.HotDealAdapter;
import com.example.carrentalapplication.adapter.PopularAdapter;
import com.example.carrentalapplication.model.AvailableModel;
import com.example.carrentalapplication.model.HotDealModel;
import com.example.carrentalapplication.model.PopularModel;
import com.example.carrentalapplication.uiFragment.CarRental;
import com.example.carrentalapplication.uiFragment.ChatBot;
import com.example.carrentalapplication.uiFragment.Home;
import com.example.carrentalapplication.uiFragment.LocationPickUp;
import com.example.carrentalapplication.uiFragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ElectricCar extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    TextView textView1, textView2, textView3;

    //For interacting with RecycleView used for listing item in each category of the screen
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    ArrayList<PopularModel> popularElectricModels;
    PopularAdapter popularElectricAdapter;

    ArrayList<HotDealModel> hotDealElectricModels;
    HotDealAdapter hotDealElectricAdapter;

    ArrayList<AvailableModel> availableElectricModels;
    AvailableAdapter availableElectricAdapter;

    FirebaseFirestore db;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_car);

        //Initialize UI element in the layout
        bottomNavigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.searchView);
        textView1 = findViewById(R.id.view_all_1);
        textView2 = findViewById(R.id.view_all_2);
        textView3 = findViewById(R.id.view_all_3);
        recyclerView1 = findViewById(R.id.popular_electric_car);
        recyclerView2 = findViewById(R.id.hot_deal_electric);
        recyclerView3 = findViewById(R.id.available);
        String type = getIntent().getStringExtra("type");
        //Set up clickable bottom navigation bar to redirect to other main fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.navigation_home) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
                        return true;
                    } else if (item.getItemId() == R.id.navigation_car_rental) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CarRental()).commit();
                        return true;
                    } else if (item.getItemId() == R.id.navigation_chat) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatBot()).commit();
                        return true;
                    } else if (item.getItemId() == R.id.navigation_location_pick_up) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LocationPickUp()).commit();
                        return true;
                    } else if (item.getItemId() == R.id.navigation_setting) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Setting()).commit();
                        return true;
                    }
                    return false;
                });
        //Hide bottom navigation bar on scroll
        //Firebase connected
        db = MyFirebase.getInstance();

        //connect data in fireStore and show in recycleView of popular normal car
        popularElectricModels = new ArrayList<>();
        boolean forNormal = type == null || type.trim().isEmpty() || "electric".equalsIgnoreCase(type);
        if(forNormal) {
            db.collection("Popular Car By Category").whereEqualTo("type","electric").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        PopularModel PNM = documentSnapshot.toObject(PopularModel.class);
                        popularElectricModels.add(PNM);
                        popularElectricAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting document.",task.getException());
                }
            });
        }

        popularElectricAdapter = new PopularAdapter(this, popularElectricModels);
        recyclerView1.setAdapter(popularElectricAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView1.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of hot deal normal car
        hotDealElectricModels = new ArrayList<>();
        if(forNormal) {
            db.collection("Hot Deal By Category").whereEqualTo("type","electric").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        HotDealModel HNM = documentSnapshot.toObject(HotDealModel.class);
                        hotDealElectricModels.add(HNM);
                        hotDealElectricAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting document.",task.getException());
                }
            });
        }


        hotDealElectricAdapter = new HotDealAdapter(this, hotDealElectricModels);
        recyclerView2.setAdapter(hotDealElectricAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView2.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of available normal car
        availableElectricModels = new ArrayList<>();
        if (forNormal) {
            db.collection("Available Car").whereEqualTo("type","electric").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        AvailableModel ANM = documentSnapshot.toObject(AvailableModel.class);
                        availableElectricModels.add(ANM);
                        availableElectricAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("","Error getting document.",task.getException());
                }
            });
        }

        availableElectricAdapter = new AvailableAdapter(this, availableElectricModels);
        recyclerView3.setAdapter(availableElectricAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView3.setHasFixedSize(true);
    }
}