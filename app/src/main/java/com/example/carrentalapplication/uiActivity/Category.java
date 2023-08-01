package com.example.carrentalapplication.uiActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.carrentalapplication.R;
import com.example.carrentalapplication.uiFragment.Home;

public class Category extends AppCompatActivity {
    //For interacting with design ID in XML file
    Button btnSkip;
    CardView cardNormal, cardHybrid, cardSport, cardElectric, cardEBike, cardMotorcycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialize UI elements in the layout
        btnSkip = findViewById(R.id.skipButton);
        cardNormal = findViewById(R.id.category_normal);
        cardHybrid = findViewById(R.id.category_hybrid);
        cardEBike = findViewById(R.id.category_e_bike);
        cardElectric = findViewById(R.id.category_electric);
        cardSport = findViewById(R.id.category_sport);
        cardMotorcycle = findViewById(R.id.category_motorcycle);

        //Clickable button to change screen
        btnSkip.setOnClickListener(view -> {
            Intent skip = new Intent(Category.this, Home.class);
            startActivity(skip);
            finish();
        });

        cardNormal.setOnClickListener(view -> {
            Intent toNormal = new Intent(Category.this, NormalCar.class);
            startActivity(toNormal);
            finish();
        });

        cardHybrid.setOnClickListener(view -> {
            Intent toHybrid = new Intent(Category.this, HybridCar.class);
            startActivity(toHybrid);
            finish();
        });

        cardElectric.setOnClickListener(view -> {
            Intent toElectric = new Intent(Category.this, ElectricCar.class);
            startActivity(toElectric);
            finish();
        });

        cardSport.setOnClickListener(view -> {
            Intent toSport = new Intent(Category.this, SportCar.class);
            startActivity(toSport);
            finish();
        });

        cardEBike.setOnClickListener(view -> {
            Intent toEBike = new Intent(Category.this, EBike.class);
            startActivity(toEBike);
            finish();
        });

        cardMotorcycle.setOnClickListener(view -> {
            Intent toMotorcycle = new Intent(Category.this, Motorcycle.class);
            startActivity(toMotorcycle);
            finish();
        });
    }
}