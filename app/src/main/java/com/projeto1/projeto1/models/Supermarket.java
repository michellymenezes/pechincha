package com.projeto1.projeto1.models;

/**
 * Created by michelly on 14/06/17.
 */

public class Supermarket {

    private String name;
    private String adress;
    private String image;
    private String cnpj;

    public Supermarket(String name, String adress, String image, String cnpj) {
        this.name = name;
        this.adress = adress;
        this.image = image;
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
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
                ", adress='" + adress + '\'' +
                ", image='" + image + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
