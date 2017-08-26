package com.projeto1.projeto1.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private int likeCount;
    private int reportCount;
    private Set<String> likeUsers;
    private Set<String> reportUsers;

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
        this.likeCount = 0;
        this.reportCount = 0;
        this.likeUsers = new HashSet<String>();
        this.reportUsers = new HashSet<String>();

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
        this.likeCount = 0;
        this.reportCount = 0;
        this.likeUsers = new HashSet<String>();
        this.reportUsers = new HashSet<String>();

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
        this.likeCount = 0;
        this.reportCount = 0;
        this.likeUsers = new HashSet<String>();
        this.reportUsers = new HashSet<String>();

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
        if (historic==null){
            historic = new ArrayList<>();
        }
        this.historic.add(hist);
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public Set<String> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(Set<String> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public Set<String> getReportUsers() {
        return reportUsers;
    }

    public void setReportUsers(Set<String> reportUsers) {
        this.reportUsers = reportUsers;
    }

    public boolean addRemoveLike(String userId){
        if(likeUsers.contains(userId)){
            likeUsers.remove(userId);
            likeCount++;
            return false;
        }
        else {
            likeUsers.add(userId);
            likeCount--;
            return true;
        }
    }

    public boolean hasLiked(String userId){
        return likeUsers.contains(userId);
    }

    public boolean addRemoveReport(String userId){
        if(reportUsers.contains(userId)){
            reportUsers.remove(userId);
            reportCount++;
            return false;
        }
        else {
            reportUsers.add(userId);
            reportCount--;
            return true;
        }
    }

    public boolean hasReported(String userId){
        return reportUsers.contains(userId);
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
                ", likeCount=" + likeCount +
                ", reportCount=" + reportCount +
                ", likeUsers=" + likeUsers +
                ", reportUsers=" + reportUsers +
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
