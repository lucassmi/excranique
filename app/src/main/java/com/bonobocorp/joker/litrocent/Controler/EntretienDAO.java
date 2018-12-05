package com.bonobocorp.joker.litrocent.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.bonobocorp.joker.litrocent.Model.EntretienModel;
import com.bonobocorp.joker.litrocent.Model.PleinModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class  EntretienDAO extends com.bonobocorp.joker.litrocent.Controler.DAOBase {

    private static final String ENTRETIEN_TABLE_NAME= "entretien";
    private static final String ENTRETIEN_ID = "entretienid";
    private static final String ENTRETIEN_VEH_KEY = "vehid";
    private static final String ENTRETIEN_KMAGE_FIXE = "kmagefixe";
    private static final String ENTRETIEN_INTERVALLE_KMAGE = "intervallekmage";
    private static final String ENTRETIEN_DATE = "dateentretien";
    private static final String ENTRETIEN_NOM = "nomentretien";
    private static final String ENTRETIEN_COMMENTAIRE = "commentaireentretien";
    private static final String ENTRETIEN_TYPE = "typeentretien";
    private static final String ENTRETIEN_ISFIXE = "isfixe";

    DateFormat df = new SimpleDateFormat("yyyy-MM-yy");

    public EntretienDAO (Context context) {
        super(context);
    }

    public void insertEntretien (EntretienModel entretien, int idVehicule) {
        open();
        int kmageFixe = entretien.getKmageFixe();
        int intervalleKmage = entretien.getIntervalleKmage();
        String date = df.format(entretien.getDateEntretien());
        String commentaire = entretien.getCommentaireEntretien();
        String nom = entretien.getNomEntretien();
        String type = entretien.getTypeEntretien();
        String isFixe = entretien.getIsFixe();

        ContentValues values = new ContentValues();
        values.putNull(ENTRETIEN_ID);
        values.put(ENTRETIEN_VEH_KEY, idVehicule);
        values.put(ENTRETIEN_KMAGE_FIXE, kmageFixe);
        values.put(ENTRETIEN_INTERVALLE_KMAGE, intervalleKmage);
        values.put(ENTRETIEN_DATE, date);
        values.put(ENTRETIEN_NOM, nom);
        values.put(ENTRETIEN_COMMENTAIRE, commentaire);
        values.put(ENTRETIEN_TYPE, type);
        values.put(ENTRETIEN_ISFIXE, isFixe);
        mDb.insert(ENTRETIEN_TABLE_NAME, null, values);
        close();
    }

    public void modifierEntretien (EntretienModel entretien, int idEntretien) {
        open();
        int kmageFixe = entretien.getKmageFixe();
        int intervalleKmage = entretien.getIntervalleKmage();
        String date = df.format(entretien.getDateEntretien());
        String commentaire = entretien.getCommentaireEntretien();
        String nom = entretien.getNomEntretien();
        String type = entretien.getTypeEntretien();

        ContentValues values = new ContentValues();
        values.put(ENTRETIEN_KMAGE_FIXE, kmageFixe);
        values.put(ENTRETIEN_INTERVALLE_KMAGE, intervalleKmage);
        values.put(ENTRETIEN_DATE, date);
        values.put(ENTRETIEN_NOM, nom);
        values.put(ENTRETIEN_COMMENTAIRE, commentaire);
        values.put(ENTRETIEN_TYPE, type);
        mDb.update(ENTRETIEN_TABLE_NAME, values, ENTRETIEN_ID + " = ?",
                new String[] {String.valueOf(idEntretien)});
        close();
    }

    public ArrayList<EntretienModel> getEntretienByIdVeh (int idVehicule) {
        open();
        ArrayList<EntretienModel> result = new ArrayList<>();
        String query = "SELECT " + ENTRETIEN_NOM +", "+
                ENTRETIEN_COMMENTAIRE +", "+
                ENTRETIEN_DATE +", "+
                ENTRETIEN_INTERVALLE_KMAGE + ", "+
                ENTRETIEN_KMAGE_FIXE + ", "+
                ENTRETIEN_TYPE + ", "+
                ENTRETIEN_ISFIXE +
                " FROM " + ENTRETIEN_TABLE_NAME +
                " WHERE "+ ENTRETIEN_VEH_KEY + " = " + idVehicule +";";
        Cursor cursor = mDb.rawQuery(query, null);
        EntretienModel entretien;
        while (cursor.moveToNext()) {
            String nom = cursor.getString(0);
            String type = cursor.getString(5);
            String isFixe = cursor.getString(6);
            String commentaire;
            String date = cursor.getString(2);
            int kmageIntervalle = Integer.parseInt(cursor.getString(3));
            int kmageFixe = Integer.parseInt(cursor.getString(4));

            if (cursor.isNull(1) || cursor.getString(1).equals("")) {
                commentaire = "";
            } else {
                commentaire = cursor.getString(1);
            }

            if (cursor.isNull(2) || cursor.getString(2).equals("")) {
                date = "";
            } else {
                date = cursor.getString(2);
            }

            entretien = new EntretienModel(nom, type, kmageIntervalle, kmageFixe, date, commentaire, isFixe);
            result.add(entretien);
        }
        cursor.close();
        close();
        return result;
    }

    public void supprimerEntretien (int id) {
        open();
        String idStr = String.valueOf(id);
        mDb.delete(ENTRETIEN_TABLE_NAME, ENTRETIEN_ID +" =?", new String[]{idStr});
        close();
    }
}
