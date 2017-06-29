package com.projeto1.projeto1.models;

import java.util.Date;
import java.util.List;

/**
 * Created by michelly on 14/06/17.
 */

public class Sale {

    private String id;
    private String product;
    private Double regularPrice;
    private Double currentPrice;
    private Date expirationDate;
    private String supermarket;
    //private Supermarket supermarket;
    private int quantity;
    private double starts;
    private String author;
    //private User author;
    private Date publicationDate;
    //private Boolean expiredSale;
    private int likes;
    //private List<User> likes;
    private int dislikes;
    //private List<User> dislikes;
    //private List<User> reports;

    private String category;

    public Sale(String id, String product, Double regularPrice, Double currentPrice, Date expirationDate, String supermarket, int quantity, double starts, String author, Date publicationDate, int likes, int dislikes, String category) {
        this.id = id;
        this.product = product;
        this.regularPrice = regularPrice;
        this.currentPrice = currentPrice;
        this.expirationDate = expirationDate;
        this.supermarket = supermarket;
        this.quantity = quantity;
        this.starts = starts;
        this.author = author;
        this.publicationDate = publicationDate;
        //this.reports = reports;
        //this.expiredSale = expiredSale;
        this.likes = likes;
        this.dislikes = dislikes;

        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

/*    public Boolean getExpiredSale() {
        return expiredSale;
    }

    public void setExpiredSale(Boolean expiredSale) {
        this.expiredSale = expiredSale;
    }*/

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /*
    public List<User> getReports() {
        return reports;
    }

    public void setReports(List<User> reports) {
        this.reports = reports;
    }
*/

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", product='" + product +
                ", regularPrice=" + regularPrice +
                ", currentPrice=" + currentPrice +
                ", expirationDate=" + expirationDate +
                ", supermarket=" + supermarket +
                ", quantity=" + quantity +
                ", starts=" + starts +
                ", author=" + author +
                ", publicationDate=" + publicationDate +
                //", expiredSale=" + expiredSale +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                //", reports=" + reports +
                ", category=" + category +

                '}';
    }
}
