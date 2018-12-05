package com.bonobocorp.joker.litrocent.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bonobocorp.joker.litrocent.Model.PleinModel;

import java.util.ArrayList;

public class PleinDAO extends com.bonobocorp.joker.litrocent.Controler.DAOBase {

    private static final String TABLE_PLEIN = "plein";
    private static final String KEY = "pleinid";
    private static final String VEH_KEY = "vehid";
    private static final String KMAGE_PARCOURU = "kmageparcouru";
    private static final String QTTY_CARB = "qttycarb";
    private static final String PRIX_TOTAL = "prixtotal";
    private static final String TYPE_CARB = "typecarb";
    private static final String CONSOMMATION = "consommation";
    private static final String CONSOMMATION_AJUSTE = "consommationajuste";
    private static final String DATE_PLEIN= "dateplein";
    private static final String PLEIN_COMPLET_BOOLEEN = "boolcomplet";

    public PleinDAO (Context context) {
        super(context);
    }

    public void ajouter(PleinModel plein, int idVehicule) {
        open();

        ContentValues values = new ContentValues();
        values.putNull(KEY);
        values.put(VEH_KEY, idVehicule);
        values.put(KMAGE_PARCOURU, plein.getKmageParcouru());
        values.put(QTTY_CARB, plein.getQttyCarb());
        values.put(PRIX_TOTAL, plein.getPrixTotal());
        values.put(TYPE_CARB, plein.getTypeCarb());
        values.put(CONSOMMATION, plein.getConsommation());
        values.put(CONSOMMATION_AJUSTE, plein.getConsommationajuste());
        values.put(DATE_PLEIN, plein.getDate());
        values.put(PLEIN_COMPLET_BOOLEEN, plein.getPleinComplet());
        mDb.insert(TABLE_PLEIN, null, values);
        close();
    }

    public void modifier (PleinModel plein, int idPlein) {
        open();

        ContentValues values = new ContentValues();
        values.put(KMAGE_PARCOURU, plein.getKmageParcouru());
        values.put(QTTY_CARB, plein.getQttyCarb());
        values.put(PRIX_TOTAL, plein.getPrixTotal());
        values.put(TYPE_CARB, plein.getTypeCarb());
        values.put(CONSOMMATION, plein.getConsommation());
        values.put(CONSOMMATION_AJUSTE, plein.getConsommationajuste());
        values.put(DATE_PLEIN, plein.getDate());
        values.put(PLEIN_COMPLET_BOOLEEN, plein.getPleinComplet());
        mDb.update(TABLE_PLEIN, values, KEY + " = ?",
                new String[] {String.valueOf(idPlein)});
        close();
    }

