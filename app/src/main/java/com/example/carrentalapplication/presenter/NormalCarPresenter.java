package com.example.carrentalapplication.presenter;

import com.example.carrentalapplication.Support.MyFirebase;
import com.example.carrentalapplication.contract.NormalCarContract;
import com.example.carrentalapplication.model.AvailableModel;
import com.example.carrentalapplication.model.HotDealModel;
import com.example.carrentalapplication.model.PopularModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NormalCarPresenter implements NormalCarContract.Presenter{
    private NormalCarContract.View view ;
    private FirebaseFirestore db;

    public NormalCarPresenter() {
        this.db = MyFirebase.getInstance();
    }
    @Override
    public void attachView(NormalCarContract.View view) {
        this.view = view ;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadPopularNormalCar() {
        db.collection("Popular Car By Category").whereEqualTo("type","normal").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<PopularModel> list = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                PopularModel cars = documentSnapshot.toObject(PopularModel.class);
                list.add(cars);
            }
            if (view != null) {
                view.showPopularNormalCar(list);
            }
        }).addOnFailureListener(e -> {
            if ( view != null ) {
                view.showError("Error loading popular normal car");
            }
        });
    }

    @Override
    public void loadHotDealNormalCar() {
        db.collection("Hot Deal By Category").whereEqualTo("type","normal").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<HotDealModel> list = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                HotDealModel cars = documentSnapshot.toObject(HotDealModel.class);
                list.add(cars);
            }
            if (view != null) {
                view.showHotDealNormalCar(list);
            }
        }).addOnFailureListener(e -> {
            if ( view != null ) {
                view.showError("Error loading popular normal car");
            }
        });
    }

    @Override
    public void loadAvailableNormalCar() {
        db.collection("Available Car").whereEqualTo("type","normal").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<AvailableModel> list = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                AvailableModel cars = documentSnapshot.toObject(AvailableModel.class);
                list.add(cars);
            }
            if (view != null) {
                view.showAvailableNormalCar(list);
            }
        }).addOnFailureListener(e -> {
            if ( view != null ) {
                view.showError("Error loading popular normal car");
            }
        });
    }
}
