package com.projeto1.projeto1.models;

import java.util.Date;
import java.util.List;

/**
 * Created by michelly on 14/06/17.
 */

public class Sale {

    private String id;
    private String productId;
    private String marketId;
    private Double salePrice;
    private Double regularPrice;
    private Date expirationDate;
    private Date publicationDate;
    private String authorId;
    private int quantity;
    private String quantUni;

    public Sale(String productId, String marketId, Double salePrice, Double regularPrice, Date expirationDate, String authorId, int quantity, String quantUni) {
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.authorId = authorId;
        this.quantity = quantity;
        this.quantUni = quantUni;

        this.id = " ";
    }

    public Sale(String productId, String marketId, Double salePrice, Double regularPrice, String authorId, int quantity, String quantUni) {
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.authorId = authorId;
        this.quantity = quantity;
        this.quantUni = quantUni;

        this.id = " ";
    }

    public Sale(String id, String productId, String marketId, Double salePrice, Double regularPrice, Date expirationDate, Date publicationDate, String authorId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuantUni() {
        return quantUni;
    }

    public void setQuantUni(String quantUni) {
        this.quantUni = quantUni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        if (quantity != sale.quantity) return false;
        if (!productId.equals(sale.productId)) return false;
        return marketId.equals(sale.marketId);

    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + marketId.hashCode();
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", salePrice=" + salePrice +
                ", regularPrice=" + regularPrice +
                ", expirationDate=" + expirationDate +
                ", publicationDate=" + publicationDate +
                ", authorId='" + authorId + '\'' +
                ", quantity=" + quantity +
                ", quantUni='" + quantUni + '\'' +
                '}';
    }
}
