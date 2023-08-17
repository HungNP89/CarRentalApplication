package com.example.carrentalapplication.uiActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapplication.R;
import com.example.carrentalapplication.adapter.AvailableNormalAdapter;
import com.example.carrentalapplication.adapter.HotDealNormalAdapter;
import com.example.carrentalapplication.adapter.PopularNormalAdapter;
import com.example.carrentalapplication.model.AvailableNormalModel;
import com.example.carrentalapplication.model.HotDealNormalModel;
import com.example.carrentalapplication.model.PopularNormalModel;
import com.example.carrentalapplication.uiFragment.CarRental;
import com.example.carrentalapplication.uiFragment.ChatBot;
import com.example.carrentalapplication.uiFragment.Home;
import com.example.carrentalapplication.uiFragment.LocationPickUp;
import com.example.carrentalapplication.uiFragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class NormalCar extends AppCompatActivity {
    //For interacting with UI design in XML file
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    TextView textView1, textView2, textView3;

    //For interacting with RecycleView used for listing item in each category of the screen
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    ArrayList<PopularNormalModel> popularNormalModelArrayList;
    PopularNormalAdapter popularNormalAdapter;

    ArrayList<HotDealNormalModel> hotDealNormalModels;
    HotDealNormalAdapter hotDealNormalAdapter;

    ArrayList<AvailableNormalModel> availableNormalModels;
    AvailableNormalAdapter availableNormalAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_car);

        //Initialize UI element in the layout
        bottomNavigationView = findViewById(R.id.nav_view);
        searchView = findViewById(R.id.searchView);
        textView1 = findViewById(R.id.view_all_1);
        textView2 = findViewById(R.id.view_all_2);
        textView3 = findViewById(R.id.view_all_3);
        recyclerView1 = findViewById(R.id.popular_normal_car);
        recyclerView2 = findViewById(R.id.hot_deal_normal);
        recyclerView3 = findViewById(R.id.available);

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
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

        //connect data in fireStore and show in recycleView of popular normal car
        popularNormalModelArrayList = new ArrayList<>();
        fireStore.collection("Popular Normal").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    PopularNormalModel PNM = documentSnapshot.toObject(PopularNormalModel.class);
                    popularNormalModelArrayList.add(PNM);
                    popularNormalAdapter.notifyDataSetChanged();
                }
            } else {
                Log.w("", "Error getting document.", task.getException());
            }
        });
        popularNormalAdapter = new PopularNormalAdapter(this, popularNormalModelArrayList);
        recyclerView1.setAdapter(popularNormalAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView1.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of hot deal normal car
        hotDealNormalModels = new ArrayList<>();
        fireStore.collection("Hot Deal Normal").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    HotDealNormalModel HDNM = documentSnapshot.toObject(HotDealNormalModel.class);
                    hotDealNormalModels.add(HDNM);
                    hotDealNormalAdapter.notifyDataSetChanged();
                }
            } else {
                Log.w("", "Error getting document", task.getException());
            }
        });
        hotDealNormalAdapter = new HotDealNormalAdapter(this, hotDealNormalModels);
        recyclerView2.setAdapter(hotDealNormalAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView2.setHasFixedSize(true);

        //connect data in fireStore and show in recycleView of available normal car
        availableNormalModels = new ArrayList<>();
        String type = getIntent().getStringExtra("type");
        if(type == null || type.isEmpty()) {
            fireStore.collection("Available Car").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot: task.getResult()) {
                        AvailableNormalModel ANM = documentSnapshot.toObject(AvailableNormalModel.class);
                        availableNormalModels.add(ANM);
                        availableNormalAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if(type != null && type.equalsIgnoreCase("normal")) {
            fireStore.collection("Available Car").whereEqualTo("type","normal").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                        AvailableNormalModel ANM = documentSnapshot.toObject(AvailableNormalModel.class);
                        availableNormalModels.add(ANM);
                        availableNormalAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        availableNormalAdapter = new AvailableNormalAdapter(this,availableNormalModels);
        recyclerView3.setAdapter(availableNormalAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView3.setHasFixedSize(true);
    }
}