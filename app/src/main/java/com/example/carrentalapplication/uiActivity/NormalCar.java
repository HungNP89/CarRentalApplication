package com.example.carrentalapplication.uiActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalapplication.R;
import com.example.carrentalapplication.adapter.AvailableAdapter;
import com.example.carrentalapplication.adapter.HotDealAdapter;
import com.example.carrentalapplication.adapter.PopularAdapter;
import com.example.carrentalapplication.contract.NormalCarContract;
import com.example.carrentalapplication.model.AvailableModel;
import com.example.carrentalapplication.model.HotDealModel;
import com.example.carrentalapplication.model.PopularModel;
import com.example.carrentalapplication.presenter.NormalCarPresenter;
import com.example.carrentalapplication.uiFragment.CarRental;
import com.example.carrentalapplication.uiFragment.ChatBot;
import com.example.carrentalapplication.uiFragment.Home;
import com.example.carrentalapplication.uiFragment.LocationPickUp;
import com.example.carrentalapplication.uiFragment.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class NormalCar extends AppCompatActivity implements NormalCarContract.View {
    //For interacting with UI design in XML file
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    TextView textView1, textView2, textView3;

    //For interacting with RecycleView used for listing item in each category of the screen
    RecyclerView recyclerView1, recyclerView2, recyclerView3;

    PopularAdapter popularAdapter;
    HotDealAdapter hotDealAdapter;
    AvailableAdapter availableAdapter;

    private NormalCarContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_car);

        presenter = new NormalCarPresenter();
        presenter.attachView(this);

        presenter.loadAvailableNormalCar();
        presenter.loadPopularNormalCar();
        presenter.loadHotDealNormalCar();

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
    }

    @Override
    public void showPopularNormalCar(List<PopularModel> list) {
        popularAdapter = new PopularAdapter(this,list);
        recyclerView1.setAdapter(popularAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView1.setHasFixedSize(true);
    }

    @Override
    public void showHotDealNormalCar(List<HotDealModel> list) {
        hotDealAdapter = new HotDealAdapter(this,list);
        recyclerView2.setAdapter(hotDealAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView2.setHasFixedSize(true);
    }

    @Override
    public void showAvailableNormalCar(List<AvailableModel> list) {
        availableAdapter = new AvailableAdapter(this,list);
        recyclerView3.setAdapter(availableAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView3.setHasFixedSize(true);
    }

    @Override
    public void showError(String message) {
        Log.w("","Errors");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}