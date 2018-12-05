package com.bonobocorp.joker.litrocent.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME= "litrocentDB.db";
    private static final int DATABASE_VERSION = 3;

    private static final String VEHICULE_TABLE_NAME = "vehicule";
    private static final String VEHICULE_KEY = "vehid";
    private static final String VEHICULE_NOM = "vehnom";
    private static final String VEHICULE_TYPE = "vehtype";
    private static final String VEHICULE_KMAGE_TOTAL = "vehkmagetotal";
    private static final String VEHICULE_KMAGE_INITIAL = "vehkmageinitial";
    private static final String VEHICULE_KMAGE_LAST_PLEIN_COMPLET = "vehkmagelastpleincomplet";
    private static final String VEHICULE_DATE_LAST_PLEIN = "vehdatelastpleincomplet";

    private static final String PLEIN_TABLE_NAME= "plein";
    private static final String PLEIN_KEY = "pleinid";
    private static final String PLEIN_VEH_KEY = "vehid";
    private static final String PLEIN_KMAGE_PARCOURU = "kmageparcouru";
    private static final String PLEIN_QTTY_CARB = "qttycarb";
    private static final String PLEIN_PRIX_TOTAL = "prixtotal";
    private static final String PLEIN_TYPE_CARB = "typecarb";
    private static final String PLEIN_CONSOMMATION = "consommation";
    private static final String PLEIN_CONSOMMATION_AJUSTE = "consommationajuste";
    private static final String PLEIN_DATE_PLEIN= "dateplein";
    private static final String PLEIN_COMPLET_BOOLEEN = "boolcomplet";

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

    private static final String VEHICULE_TABLE_CREATE =
            "CREATE TABLE " + VEHICULE_TABLE_NAME + " (" +
                    VEHICULE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VEHICULE_NOM + " TEXT, " +
                    VEHICULE_TYPE+ " TEXT, " +
                    VEHICULE_KMAGE_TOTAL + " INTEGER, " +
                    VEHICULE_KMAGE_INITIAL + " INTEGER, " +
                    VEHICULE_KMAGE_LAST_PLEIN_COMPLET + " INTEGER, " +
                    VEHICULE_DATE_LAST_PLEIN + " TEXT);";

    private static final String PLEIN_TABLE_CREATE =
            "CREATE TABLE " + PLEIN_TABLE_NAME + " (" +
                    PLEIN_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PLEIN_VEH_KEY + " INTEGER, " +
                    PLEIN_KMAGE_PARCOURU + " INTEGER, " +
                    PLEIN_QTTY_CARB + " REAL, " +
                    PLEIN_PRIX_TOTAL + " REAL, " +
                    PLEIN_TYPE_CARB + " TEXT, " +
                    PLEIN_CONSOMMATION + " REAL, " +
                    PLEIN_CONSOMMATION_AJUSTE + " REAL," +
                    PLEIN_DATE_PLEIN + " TEXT," +
                    PLEIN_COMPLET_BOOLEEN + " TEXT," +
                    "FOREIGN KEY("+ PLEIN_VEH_KEY+") REFERENCES "+VEHICULE_TABLE_NAME +"("+ VEHICULE_KEY +"));";

    private static final String ENTRETIEN_TABLE_CREATE =
            "CREATE TABLE " + ENTRETIEN_TABLE_NAME + " (" +
                    ENTRETIEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ENTRETIEN_VEH_KEY+ " INTEGER, " +
                    ENTRETIEN_KMAGE_FIXE + " INTEGER, " +
                    ENTRETIEN_INTERVALLE_KMAGE + " INTEGER, " +
                    ENTRETIEN_DATE + " TEXT, " +
                    ENTRETIEN_COMMENTAIRE + " TEXT," +
                    ENTRETIEN_NOM + " TEXT," +
                    ENTRETIEN_TYPE +" TEXT,"+
                    ENTRETIEN_ISFIXE +" TEXT,"+
                    "FOREIGN KEY("+ PLEIN_VEH_KEY+") REFERENCES "+VEHICULE_TABLE_NAME +"("+ VEHICULE_KEY +"));";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VEHICULE_TABLE_CREATE);
        db.execSQL(PLEIN_TABLE_CREATE);
        db.execSQL(ENTRETIEN_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VEHICULE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLEIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ENTRETIEN_TABLE_NAME);
        onCreate(db);
    }
}

