package com.bonobocorp.joker.litrocent.Vue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bonobocorp.joker.litrocent.R;
import com.bonobocorp.joker.litrocent.Controler.VehiculeDAO;
import com.bonobocorp.joker.litrocent.Model.VehiculeModel;

public class CreateVehicule extends AppCompatActivity{

    Spinner typeVehicule = null;
    EditText nomVehicule = null;
    EditText kmageInitial = null;
    TextView tvkmageInitial = null;
    VehiculeDAO vdao = new VehiculeDAO(this);
    String vehIDString;
    int vehID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_vehicule);

        // Hide Keyboard onCreate
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        typeVehicule =  findViewById(R.id.spinnerTypeVehicule);
        ArrayAdapter<CharSequence> typeVehAdapter = ArrayAdapter.createFromResource(this,R.array.typeVehicule, android.R.layout.simple_spinner_item);
        typeVehAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeVehicule.setAdapter(typeVehAdapter);

        nomVehicule = findViewById(R.id.inputNomVehicule);
        kmageInitial = findViewById(R.id.inputPremierKmage);
        final Button addVehiculeButton = findViewById(R.id.buttonAddVehicule);
        tvkmageInitial = findViewById(R.id.premierKmageText);

        final String vehiculeID = getIntent().getStringExtra("idVehicule");

        if (vehiculeID == null){
            addVehiculeButton.setText(R.string.valid);
        } else {
            vehID = Integer.parseInt(vehiculeID);
            vehIDString = vehiculeID;
            setTitle(R.string.modifveh);

            addVehiculeButton.setText(R.string.modif);
            tvkmageInitial.setVisibility(View.GONE);
            kmageInitial.setVisibility(View.GONE);

            VehiculeModel veh = vdao.selectVehicule(vehID);
            nomVehicule.setText(veh.getVehiculeNom());
            String typeVeh = veh.getVehiculeType();

            typeVehAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeVehicule.setAdapter(typeVehAdapter);
            int spinnerPosition = typeVehAdapter.getPosition(typeVeh);
            typeVehicule.setSelection(spinnerPosition);

        }

        addVehiculeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addVehiculeButton.getText().equals(getText(R.string.valid))) {
                addVehicule(v);
                } else if (addVehiculeButton.getText().equals(getText(R.string.modif))) {
                modifVehicule(v);
                }
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem plein = menu.findItem(R.id.pleinDuVehicule);
        MenuItem graph = menu.findItem(R.id.graphDuVehicule);
        MenuItem modif = menu.findItem(R.id.modifVehicule);
        modif.setVisible(false);
        graph.setVisible(false);
        plein.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent pageAccueil = new Intent(CreateVehicule.this, PageAccueil.class);
                startActivity(pageAccueil);
                return true;
            case R.id.info:

                //Creation du dialogue Aide
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.needhelp)
                        .setMessage(R.string.needHelpCreaVeh)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addVehicule(View view) {

        int kmageInitialvalue;

        if (kmageInitial.getText().toString().matches(""))
            kmageInitialvalue = 0;
        else {
            kmageInitialvalue = Integer.parseInt(kmageInitial.getText().toString());
        }

        Spinner spinner = findViewById(R.id.spinnerTypeVehicule);
        String name = nomVehicule.getText().toString();
        String type = spinner.getSelectedItem().toString();
        if (kmageInitialvalue == 0 || name.matches("") || kmageInitialvalue > 999999) {

            //Creation du dialogue données incomplètes
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.baddata)
                    .setMessage(R.string.alldata2)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {

            VehiculeModel vehicule = new VehiculeModel(name, type, kmageInitialvalue);
            vdao.ajouter(vehicule);
            Toast.makeText(CreateVehicule.this, R.string.toastAjouterVeh, Toast.LENGTH_SHORT).show();
            Intent pageAccueil = new Intent(CreateVehicule.this, PageAccueil.class);
            startActivity(pageAccueil);

        }
    }

    public void modifVehicule(View view) {
        Spinner spinner = findViewById(R.id.spinnerTypeVehicule);
        String type = spinner.getSelectedItem().toString();
        String name = nomVehicule.getText().toString();

        vdao.updateVehicule(vehID, name, type);
        Toast.makeText(CreateVehicule.this, R.string.toastModifierVeh, Toast.LENGTH_SHORT).show();
        Intent pageAccueil = new Intent(CreateVehicule.this, PageAccueil.class);
        startActivity(pageAccueil);

    }
}


