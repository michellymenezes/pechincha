package com.projeto1.projeto1.models;

/**
 * Created by michelly on 14/06/17.
 */

public class Supermarket {

    private String name;
    private Address adress;
    private String image;
    private String cnpj;
    private Localization localization;

    public Supermarket(String name, Address adress, String image, String cnpj, Localization localization) {
        this.name = name;
        this.adress = adress;
        this.image = image;
        this.cnpj = cnpj;
        this.localization = localization;
    }

    public Supermarket(String name, Address adress) {
        this.name = name;
        this.adress = adress;
        this.image = "";
        this.cnpj = "";
        this.localization = null;
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
    public String toString() {
        return "Supermarket{" +
                "name='" + name + '\'' +
                ", adress=" + adress +
                ", image='" + image + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", localization=" + localization +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supermarket that = (Supermarket) o;

        if (!name.equals(that.name)) return false;
        return adress.equals(that.adress);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + adress.hashCode();
        return result;
    }
}
