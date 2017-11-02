package com.projeto1.projeto1.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by michelly on 14/08/17.
 */

public class Historic {

    private Date saleDate;
    private double value;
    private int likeCount, dislikeCount, reportCount;
    private List<String> likeUsers, dislikeUsers;

    public Historic(Date date, double value, int likeCount, int dislikeCount, int reportCount, List<String> likeUsers, List<String> dislikeUsers) {
        this.saleDate = date;
        this.value = value;
        this.likeCount = likeCount;
        this.likeUsers = likeUsers;
        this.dislikeCount = dislikeCount;
        this.reportCount = reportCount;
        this.dislikeUsers = dislikeUsers;
        this.dislikeUsers = dislikeUsers;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getRepostCount() {
        return reportCount;
    }

    public void setRepostCount(int repostCount) {
        this.reportCount = repostCount;
    }

    public List<String> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(ArrayList<String> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public List<String> getDislikeUsers() {
        return dislikeUsers;
    }

    public void setDislikeUsers(ArrayList<String> dislikeUsers) {
        this.dislikeUsers = dislikeUsers;
    }

    public Date getDate() {
        return saleDate;
    }

    public void setDate(Date date) {
        this.saleDate = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Historic historic = (Historic) o;

        if (Double.compare(historic.value, value) != 0) return false;
        if (likeCount != historic.likeCount) return false;
        if (dislikeCount != historic.dislikeCount) return false;
        if (reportCount != historic.reportCount) return false;
        if (!saleDate.equals(historic.saleDate)) return false;
        if (likeUsers != null ? !likeUsers.equals(historic.likeUsers) : historic.likeUsers != null)
            return false;
        return dislikeUsers != null ? dislikeUsers.equals(historic.dislikeUsers) : historic.dislikeUsers == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = saleDate.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + likeCount;
        result = 31 * result + dislikeCount;
        result = 31 * result + reportCount;
        result = 31 * result + (likeUsers != null ? likeUsers.hashCode() : 0);
        result = 31 * result + (dislikeUsers != null ? dislikeUsers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Historic{" +
                "saleDate=" + saleDate +
                ", value=" + value +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", repostCount=" + reportCount +
                ", likeUsers=" + likeUsers +
                ", dislikeUsers=" + dislikeUsers +
                '}';
    }
}
