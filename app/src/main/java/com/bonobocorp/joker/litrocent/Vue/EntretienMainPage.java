package com.bonobocorp.joker.litrocent.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bonobocorp.joker.litrocent.Controler.EntretienDAO;
import com.bonobocorp.joker.litrocent.Model.EntretienModel;
import com.bonobocorp.joker.litrocent.R;

import java.util.ArrayList;

public class EntretienMainPage extends AppCompatActivity {

    String vehIDString;
    int vehID;
    EntretienDAO edao = new EntretienDAO(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_entretiens);
        FloatingActionButton btnCreateEntretien =findViewById(R.id.floatingAddEntretien);
        TextView pasDEntretien =findViewById(R.id.pasDEntretiens);
        ListView listViewEntretiens = findViewById(R.id.listViewEntretiens);

        final String vehiculeID = getIntent().getStringExtra("idVehicule");
        vehID = Integer.parseInt(vehiculeID);
        vehIDString = vehiculeID;
        ArrayList<EntretienModel> listEntretien = edao.getEntretienByIdVeh(vehID);

        if  (listEntretien.isEmpty()) {
            listViewEntretiens.setVisibility(View.GONE);
            pasDEntretien.setVisibility(View.VISIBLE);
        } else {
            listViewEntretiens.setVisibility(View.VISIBLE);
            pasDEntretien.setVisibility(View.GONE);
        }

        //Redirection Boutton création vehicule
        btnCreateEntretien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createVehicule = new Intent(EntretienMainPage.this, CreateEntretien.class);
                startActivity(createVehicule);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent pageAccueil = new Intent(EntretienMainPage.this, PageAccueil.class);
                startActivity(pageAccueil);
                return true;
                //TODO modifier les infos du menu
            case R.id.info:
                //Creation du dialogue Aide
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.needhelp)
                        .setMessage(R.string.pleinHelpContenu)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
                return true;

            case R.id.pleinDuVehicule:
                Intent mesPleins = new Intent(EntretienMainPage.this, PleinsMainPage.class);
                mesPleins.putExtra("idVehicule", vehIDString);
                startActivity(mesPleins);
                return true;

            case R.id.graphDuVehicule:
                Intent graphVeh = new Intent(EntretienMainPage.this, GraphVehicule.class);
                graphVeh.putExtra("idVehicule", vehIDString);
                startActivity(graphVeh);
                return true;

            case R.id.modifVehicule:
                Intent modif = new Intent(EntretienMainPage.this, CreateVehicule.class);
                modif.putExtra("idVehicule", vehIDString);
                startActivity(modif);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
