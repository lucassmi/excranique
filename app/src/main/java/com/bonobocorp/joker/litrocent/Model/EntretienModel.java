package com.bonobocorp.joker.litrocent.Model;

public class EntretienModel {

    public int id;
    public int vehid;
    public int intervalleKmage;
    public int kmageFixe;
    public String dateEntretien;
    public String nomEntretien;
    public String commentaireEntretien;
    public String typeEntretien;
    public String isFixe;

    public EntretienModel(String nomEntretien, String typeEntretien, int intervalleKmage, int kmageFixe, String dateEntretien, String commentaireEntretien, String isFixe) {

        this.intervalleKmage = intervalleKmage;
        this.typeEntretien = typeEntretien;
        this.kmageFixe = kmageFixe;
        this.dateEntretien = dateEntretien;
        this.nomEntretien = nomEntretien;
        this.commentaireEntretien = commentaireEntretien;
        this.isFixe = isFixe;
    }

    public EntretienModel (int vehid, String nom, String typeEntretien, int kmage, String FixeOuNon) {
        this.vehid = vehid;
        this.typeEntretien = typeEntretien;
        this.nomEntretien = nom;
        if (FixeOuNon.equals("FIXE")) {
            this.kmageFixe = kmage;
        } else {
            this.intervalleKmage = kmage;
        }
        //TODO Gérer kmage fixe ou non
    }

    public EntretienModel (int vehid, String nom, String typeEntretien, String commentaire, int kmage, String FixeOuNon) {
        this.vehid = vehid;
        this.nomEntretien = nom;
        this.typeEntretien = typeEntretien;
        this.commentaireEntretien = commentaire;
        if (FixeOuNon.equals("FIXE")) {
            this.kmageFixe = kmage;
        } else {
            this.intervalleKmage = kmage;
        }
        //TODO Gérer kmage fixe ou non
    }

    public EntretienModel (int vehid,String nom, String typeEntretien, String commentaire, String dateEntretien) {
        this.vehid = vehid;
        this.typeEntretien = typeEntretien;
        this.dateEntretien = dateEntretien;
        this.nomEntretien = nom;
        this.commentaireEntretien = commentaire;
    }

    public EntretienModel (int vehid,String nom, String typeEntretien, String dateEntretien) {
        this.vehid = vehid;
        this.typeEntretien = typeEntretien;
        this.dateEntretien = dateEntretien;
        this.nomEntretien = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehid() {
        return vehid;
    }

    public void setVehid(int vehid) {
        this.vehid = vehid;
    }

    public int getIntervalleKmage() {
        return intervalleKmage;
    }

    public void setIntervalleKmage(int intervalleKmage) {
        this.intervalleKmage = intervalleKmage;
    }

    public int getKmageFixe() {
        return kmageFixe;
    }

    public void setKmageFixe(int kmageFixe) {
        this.kmageFixe = kmageFixe;
    }

    public String getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(String dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public String getNomEntretien() {
        return nomEntretien;
    }

    public String getCommentaireEntretien() {
        return commentaireEntretien;
    }

    public void setNomEntretien(String nomEntretien) {
        this.nomEntretien = nomEntretien;
    }

    public void setCommentaireEntretien(String commentaireEntretien) {
        this.commentaireEntretien = commentaireEntretien;
    }

    public String getTypeEntretien() {
        return typeEntretien;
    }

    public void setTypeEntretien(String typeEntretien) {
        this.typeEntretien = typeEntretien;
    }

    public String getIsFixe() {
        return isFixe;
    }

    public void setIsFixe(String isFixe) {
        this.isFixe = isFixe;
    }
}
