package com.bonobocorp.joker.litrocent.Model;

public class PleinModel {

    private int id;
    private int kmageParcouru;
    private float qttyCarb;
    private float prixTotal;
    private String typeCarb;
    private float consommation;
    private float consommationajuste;
    private String date;
    private String pleinComplet;

    public PleinModel( int kmageParcouru, float qttyCarb, float prixTotal, String typeCarb,
                       float consommation, float consommationajuste, String date, String pleinComplet) {

        this.kmageParcouru = kmageParcouru;
        this.qttyCarb = qttyCarb;
        this.prixTotal = prixTotal;
        this.typeCarb = typeCarb;
        this.consommation = consommation;
        this.consommationajuste = consommationajuste;
        this.date = date;
        this.pleinComplet = pleinComplet;
    }

    public PleinModel( int kmageParcouru, float qttyCarb, float prixTotal, String typeCarb,
                       float consommation, float consommationajuste, String date, String pleinComplet, int id) {

        this.kmageParcouru = kmageParcouru;
        this.qttyCarb = qttyCarb;
        this.prixTotal = prixTotal;
        this.typeCarb = typeCarb;
        this.consommation = consommation;
        this.consommationajuste = consommationajuste;
        this.date = date;
        this.pleinComplet = pleinComplet;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKmageParcouru() {
        return kmageParcouru;
    }

    public void setKmageParcouru(int kmageParcouru) {
        this.kmageParcouru = kmageParcouru;
    }

    public float getQttyCarb() {
        return qttyCarb;
    }

    public void setQttyCarb(float qttyCarb) {
        this.qttyCarb = qttyCarb;
    }

    public float getConsommation() {
        return consommation;
    }

    public void setConsommation(float consommation) {
        this.consommation = consommation;
    }

    public float getConsommationajuste() {
        return consommationajuste;
    }

    public void setConsommationajuste(float consommationajuste) {
        this.consommationajuste = consommationajuste;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getTypeCarb() {
        return typeCarb;
    }

    public void setTypeCarb(String typeCarb) {
        this.typeCarb = typeCarb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPleinComplet() {
        return pleinComplet;
    }

    public void setPleinComplet(String pleinComplet) {
        this.pleinComplet = pleinComplet;
    }
}
