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
import com.example.carrentalapplication.adapter.AvailableHybridAdapter;
import com.example.carrentalapplication.adapter.HotDealHybridAdapter;
import com.example.carrentalapplication.adapter.PopularHybridAdapter;
import com.example.carrentalapplication.model.AvailableHybridModel;
import com.example.carrentalapplication.model.HotDealHybridModel;
import com.example.carrentalapplication.model.PopularHybridModel;
import com.example.carrentalapplication.uiFragment.CarRental;
import com.example.carrentalapplication.uiFragment.ChatBot;
import com.example.carrentalapplication.uiFragment.Home;
import com.example.carrentalapplication.uiFragment.LocationPickUp;
import com.example.carrentalapplication.uiFragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HybridCar extends AppCompatActivity {
    //For interacting with UI design in XML file
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    TextView textView1, textView2, textView3;

    //For interacting with RecycleView used for listing item in each category of the screen
    RecyclerView recyclerView1, recyclerView2, recyclerView3;

    ArrayList<PopularHybridModel> popularHybridModels;
    PopularHybridAdapter popularHybridAdapter;

    ArrayList<HotDealHybridModel> hotDealHybridModels;
    HotDealHybridAdapter hotDealHybridAdapter;

    ArrayList<AvailableHybridModel> availableHybridModels;
    AvailableHybridAdapter availableHybridAdapter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid_car);

        //Initialize UI element in the layout
        bottomNavigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.searchView);
        textView1 = findViewById(R.id.view_all_1);
        textView2 = findViewById(R.id.view_all_2);
        textView3 = findViewById(R.id.view_all_3);
        recyclerView1 = findViewById(R.id.popular_hybrid_car);
        recyclerView2 = findViewById(R.id.hot_deal_hybrid);
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

        //Firebase connected
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

        //connect data in fireStore and show in recycleView of popular normal car
        popularHybridModels = new ArrayList<>();
        if(type == null || type.isEmpty()) {
            fireStore.collection("Popular Car By Category").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        PopularHybridModel PHM = documentSnapshot.toObject(PopularHybridModel.class);
                        popularHybridModels.add(PHM);
                        popularHybridAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("electric")) {
            fireStore.collection("Popular Car By Category").whereEqualTo("type", "electric").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        PopularHybridModel PHM = documentSnapshot.toObject(PopularHybridModel.class);
                        popularHybridModels.add(PHM);
                        popularHybridAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("", "Error getting document.", task.getException());
                }
            });
        }
        popularHybridAdapter = new PopularHybridAdapter(this, popularHybridModels);
        recyclerView1.setAdapter(popularHybridAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView1.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of hot deal normal car
        hotDealHybridModels = new ArrayList<>();
        if(type == null || type.isEmpty()) {
            fireStore.collection("Hot Deal By Category").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        HotDealHybridModel HHM = documentSnapshot.toObject(HotDealHybridModel.class);
                        hotDealHybridModels.add(HHM);
                        hotDealHybridAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("electric")) {
            fireStore.collection("Hot Deal By Category").whereEqualTo("type", "electric").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        HotDealHybridModel HHM = documentSnapshot.toObject(HotDealHybridModel.class);
                        hotDealHybridModels.add(HHM);
                        hotDealHybridAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("", "Error getting document.", task.getException());
                }
            });
        }
        hotDealHybridAdapter = new HotDealHybridAdapter(this, hotDealHybridModels);
        recyclerView2.setAdapter(hotDealHybridAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView2.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of available normal car
        availableHybridModels = new ArrayList<>();
        if (type == null || type.isEmpty()) {
            fireStore.collection("Available Car").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        AvailableHybridModel AHM = documentSnapshot.toObject(AvailableHybridModel.class);
                        availableHybridModels.add(AHM);
                        availableHybridAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("electric")) {
            fireStore.collection("Available Car").whereEqualTo("type", "electric").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        AvailableHybridModel AHM = documentSnapshot.toObject(AvailableHybridModel.class);
                        availableHybridModels.add(AHM);
                        availableHybridAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.w("", "Error getting document.", task.getException());
                }
            });
        }
        availableHybridAdapter = new AvailableHybridAdapter(this, availableHybridModels);
        recyclerView3.setAdapter(availableHybridAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView3.setHasFixedSize(true);

    }
}