package com.bonobocorp.joker.litrocent.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bonobocorp.joker.litrocent.Controler.PleinDAO;
import com.bonobocorp.joker.litrocent.Model.PleinModel;
import com.bonobocorp.joker.litrocent.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PleinsMainPage extends AppCompatActivity {

    private TableLayout tableLayout;
    PleinDAO pdao = new PleinDAO(this);
    ArrayList<PleinModel> listPleins;
    String vehIDString;
    int vehID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mes_pleins);


        final String vehiculeID = getIntent().getStringExtra("idVehicule");
        vehID = Integer.parseInt(vehiculeID);
        vehIDString = vehiculeID;

        TextView pasDePlein = findViewById(R.id.pasDePlein);
        tableLayout = findViewById(R.id.tableLayout);
        FloatingActionButton btnCreatePlein = findViewById(R.id.floatingAddPlein);

        TextView pleinTitreDate = findViewById(R.id.tvTitreDatePlein);
        TextView pleinTitreKmParcouru = findViewById(R.id.tvTitreDist);
        TextView pleinTitreQttyCarb=  findViewById(R.id.tvTitreLitre);
        TextView pleinTitreConso = findViewById(R.id.tvTitreConso);
        TextView pleinTitreConso2 = findViewById(R.id.tvTitreConso2);

        pleinTitreDate.setText(R.string.pleinDateAfficher);
        pleinTitreKmParcouru.setText(R.string.kmParcouru);
        pleinTitreQttyCarb.setText(R.string.qttycarb);
        pleinTitreConso.setText(R.string.conso);
        pleinTitreConso2.setText(R.string.consoDepuisDernierPlein);

        listPleins = pdao.getPleinsByIdVeh(vehID, " DESC ");
        NumberFormat nf = new DecimalFormat("##0.##");


        for (PleinModel plein : listPleins){

            String spleinQttyCarb = nf.format(plein.getQttyCarb());
            String spleinConso = nf.format(plein.getConsommation());
            String spleinConso2 = nf.format(plein.getConsommationajuste());
            String id = String.valueOf(plein.getId());

            String tabDate[] = plein.getDate().split("-");
            String spleinDate = tabDate[2] + "/" + tabDate[1] + "/" + tabDate[0];
            String kmParcouru = String.valueOf(plein.getKmageParcouru()) + getString(R.string.km);
            String QttyCarb = spleinQttyCarb + getString(R.string.L);
            String conso = spleinConso + getString(R.string.L100);
            String conso2 = spleinConso2+ getString(R.string.L100);



            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item_plein,null,false);
            TextView pleinDate = tableRow.findViewById(R.id.tvDatePlein);
            TextView pleinKmParcouru = tableRow.findViewById(R.id.tvkmageParc);
            TextView pleinQttyCarb=  tableRow.findViewById(R.id.tvqttyPlein);
            TextView pleinConso = tableRow.findViewById(R.id.tvConso);
            TextView pleinConso2 = tableRow.findViewById(R.id.tvConso2);
            TextView tvid = tableRow.findViewById(R.id.idPlein);

            pleinDate.setText(spleinDate);
            pleinKmParcouru.setText(kmParcouru);
            pleinQttyCarb.setText(QttyCarb);
            if (spleinConso.equals("0")) {
                pleinConso.setText("-");
            } else {
                pleinConso.setText(conso);
            }

            if (spleinConso2.equals("0")) {
                pleinConso2.setText("-");
            }else {
                pleinConso2.setText(conso2);
            }
            if (plein.getPleinComplet().equals("true")) {
                pleinDate.setBackgroundColor(getColor(R.color.backgroundTableisComplet));
                pleinKmParcouru.setBackgroundColor(getColor(R.color.backgroundTableisComplet));
                pleinQttyCarb.setBackgroundColor(getColor(R.color.backgroundTableisComplet));
                pleinConso.setBackgroundColor(getColor(R.color.backgroundTableisComplet));
                pleinConso2.setBackgroundColor(getColor(R.color.backgroundTableisComplet));

            }

            tvid.setText(id);

            tableLayout.addView(tableRow);
        }

        if (listPleins.isEmpty()) {
            pasDePlein.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.GONE);
        } else {
            pasDePlein.setVisibility(View.GONE);
            tableLayout.setVisibility(View.VISIBLE);
        }



        //Redirection Boutton création plein
        btnCreatePlein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createPlein = new Intent(PleinsMainPage.this, CreatePlein.class);
                createPlein.putExtra("idVehicule", vehiculeID);
                startActivity(createPlein);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem plein = menu.findItem(R.id.pleinDuVehicule);
        plein.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent pageAccueil = new Intent(PleinsMainPage.this, PageAccueil.class);
                startActivity(pageAccueil);
                return true;
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
            case R.id.graphDuVehicule:
                Intent graphVeh = new Intent(PleinsMainPage.this, GraphVehicule.class);
                graphVeh.putExtra("idVehicule", vehIDString);
                startActivity(graphVeh);
                return true;

            case R.id.modifVehicule:
                Intent modif = new Intent(PleinsMainPage.this, CreateVehicule.class);
                modif.putExtra("idVehicule", vehIDString);
                startActivity(modif);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void suppPlein(View v) {
        TableRow tr = (TableRow) v;
        TextView date, id;
        date = (TextView) tr.getChildAt(0);
        id = (TextView) tr.getChildAt(5);
        final String svalueId = id.getText().toString();
        final int valueId = Integer.parseInt(svalueId);
        String datePlein = date.getText().toString() + " ?";
        String secureMessage = getResources().getString(R.string.textinfomodif) + datePlein;

        boolean isLastPlein = pdao.isLastPlein(valueId, vehID);
        //TODO
        // implémentation possibilité de modifier SI c'est le dernier plein (variable au dessus)
        if (isLastPlein) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.secureModifierPlein)
                    .setMessage(secureMessage)
                    .setPositiveButton(R.string.modifier, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent modif = new Intent(PleinsMainPage.this, CreatePlein.class);
                            Bundle extras = new Bundle();
                            extras.putString("idVehicule",vehIDString);
                            extras.putString("idPlein", svalueId);
                            modif.putExtras(extras);
                            startActivity(modif);
                        }
                    })
                    .setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.infosupp)
                    .setMessage(R.string.textinfoLastPlein)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

}
