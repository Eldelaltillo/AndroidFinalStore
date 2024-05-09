package com.example.proyectofinal;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private String title; //Nombre producto
    private String description; //Texto descriptivo del producto
    private String Storage;
    private String RAM;
    private String GPU;
    private String picUrl; // URL del la imagen del producto
    private int review; // Cuantos comentarios o reseñas tiene el producto
    private double score; // Cuantas estrellas tiene el producto
    private int numberinCart;
    private double price; // Precio del producto

    public PopularDomain(String title, String description, String storage, String RAM, String GPU, String picUrl, int review, double score, double price) {
        this.title = title;
        this.description = description;
        this.Storage = storage;
        this.RAM = RAM;
        this.GPU = GPU;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.price = price;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String storage) {
        Storage = storage;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }
}
