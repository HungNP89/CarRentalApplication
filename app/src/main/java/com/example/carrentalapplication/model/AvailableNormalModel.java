package com.example.carrentalapplication.model;

import java.io.Serializable;

public class AvailableNormalModel implements Serializable {
    String name;
    int edition;
    double rating;
    int price;
    String image;
    String condition;
    String type;
    String info;

    public AvailableNormalModel() {

    }

    public AvailableNormalModel(String name, int edition, String image, String condition, String type, double rating, int price, String info) {
        this.name = name;
        this.edition = edition;
        this.image = image;
        this.condition = condition;
        this.type = type;
        this.rating = rating;
        this.price = price;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
