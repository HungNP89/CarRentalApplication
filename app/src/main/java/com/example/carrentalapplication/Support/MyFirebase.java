package com.example.carrentalapplication.Support;

import com.google.firebase.firestore.FirebaseFirestore;

public class MyFirebase {
    private static FirebaseFirestore fireStoreDB;

    private MyFirebase() {

    }

    public static FirebaseFirestore getInstance() {
        if(fireStoreDB == null) {
            fireStoreDB = FirebaseFirestore.getInstance();
        }

        return fireStoreDB;
    }
}
