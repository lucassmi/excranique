package com.bonobocorp.joker.litrocent.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bonobocorp.joker.litrocent.Model.VehiculeModel;

import java.util.ArrayList;

public class VehiculeDAO extends com.bonobocorp.joker.litrocent.Controler.DAOBase {

    private static final String TABLE_VEHICULE = "vehicule";
    private static final String TABLE_PLEIN = "plein";
    private static final String KEY = "vehid";
    private static final String NOM = "vehnom";
    private static final String TYPE = "vehtype";
    private static final String KMAGE_TOTAL = "vehkmagetotal";
    private static final String KMAGE_INITIAL = "vehkmageinitial";
    private static final String KMAGE_LAST_PLEIN_COMPLET = "vehkmagelastpleincomplet";
    private static final String DATE_LAST_PLEIN = "vehdatelastpleincomplet";


    public VehiculeDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(VehiculeModel vehicule) {
        open();
        ContentValues values = new ContentValues();
        values.putNull(KEY);
        values.put(NOM, vehicule.getVehiculeNom());
        values.put(TYPE, vehicule.getVehiculeType());
        values.put(KMAGE_TOTAL, vehicule.getKmageInitial());
        values.put(KMAGE_INITIAL, vehicule.getKmageInitial());
        values.put(KMAGE_LAST_PLEIN_COMPLET, 0);
        values.put(DATE_LAST_PLEIN, 0);
        mDb.insert(TABLE_VEHICULE, null, values);
        close();
    }

    public void supprimer (int id) {
        open();
        String idStr = String.valueOf(id);
        mDb.delete(TABLE_VEHICULE, KEY +" =?", new String[]{idStr});
        mDb.delete(TABLE_PLEIN, KEY +" =?", new String[]{idStr});
        close();
    }

    public ArrayList<VehiculeModel> getVehicule () {
        open();
        ArrayList<VehiculeModel> result = new ArrayList<>();
        String query = "SELECT "+ NOM +", " + TYPE +", " + KEY+" FROM " + TABLE_VEHICULE +";";
        Cursor cursor = mDb.rawQuery(query, null);
        VehiculeModel veh;
        while (cursor.moveToNext()) {
            String nom = cursor.getString(0);
            String type = cursor.getString(1);
            int id = cursor.getInt(2);
            veh = new VehiculeModel(id, nom, type);
            result.add(veh);
        }
        cursor.close();
        close();
        return result;
    }

    public void updateVehicule (int id, String nom, String type) {
        open();
        ContentValues values = new ContentValues();
        values.put(NOM, nom);
        values.put(TYPE, type);
        mDb.update(TABLE_VEHICULE, values, KEY + " = ?",
                new String[] {String.valueOf(id)});
        close();

    }

    public void updateVehiculeKmageTotal (int id, int kmageTotal) {
        open();
        ContentValues values = new ContentValues();
        values.put(KMAGE_TOTAL, kmageTotal);
        mDb.update(TABLE_VEHICULE, values, KEY + " = ?",
                new String[] {String.valueOf(id)});
        close();

    }

    public void updateVehiculeKmageLastPleinComplet (int id, int kmageLastPleinComplet) {
        open();
        ContentValues values = new ContentValues();
        values.put(KMAGE_LAST_PLEIN_COMPLET, kmageLastPleinComplet);
        mDb.update(TABLE_VEHICULE, values, KEY + " = ?",
                new String[] {String.valueOf(id)});
        close();
    }

    public void updateVehiculeDateLastPleinComplet (int id, String dateLastPleinComplet) {
        open();
        ContentValues values = new ContentValues();
        values.put(DATE_LAST_PLEIN, dateLastPleinComplet);
        mDb.update(TABLE_VEHICULE, values, KEY + " = ?",
                new String[] {String.valueOf(id)});
        close();
    }


    public VehiculeModel selectVehicule (int id) {
        open();
        VehiculeModel result;
        String query = "SELECT "+ NOM +", " + TYPE +", " + KMAGE_TOTAL +", " + KMAGE_INITIAL +", " + KMAGE_LAST_PLEIN_COMPLET +", " + DATE_LAST_PLEIN +" FROM " + TABLE_VEHICULE +" WHERE "+ KEY + " = "+ id +";";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        String nom = cursor.getString(0);
        String type = cursor.getString(1);
        int kmageTotal = Integer.parseInt(cursor.getString(2));
        int kmageInitial = Integer.parseInt(cursor.getString(3));
        int kmageLastPleinComplet = Integer.parseInt(cursor.getString(4));
        String dateLastPleinComplet = cursor.getString(5);
        result = new VehiculeModel(nom, type, kmageTotal, kmageInitial, kmageLastPleinComplet, dateLastPleinComplet);
        cursor.close();
        close();
        return result;
    }

    public VehiculeModel selectLastVehicule () {
        open();
        VehiculeModel result;
        String maxId = "SELECT MAX("+ KEY +") FROM" + TABLE_VEHICULE +";";
        Cursor cursorid = mDb.rawQuery(maxId, null);
        cursorid.moveToFirst();
        int id = cursorid.getInt(0);
        String query = "SELECT "+ NOM +", " + TYPE +", " + KMAGE_TOTAL +", " + KMAGE_INITIAL +", " + KMAGE_LAST_PLEIN_COMPLET +", " + DATE_LAST_PLEIN +" FROM " + TABLE_VEHICULE +" WHERE "+ KEY + " = "+ id +";";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        String nom = cursor.getString(0);
        String type = cursor.getString(1);
        int kmageTotal = Integer.parseInt(cursor.getString(2));
        int kmageInitial = Integer.parseInt(cursor.getString(3));
        int kmageLastPleinComplet = Integer.parseInt(cursor.getString(4));
        String dateLastPleinComplet = cursor.getString(5);
        result = new VehiculeModel(nom, type, kmageTotal, kmageInitial, kmageLastPleinComplet, dateLastPleinComplet);
        cursor.close();
        close();
        return result;
    }

    public String getKmage(int id) {
        open();
        String query = "SELECT "+ KMAGE_TOTAL +" FROM " + TABLE_VEHICULE +" WHERE "+ KEY + " = "+ id +";";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        String kmage = cursor.getString(0);
        close();
        return kmage;
    }

}
