package com.projeto1.projeto1.models;

import java.util.ArrayList;
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
    private String expirationDate;
    private Date publicationDate;
    private String authorId;
    private int quantity;
    private String quantUni;
    private List<Historic> historic;

    public Sale(String productId, String marketId, Double salePrice, Double regularPrice, String expirationDate, String authorId, int quantity, String quantUni, List<Historic> historic) {
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.authorId = authorId;
        this.quantity = quantity;
        this.quantUni = quantUni;
        this.historic = historic;

        this.id = " ";
    }

    public Sale(String productId, String marketId, Double salePrice, Double regularPrice, String expirationDate, String authorId, int quantity, String quantUni) {
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.authorId = authorId;
        this.quantity = quantity;
        this.quantUni = quantUni;
        this.historic = new ArrayList<Historic>();

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

    public Sale(String id, String productId, String marketId, Double salePrice, Double regularPrice, String expirationDate, Date publicationDate, String authorId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
        this.quantity = quantity;
        this.historic = new ArrayList<Historic>();
    }

    public Sale(String id, String productId, String marketId, Double salePrice, Double regularPrice, String expirationDate, Date publicationDate, String authorId, int quantity, List<Historic> historic) {
        this.id = id;
        this.productId = productId;
        this.marketId = marketId;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.expirationDate = expirationDate;
        this.publicationDate = publicationDate;
        this.authorId = authorId;
        this.quantity = quantity;
        this.historic = historic;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
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

    public List<Historic> getHistoric() {
        return historic;
    }

    public void setHistoric(List<Historic> historic) {
        this.historic = historic;
    }

    public void addHistoric(Historic hist){
        this.historic.add(hist);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", salePrice=" + salePrice +
                ", regularPrice=" + regularPrice +
                ", expirationDate='" + expirationDate + '\'' +
                ", publicationDate=" + publicationDate +
                ", authorId='" + authorId + '\'' +
                ", quantity=" + quantity +
                ", quantUni='" + quantUni + '\'' +
                ", historic=" + historic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        if (quantity != sale.quantity) return false;
        if (!id.equals(sale.id)) return false;
        if (!productId.equals(sale.productId)) return false;
        if (!marketId.equals(sale.marketId)) return false;
        return quantUni != null ? quantUni.equals(sale.quantUni) : sale.quantUni == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + marketId.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + (quantUni != null ? quantUni.hashCode() : 0);
        return result;
    }
}
