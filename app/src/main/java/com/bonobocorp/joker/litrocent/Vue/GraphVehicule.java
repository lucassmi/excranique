package com.bonobocorp.joker.litrocent.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bonobocorp.joker.litrocent.Controler.PleinDAO;
import com.bonobocorp.joker.litrocent.Model.PleinModel;
import com.bonobocorp.joker.litrocent.R;
import com.bonobocorp.joker.litrocent.Controler.VehiculeDAO;
import com.bonobocorp.joker.litrocent.Model.VehiculeModel;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphVehicule extends AppCompatActivity {

    PleinDAO pdao = new PleinDAO(this);
    VehiculeDAO vdao = new VehiculeDAO(this);
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> series2;
    LineGraphSeries<DataPoint> seriesPrix;
    LineGraphSeries<DataPoint> seriesKmage;
    NumberFormat nf = new DecimalFormat("#0");
    int displayConso = 0;
    int displayConso2 = 0;
    int displayPrix = 0;
    int displayKmage = 0;
    int xDipslay = 0;
    String vehIDString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        final String vehiculeID = getIntent().getStringExtra("idVehicule");
        int vehID = Integer.parseInt(vehiculeID);
        vehIDString = vehiculeID;

        ArrayList<PleinModel> listPleins;
        VehiculeModel veh = vdao.selectVehicule(vehID);
        int vehKmageEvolutif = veh.getKmageInitial();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

        listPleins = pdao.getPleinsByIdVeh(vehID, " ASC ");

        DataPoint[] dp = new DataPoint[listPleins.size()];
        DataPoint[] dp2 = new DataPoint[listPleins.size()];
        DataPoint[] dpPrix = new DataPoint[listPleins.size()];
        DataPoint[] dpKmageEvolutif = new DataPoint[listPleins.size()];
        int count = 0;

        for (PleinModel plein : listPleins) {
            float conso = plein.getConsommation();
            float conso2 = plein.getConsommationajuste();
            float prix = plein.getPrixTotal();
            vehKmageEvolutif += plein.getKmageParcouru();
            String sDate1 = plein.getDate();
            Date date1 = null;
            try {
                date1 = formatter1.parse(sDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dp[count] = new DataPoint(date1, conso);
            dp2[count] = new DataPoint(date1, conso2);
            dpPrix[count] = new DataPoint(date1, prix);
            dpKmageEvolutif[count] = new DataPoint(date1, vehKmageEvolutif);

            count++;
        }

        TextView noPlein = findViewById(R.id.pasDePlein);

        graph = findViewById(R.id.graph);
        series = new LineGraphSeries<>(dp);
        series2 = new LineGraphSeries<>(dp2);
        seriesPrix = new LineGraphSeries<>(dpPrix);
        seriesKmage = new LineGraphSeries<>(dpKmageEvolutif);

        graph.addSeries(series);
        graph.addSeries(series2);

        series.setTitle(getString(R.string.conso));
        series2.setTitle(getString(R.string.consoMoyenne));
        seriesPrix.setTitle(getString(R.string.prixPlein));
        seriesKmage.setTitle(getString(R.string.kilometrage));

        series.setColor(Color.RED);
        series2.setColor(Color.argb(255, 0,166,0));
        seriesPrix.setColor(Color.argb(255, 0,0,166));
        seriesKmage.setColor(Color.argb(255, 150,150,150));

        series.setDrawDataPoints(true);
        series2.setDrawDataPoints(true);
        seriesPrix.setDrawDataPoints(true);
        seriesKmage.setDrawDataPoints(true);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf,nf));


        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));

        if (listPleins.size() == 0 || listPleins.size() == 1) {
            graph.setVisibility(View.GONE);
            noPlein.setVisibility(View.VISIBLE);
        }else if (listPleins.size() == 2){
            graph.setVisibility(View.VISIBLE);
            noPlein.setVisibility(View.GONE);
            xDipslay = 2;
        }else if (listPleins.size() == 3){
            graph.setVisibility(View.VISIBLE);
            noPlein.setVisibility(View.GONE);
            xDipslay = 3;
        }else {
            graph.setVisibility(View.VISIBLE);
            noPlein.setVisibility(View.GONE);
            xDipslay = 4;
        }

        graph.getGridLabelRenderer().setNumHorizontalLabels(xDipslay);

        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(true);
        final NumberFormat nfDp = new DecimalFormat("#0.##");
        final NumberFormat nfDpKm = new DecimalFormat("#######0");
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), getString(R.string.consoSurClique) + nfDp.format(dataPoint.getY()) + getString(R.string.L100), Toast.LENGTH_SHORT).show();
            }
        });

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), getString(R.string.consoMoyenneSurClique) + nfDp.format(dataPoint.getY()) + getString(R.string.L100), Toast.LENGTH_SHORT).show();
            }
        });

        seriesPrix.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), getString(R.string.prixSurClique) + nfDp.format(dataPoint.getY()) + getString(R.string.monnaie), Toast.LENGTH_SHORT).show();
            }
        });

        seriesKmage.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), getString(R.string.kmSurClique) + nfDpKm.format(dataPoint.getY()) + getString(R.string.km), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideDisplayConso(View v) {

        if ( (displayConso % 2 ) == 0 ) {
            graph.removeSeries(series);
            displayConso++;
        } else {
            graph.addSeries(series);
            displayConso++;
        }
    }

    public void hideDisplayConso2(View v) {

        if ( (displayConso2 % 2 ) == 0 ) {
            graph.removeSeries(series2);
            displayConso2++;
        } else {
            graph.addSeries(series2);
            displayConso2++;
        }
    }

    public void hideDisplayPrix(View v) {

        if ( (displayPrix % 2 ) == 0 ) {
            graph.addSeries(seriesPrix);
            displayPrix++;
        } else {
            graph.removeSeries(seriesPrix);
            displayPrix++;
        }
    }

    public void hideDisplayKilometrage(View v) {

        if ( (displayKmage % 2 ) == 0 ) {
            graph.addSeries(seriesKmage);
            displayKmage++;
        } else {
            graph.removeSeries(seriesKmage);
            displayKmage++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem graph = menu.findItem(R.id.graphDuVehicule);
        graph.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent pageAccueil = new Intent(GraphVehicule.this, PageAccueil.class);
                startActivity(pageAccueil);
                return true;
            case R.id.info:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.needhelp)
                        .setMessage(R.string.graphHelpContenu)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
                return true;
            case R.id.pleinDuVehicule:
                Intent mesPleins = new Intent(GraphVehicule.this, PleinsMainPage.class);
                mesPleins.putExtra("idVehicule", vehIDString);
                startActivity(mesPleins);
                return true;

            case R.id.modifVehicule:
                Intent modif = new Intent(GraphVehicule.this, CreateVehicule.class);
                modif.putExtra("idVehicule", vehIDString);
                startActivity(modif);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
