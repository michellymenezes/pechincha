package com.projeto1.projeto1.models;

import java.util.UUID;

/**
 * Created by michelly on 14/06/17.
 */

public class Market {
    private String uniqueID;
    private String id;
    private String name;
    private Address address;
    private String image;
    private String cnpj;
    private Localization localization;

    public Market(String name, Address address, String image, String cnpj, Localization localization) {
        generateId();
        this.id = uniqueID;
        this.name = name;
        this.address = address;
        this.image = image;
        this.cnpj = cnpj;
        this.localization = localization;
    }

    public Market(String id, String name, Address address, String image, String cnpj, Localization localization) {
        if (id==null){
            generateId();
            this.id = uniqueID;
        } else {
            this.id = id;
        }
        this.name = name;
        this.address = address;
        this.image = image;
        this.cnpj = cnpj;
        this.localization = localization;
    }

    public Market(String name, Address adrdess) {
        this.name = name;
        this.address = address;
        this.image = " ";
        this.cnpj = "";
        this.localization = null;
        uniqueID = null;
        generateId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAdress() {
        return address;
    }

    public void setAdress(Address adress) {
        this.address = adress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Market market = (Market) o;

        return id != null ? id.equals(market.id) : market.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Market{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", image='" + image + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", localization=" + localization +
                '}';
    }

    private void generateId(){
        uniqueID = UUID.randomUUID().toString();
    }
}