    public boolean isLastPlein(int idPlein, int idVeh) {
        open();
        String idStr = String.valueOf(idVeh);
        String query = "SELECT MAX(" + KEY + ") FROM "+  TABLE_PLEIN + " WHERE " + VEH_KEY + "=" + idStr +" ;";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        int id = Integer.parseInt(cursor.getString(0));
        if (id == idPlein) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<PleinModel> getPleinsAfterDate(String date, int idveh) {
        //date en paramètre est la date du dernier plein complet du véhicule
        open();
        ArrayList<PleinModel> result = new ArrayList<>();
        String query = "SELECT " + KMAGE_PARCOURU +", "+
                QTTY_CARB +", "+
                PRIX_TOTAL +", "+
                TYPE_CARB + ", "+
                CONSOMMATION +", "+
                CONSOMMATION_AJUSTE +", " +
                DATE_PLEIN +", " +
                PLEIN_COMPLET_BOOLEEN+", " +
                KEY +
                " FROM " + TABLE_PLEIN +
                " WHERE "+ VEH_KEY + " = " + idveh +
                " AND "+ DATE_PLEIN + " > '" + date +"' ;";

        Cursor cursor = mDb.rawQuery(query, null);
        PleinModel plein;
        while (cursor.moveToNext()) {
            int kmageParcouru = Integer.parseInt(cursor.getString(0));
            float qttyCarb = Float.parseFloat(cursor.getString(1));
            float prixTotal = Float.parseFloat(cursor.getString(2));
            String typeCarb = cursor.getString(3);
            float conso = Float.parseFloat(cursor.getString(4));
            float conso2 = Float.parseFloat(cursor.getString(5));
            String datePlein = cursor.getString(6);
            String pleinCompletBool = cursor.getString(7);

            plein = new PleinModel(kmageParcouru, qttyCarb, prixTotal,
                    typeCarb, conso, conso2, datePlein, pleinCompletBool);
            result.add(plein);
        }
        cursor.close();
        close();
        return result;

    }


    public ArrayList<PleinModel> getPleinsByIdVeh (int idVehicule, String order) {
        open();
        ArrayList<PleinModel> result = new ArrayList<>();
        String query = "SELECT " + KMAGE_PARCOURU +", "+
                QTTY_CARB +", "+
                PRIX_TOTAL +", "+
                TYPE_CARB + ", "+
                CONSOMMATION +", "+
                CONSOMMATION_AJUSTE +", " +
                DATE_PLEIN +", " +
                PLEIN_COMPLET_BOOLEEN+", " +
                KEY +
                " FROM " + TABLE_PLEIN +
                " WHERE "+ VEH_KEY + " = " + idVehicule +
                " ORDER BY " + DATE_PLEIN + order +";";
        Cursor cursor = mDb.rawQuery(query, null);
        PleinModel plein;
        while (cursor.moveToNext()) {
            int kmageParcouru = Integer.parseInt(cursor.getString(0));
            float qttyCarb = Float.parseFloat(cursor.getString(1));
            float prixTotal = Float.parseFloat(cursor.getString(2));
            String typeCarb = cursor.getString(3);
            float conso = Float.parseFloat(cursor.getString(4));
            float conso2 = 0;
            if (cursor.isNull(5) || cursor.getString(5).equals("inf")) {
                conso2 = 0;
            //} else if (cursor.getString(5).equals("inf")) {
                //TODO
                //Temporaire
              //  conso2 = 0;
            } else {
                    conso2 = Float.parseFloat(cursor.getString(5));

            }
            String datePlein = cursor.getString(6);
            String pleinCompletBool = cursor.getString(7);
            int id = Integer.parseInt(cursor.getString(8));

            plein = new PleinModel(kmageParcouru, qttyCarb, prixTotal, typeCarb, conso, conso2, datePlein, pleinCompletBool, id);
            result.add(plein);
        }
        cursor.close();
        close();
        return result;
    }

    public PleinModel getPleinByIdVehPlein (int idVehicule, int idPlein) {
        open();
        String query = "SELECT " + KMAGE_PARCOURU +", "+
                QTTY_CARB +", "+
                PRIX_TOTAL +", "+
                TYPE_CARB + ", "+
                CONSOMMATION +", "+
                CONSOMMATION_AJUSTE +", " +
                DATE_PLEIN +", " +
                PLEIN_COMPLET_BOOLEEN+
                " FROM " + TABLE_PLEIN +
                " WHERE "+ VEH_KEY + " = " + idVehicule +
                " AND " + KEY + " = " + idPlein+ ";";

        Cursor cursor = mDb.rawQuery(query, null);
        PleinModel plein;
        cursor.moveToFirst();

        int kmageParcouru = Integer.parseInt(cursor.getString(0));
        float qttyCarb = Float.parseFloat(cursor.getString(1));
        float prixTotal = Float.parseFloat(cursor.getString(2));
        String typeCarb = cursor.getString(3);
        float conso = Float.parseFloat(cursor.getString(4));
        float conso2 = 0;
        if (cursor.isNull(5) || cursor.getString(5).equals("inf")) {
            conso2 = 0;
        } else {
            conso2 = Float.parseFloat(cursor.getString(5));
        }
        String datePlein = cursor.getString(6);
        String pleinCompletBool = cursor.getString(7);

        plein = new PleinModel(kmageParcouru, qttyCarb, prixTotal, typeCarb, conso, conso2, datePlein, pleinCompletBool);
        cursor.close();
        close();
        return plein;
    }

}

