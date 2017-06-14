package com.projeto1.projeto1.models;

/**
 * Created by michelly on 14/06/17.
 */

public class Product {

    private String name;
    private String brand;
    private String description;
    private String image;
    private String barcode;
    private String category;

    public Product(String name, String brand, String description, String image, String barcode, String category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.barcode = barcode;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", barcode='" + barcode + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
