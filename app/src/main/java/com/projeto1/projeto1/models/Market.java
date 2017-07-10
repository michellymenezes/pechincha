package com.projeto1.projeto1.models;

/**
 * Created by michelly on 14/06/17.
 */

public class Market {
    private String id;
    private String name;
    private Address adress;
    private String image;
    private String cnpj;
    private Localization localization;

    public Market(String name, Address adress, String image, String cnpj, Localization localization) {
        this.id = "";
        this.name = name;
        this.adress = adress;
        this.image = image;
        this.cnpj = cnpj;
        this.localization = localization;
    }

    public Market(String id, String name, Address adress, String image, String cnpj, Localization localization) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.image = image;
        this.cnpj = cnpj;
        this.localization = localization;
    }

    public Market(String name, Address adress) {
        this.name = name;
        this.adress = adress;
        this.image = "";
        this.cnpj = "";
        this.localization = null;
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
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
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
                ", adress=" + adress +
                ", image='" + image + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", localization=" + localization +
                '}';
    }
}
