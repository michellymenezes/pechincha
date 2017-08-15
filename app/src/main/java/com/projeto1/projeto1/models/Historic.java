package com.projeto1.projeto1.models;

import java.util.Date;

/**
 * Created by michelly on 14/08/17.
 */

public class Historic {

    private Date saleDate;
    private double value;

    public Historic(Date date, double value) {
        this.saleDate = date;
        this.value = value;
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
        return saleDate.equals(historic.saleDate);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = saleDate.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Historic{" +
                "date=" + saleDate +
                ", value=" + value +
                '}';
    }
}
