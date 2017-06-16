package com.projeto1.projeto1.models;

import java.util.Date;

/**
 * Created by michelly on 14/06/17.
 */

public class Sale {

    private String productName;
    private Double regularPrice;
    private Double currentPrice;
    private Date expirationDate;
    private Supermarket supermarket;
    private int quantity;
    private double starts;
    private User author;
    private Date publicationDate;
    private int reports;
    private Boolean expiredSale;

    public Sale(String productName, Double regularPrice, Double currentPrice, Date expirationDate, Supermarket supermarket, int quantity, double starts, User author, Date publicationDate, int reports, Boolean expiredSale) {
        this.productName = productName;
        this.regularPrice = regularPrice;
        this.currentPrice = currentPrice;
        this.expirationDate = expirationDate;
        this.supermarket = supermarket;
        this.quantity = quantity;
        this.starts = starts;
        this.author = author;
        this.publicationDate = publicationDate;
        this.reports = reports;
        this.expiredSale = expiredSale;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Supermarket getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(Supermarket supermarket) {
        this.supermarket = supermarket;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getStarts() {
        return starts;
    }

    public void setStarts(double starts) {
        this.starts = starts;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public Boolean getExpiredSale() {
        return expiredSale;
    }

    public void setExpiredSale(Boolean expiredSale) {
        this.expiredSale = expiredSale;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "productName='" + productName + '\'' +
                ", regularPrice=" + regularPrice +
                ", currentPrice=" + currentPrice +
                ", expirationDate=" + expirationDate +
                ", supermarket=" + supermarket +
                ", quantity=" + quantity +
                ", starts=" + starts +
                ", author=" + author +
                ", publicationDate=" + publicationDate +
                ", reports=" + reports +
                ", expiredSale=" + expiredSale +
                '}';
    }
}
