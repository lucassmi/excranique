package com.bonobocorp.joker.litrocent.Model;


public class VehiculeModel {

    private String vehiculeType;
    private String vehiculeNom;
    private int id;
    private int kmagetotal;
    private int kmageInitial;
    private int kmageLastPleinComplet;
    private String dateLastPleinComplet;


    public VehiculeModel(String vehiculeNom, String vehiculeType, int kmagetotal, int kmageInitial, int kmageLastPleinComplet, String dateLastPleinComplet) {
        this.vehiculeType = vehiculeType;
        this.vehiculeNom = vehiculeNom;
        this.kmagetotal = kmagetotal;
        this.kmageInitial = kmageInitial;
        this.kmageLastPleinComplet = kmageLastPleinComplet;
        this.dateLastPleinComplet = dateLastPleinComplet;
    }

    public VehiculeModel (String vehiculeNom, String vehiculeType) {
        super();
        this.vehiculeNom = vehiculeNom;
        this.vehiculeType = vehiculeType;

    }

    public VehiculeModel (String vehiculeNom, String vehiculeType, int kmageInitial) {
        super();
        this.vehiculeNom = vehiculeNom;
        this.vehiculeType = vehiculeType;
        this.kmageInitial = kmageInitial;
        this.kmagetotal = kmageInitial;
    }

    public VehiculeModel (int id, String vehiculeNom, String vehiculeType) {
        super();
        this.id = id;
        this.vehiculeNom = vehiculeNom;
        this.vehiculeType = vehiculeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehiculeType() {
        return vehiculeType;
    }

    public void setVehiculeType(String vehiculeModel) {
        this.vehiculeType = vehiculeModel;
    }

    public String getVehiculeNom() {
        return vehiculeNom;
    }

    public void setVehiculeNom(String vehiculeNom) {
        this.vehiculeNom = vehiculeNom;
    }

    public int getKmagetotal() {
        return kmagetotal;
    }

    public void setKmagetotal(int kmagetotal) {
        this.kmagetotal = kmagetotal;
    }

    public int getKmageInitial() {
        return kmageInitial;
    }

    public void setKmageInitial(int kmageInitial) {
        this.kmageInitial = kmageInitial;
    }

    public int getKmageLastPleinComplet() {
        return kmageLastPleinComplet;
    }

    public void setKmageLastPleinComplet(int kmageLastPleinComplet) {
        this.kmageLastPleinComplet = kmageLastPleinComplet;
    }

    public String getDateLastPleinComplet() {
        return dateLastPleinComplet;
    }

    public void setDateLastPleinComplet(String dateLastPleinComplet) {
        this.dateLastPleinComplet = dateLastPleinComplet;
    }
}
