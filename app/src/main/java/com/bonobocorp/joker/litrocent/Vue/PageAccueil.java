package com.bonobocorp.joker.litrocent.Vue;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.drawable.Drawable;
    import android.support.design.widget.FloatingActionButton;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ExpandableListAdapter;
    import android.widget.ExpandableListView;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bonobocorp.joker.litrocent.R;
    import com.bonobocorp.joker.litrocent.Controler.VehiculeDAO;
    import com.bonobocorp.joker.litrocent.Model.VehiculeModel;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

public class PageAccueil extends AppCompatActivity {

    //instanciation des vues
    ArrayList<VehiculeModel> liste = new ArrayList<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<ChampText>> expandableListDetail;

    VehiculeDAO vdao = new VehiculeDAO(this);
    ExpandableListDataDump dataDump = new ExpandableListDataDump(this);

    //Methode pour gérer les clicks sur les items de la textview
    public void onClickTextView (View itemViewChampText) {
        String choixText = ((ChampText) itemViewChampText).getText().toString();
        String stringidveh = ((ChampText) itemViewChampText).getIdveh();
        final int idveh = Integer.parseInt(stringidveh);

        if (choixText.equals(getString(R.string.expSupprimer))) {

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.secureTitreSupprimerVeh)
                    .setMessage(R.string.secureSupprimerVeh)
                    .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            vdao.supprimer(idveh);
                            Toast.makeText(getApplicationContext(), R.string.toastSupprimerVeh, Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                    })
                    .setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else if (choixText.equals(getString(R.string.expMesPleins))) {
            Intent mesPleins = new Intent(PageAccueil.this, PleinsMainPage.class);
            mesPleins.putExtra("idVehicule", stringidveh);
            startActivity(mesPleins);

        } else if (choixText.equals(getString(R.string.expStats))) {
            Intent stats = new Intent(PageAccueil.this, GraphVehicule.class);
            stats.putExtra("idVehicule", stringidveh);
            startActivity(stats);

        } else if (choixText.equals(getString(R.string.modifVehicule))) {
            Intent stats = new Intent(PageAccueil.this, CreateVehicule.class);
            stats.putExtra("idVehicule", stringidveh);
            startActivity(stats);
        } else if (choixText.equals(getString(R.string.expEntretiens))) {
            Intent stats = new Intent(PageAccueil.this, EntretienMainPage.class);
            stats.putExtra("idVehicule", stringidveh);
            startActivity(stats);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        ImageView img = findViewById(R.id.imageview);
        //Drawable litrocent = getResources().getDrawable(R.drawable.litrocentwhite);
        Drawable litrocent = ContextCompat.getDrawable(getBaseContext(), R.drawable.litrocent);
        img.setImageDrawable(litrocent);

        //Attribution des vues instanciés plus haut
        FloatingActionButton btnCreateVehicule = findViewById(R.id.floatingAddVehicule);
        TextView pasDeVehicule = findViewById(R.id.pasDeVehicule);
        expandableListView = findViewById(R.id.expandableListView);

        //récupération des infos pour alimenter la liste et expandableList
        liste = vdao.getVehicule();

        expandableListDetail = dataDump.getData(liste);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        //Détermine l'affichage de la vue sur la page d'accueil, si pas de véhicule, afficher le text
        if (liste.isEmpty()) {
            pasDeVehicule.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        } else {
            pasDeVehicule.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);
        }

        //Redirection Boutton création vehicule
        btnCreateVehicule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createVehicule = new Intent(PageAccueil.this, CreateVehicule.class);

                startActivity(createVehicule);
            }
        });

    }
}
