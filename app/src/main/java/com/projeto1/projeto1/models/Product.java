package com.projeto1.projeto1.models;

/**
 * Created by michelly on 14/06/17.
 */

public class Product {
    private String id;
    private String name;
    private String brand;
    private String description;
    private String image;
    private String barcode;
    private String category;
    private String subcategory;
    private double size;
    private String sizeUnity;

    public Product(String name, String brand, String description, String image, String barcode, String category, String subcategory, double size, String sizeUnity) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.barcode = barcode;
        this.category = category;
        this.subcategory = subcategory;
        this.size = size;
        this.sizeUnity = sizeUnity;
    }
    public Product(String id, String name, String brand, String description, String image, String barcode, String category, String subcategory, double size, String sizeUnity) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.image = image;
        this.barcode = barcode;
        this.category = category;
        this.subcategory = subcategory;
        this.id = id;
        this.size = size;
        this.sizeUnity = sizeUnity;
    }

    public Product(String name, String brand, String barcode, String category){
        this.name = name;
        this.brand = brand;
        this.barcode = barcode;
        this.category = category;
        this.description = " ";
        this.image = " ";
        this.subcategory = " ";
        this.size = 1;
        this.sizeUnity = "Uni";

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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getSizeUnity() {
        return sizeUnity;
    }

    public void setSizeUnity(String sizeUnity) {
        this.sizeUnity = sizeUnity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", barcode='" + barcode + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", size=" + size +
                ", sizeUnity='" + sizeUnity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return barcode.equals(product.barcode);

    }

    @Override
    public int hashCode() {
        return barcode.hashCode();
    }
}
