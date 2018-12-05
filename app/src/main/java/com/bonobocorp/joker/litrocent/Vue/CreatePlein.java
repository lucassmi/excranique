package com.bonobocorp.joker.litrocent.Vue;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bonobocorp.joker.litrocent.Controler.Calcul;
import com.bonobocorp.joker.litrocent.Controler.PleinDAO;
import com.bonobocorp.joker.litrocent.Model.PleinModel;
import com.bonobocorp.joker.litrocent.R;
import com.bonobocorp.joker.litrocent.Controler.VehiculeDAO;
import com.bonobocorp.joker.litrocent.Model.VehiculeModel;

import java.util.ArrayList;
import java.util.Calendar;

public class CreatePlein extends AppCompatActivity {

    private static TextView displayCurrentTime = null;

    PleinDAO pdao = new PleinDAO(this);
    VehiculeDAO vdao = new VehiculeDAO(this);
    Spinner typeCarb = null;
    EditText kmageActuel = null;
    EditText qttyCarb = null;
    EditText prixCarb = null;
    RadioGroup pleinComplet = null;
    TextView tvPleinComplet = null;
    String pleinCompletBool;
    Button addPleinButton = null;
    int vehID;
    int pleinID;
    String vehIDString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_plein);

        // Hide Keyboard onCreate
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Attribution des vues
        addPleinButton = findViewById(R.id.buttonAddPlein);
        kmageActuel = findViewById(R.id.inputKmageTotal);
        qttyCarb = findViewById(R.id.inputQttyCarb);
        prixCarb = findViewById(R.id.inputPrixTotal);
        tvPleinComplet = findViewById(R.id.tvReinitConso);
        pleinComplet = findViewById(R.id.pleinComplet);
        pleinComplet.check(R.id.radioPleinComplet0);
        pleinCompletBool = "false";

        //Adapter Type carburant
        typeCarb = findViewById(R.id.spinnerTypeCarb);
        ArrayAdapter<CharSequence> typeCarbAdapter = ArrayAdapter.createFromResource(this, R.array.pleinTypeCarb, android.R.layout.simple_spinner_item);
        typeCarbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeCarb.setAdapter(typeCarbAdapter);

        final String vehiculeID = getIntent().getStringExtra("idVehicule");
        final String spleinID = getIntent().getStringExtra("idPlein");

        vehID = Integer.parseInt(vehiculeID);
        vehIDString = vehiculeID;

        //Creation datePicker
        displayCurrentTime = findViewById(R.id.datepleinSelectionner);
        Button displayTimeButton = findViewById(R.id.buttonSelectDate);
        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment mDatePicker = new DatePickerFragment();
                mDatePicker.show(getFragmentManager(), "select date");

            }
        });

        if (spleinID == null){
            addPleinButton.setText(R.string.valid);
        } else {
            addPleinButton.setText(R.string.modif);
            setTitle(R.string.modifPlein);

            PleinModel plein;
            VehiculeModel veh;

            pleinID = Integer.parseInt(spleinID);
            plein = pdao.getPleinByIdVehPlein(vehID, pleinID);
            veh = vdao.selectVehicule(vehID);

            String stypeCarb = plein.getTypeCarb();
            String tabDate[] = plein.getDate().split("-");
            String spleinDate = tabDate[2] + "/" + tabDate[1] + "/" + tabDate[0];
            String sboolPleinComplet = plein.getPleinComplet();

            kmageActuel.setText(String.valueOf(veh.getKmagetotal()), TextView.BufferType.EDITABLE);
            qttyCarb.setText(String.valueOf(plein.getQttyCarb()), TextView.BufferType.EDITABLE);
            prixCarb.setText(String.valueOf(plein.getPrixTotal()), TextView.BufferType.EDITABLE);

            typeCarbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeCarb.setAdapter(typeCarbAdapter);
            int spinnerPosition = typeCarbAdapter.getPosition(stypeCarb);
            typeCarb.setSelection(spinnerPosition);
            displayCurrentTime.setText(spleinDate);

            pleinComplet.setVisibility(View.GONE);
            tvPleinComplet.setVisibility(View.GONE);


            if (sboolPleinComplet.equals("true")) {
                pleinComplet.check(R.id.radioPleinComplet1);
                pleinCompletBool = "true";

            } else {
                pleinComplet.check(R.id.radioPleinComplet0);
                pleinCompletBool = "false";
            }
        }

        // Action quand changement de radioButton
        this.pleinComplet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnPleinCompletChanged(group, checkedId);
            }
        });

        //Action quand ajout plein
        addPleinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlein(v);
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
                Intent pageAccueil = new Intent(CreatePlein.this, PageAccueil.class);
                startActivity(pageAccueil);
                return true;
            case R.id.info:
                String kmage = getResources().getString(R.string.erreur);

                if (addPleinButton.getText().equals(getText(R.string.valid))) {
                    kmage = vdao.getKmage(vehID);
                } else if (addPleinButton.getText().equals(getText(R.string.modif))) {
                    PleinModel plein;
                    plein = pdao.getPleinByIdVehPlein(vehID, pleinID);
                    kmage = String.valueOf(Integer.parseInt(vdao.getKmage(vehID)) - plein.getKmageParcouru());
                }

                //Creation du dialogue Aide
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.needhelp)
                        .setMessage(
                                "Le Kilométrage est celui en vigueur au moment du plein. Celui-ci doit être supérieur au dernier kilométrage renseigné pour le véhicule, qui est actuellement de : " + kmage + "km"
                                + "\nViennent ensuite la quantité de carburant ajoutée lors du plein, le prix payé, le type de carburant et la date à laquelle le plein à été renseigné."
                                + "\nVous pouvez réinitialiser la consommation moyenne, celle-ci sera calculée et affinée à chaque plein jusqu'à ce qu'elle soit réinitialisée de nouveau. Il est conseillé de faire un plein complet lors d'une réinitialisation."
                        )
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

    // Action quand changement de radioButton
    private void doOnPleinCompletChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if (checkedRadioId == R.id.radioPleinComplet0) {
            pleinCompletBool = "false";
        } else if (checkedRadioId == R.id.radioPleinComplet1) {
            Toast.makeText(CreatePlein.this, R.string.infoReinit, Toast.LENGTH_LONG).show();
            pleinCompletBool = "true";
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String valueMonth;
            String valueDay;
            if (month >= 0 && month < 9) {
                valueMonth = "0" + String.valueOf(month + 1);
            } else {
                valueMonth = String.valueOf(month + 1);
            }

            if (day >= 1 && day < 10) {
                valueDay = "0" + String.valueOf(day);
            } else {
                valueDay = String.valueOf(day);
            }

            displayCurrentTime.setText(String.format("%s/%s/%s", valueDay, valueMonth, String.valueOf(year)));
        }
    }

    public void addPlein(View view) {

        Spinner spinner = findViewById(R.id.spinnerTypeCarb);
        int kmageTotal;
        float qttyCarburant;
        float prixCarburant;

        if (kmageActuel.getText().toString().matches("")) {
            kmageTotal = 0;
        } else {
            kmageTotal = Integer.parseInt(kmageActuel.getText().toString());
        }

        if (qttyCarb.getText().toString().matches("")) {
            qttyCarburant = 0;
        } else {
            qttyCarburant = Float.parseFloat(qttyCarb.getText().toString());
        }

        if (prixCarb.getText().toString().matches("")) {
            prixCarburant = 0;
        } else {
            prixCarburant = Float.parseFloat(prixCarb.getText().toString());
        }

        String typeCarb = spinner.getSelectedItem().toString();

        int kmageParcouru;

        //split date pour enregistrer au format US
        String displayDate = displayCurrentTime.getText().toString();

        if (kmageTotal == 0 || qttyCarburant == 0 || prixCarburant == 0 || displayDate.matches("")) {

            //Creation du dialogue données incomplètes
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(R.string.baddata)
                    .setMessage(R.string.alldata)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            String tabDate[] = displayDate.split("/");
            String date = tabDate[2] + "-" + tabDate[1] + "-" + tabDate[0];
            Calcul calcul = new Calcul();

            // TODO
            //VOIR POUR CHANGER LES OPERATIONS CI DESSOUS DANS AUTRE CLASSE CALCULS
            int kmageTotalVeh = 0;

            if (addPleinButton.getText().equals(getText(R.string.valid))) {
                kmageTotalVeh = Integer.parseInt(vdao.getKmage(vehID));
            } else if (addPleinButton.getText().equals(getText(R.string.modif))) {
                PleinModel plein;
                plein = pdao.getPleinByIdVehPlein(vehID, pleinID);
                kmageTotalVeh = Integer.parseInt(vdao.getKmage(vehID)) - plein.getKmageParcouru();

                System.out.println(kmageTotalVeh + "LA ----------------------------------------------------------------------------");

                //PleinModel plein;
                //plein = pdao.getPleinByIdVehPlein(vehID, pleinID);
                //kmageTotalVeh = Integer.parseInt(vdao.getKmage(vehID)) - plein.getKmageParcouru();
            }

            kmageParcouru = kmageTotal - kmageTotalVeh ;

            VehiculeModel veh = vdao.selectVehicule(vehID);
            String dateLastPleinCompletVehicule = veh.getDateLastPleinComplet();
            ArrayList<PleinModel> listAllPleins;
            ArrayList<PleinModel> listPleinsDepuisDernierPleinComplet;

            listAllPleins = pdao.getPleinsByIdVeh(vehID, " DESC ");

            // Verification des inputs
            //if (kmageTotal < kmageTotalVeh  || kmageTotal < veh.getKmageLastPleinComplet() || kmageTotal < veh.getKmageInitial()) {
            if (kmageTotal < kmageTotalVeh || kmageTotal < veh.getKmageInitial()){

                //Creation du dialogue kmage Incohérent
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle(R.string.baddata)
                        .setMessage(R.string.badkm)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {

                float conso;
                float conso2;
                int totalKmParcouru = 0;
                float totalQttyCarb = 0;
                boolean testTrue = false;


                if (listAllPleins.isEmpty()) {
                    //conso = (qttyCarburant * 100) / kmageParcouru;
                    conso = 0;
                    conso2 = 0;
                } else {
                    for (PleinModel plein : listAllPleins) {
                        if (plein.getPleinComplet().equals("true")) {
                            testTrue = true;
                        }
                    }
                    if (testTrue) {
                        listPleinsDepuisDernierPleinComplet = pdao.getPleinsAfterDate(dateLastPleinCompletVehicule, vehID);

                        if (addPleinButton.getText().equals(getText(R.string.valid))) {
                            totalKmParcouru = kmageParcouru;
                            totalQttyCarb = qttyCarburant;
                        } else if (addPleinButton.getText().equals(getText(R.string.modif))) {
                            PleinModel plein;
                            plein = pdao.getPleinByIdVehPlein(vehID, pleinID);
                            totalKmParcouru = kmageParcouru - plein.getKmageParcouru();
                            totalQttyCarb = qttyCarburant - plein.getQttyCarb();
                        }

                        for (PleinModel plein : listPleinsDepuisDernierPleinComplet) {
                            totalKmParcouru += plein.getKmageParcouru();
                            totalQttyCarb += plein.getQttyCarb();
                        }
                        //conso = (qttyCarburant * 100) / kmageParcouru;
                        //conso2 = (totalQttyCarb * 100) / totalKmParcouru;
                        conso = calcul.calculConsommation(qttyCarburant, kmageParcouru);
                        conso2 = calcul.calculConsommation(totalQttyCarb, totalKmParcouru);
                    } else {
                        conso = calcul.calculConsommation(qttyCarburant, kmageParcouru);
                        conso2 = 0;
                        //conso = (qttyCarburant * 100) / kmageParcouru;
                        //conso2 = 0;
                    }
                }

                PleinModel monPlein = new PleinModel(kmageParcouru, qttyCarburant, prixCarburant, typeCarb, conso, conso2, date, pleinCompletBool);

                if (pleinCompletBool.equals("true")) {
                    // appel des méthodes vdao et pdao si plein complet
                    vdao.updateVehiculeKmageTotal(vehID, kmageTotal);
                    vdao.updateVehiculeKmageLastPleinComplet(vehID, kmageTotal);
                    vdao.updateVehiculeDateLastPleinComplet(vehID, date);

                    if (addPleinButton.getText().equals(getText(R.string.valid))) {
                        pdao.ajouter(monPlein, vehID);
                        Toast.makeText(CreatePlein.this, R.string.toastAjouterPlein, Toast.LENGTH_SHORT).show();
                    } else if (addPleinButton.getText().equals(getText(R.string.modif))) {
                        pdao.modifier(monPlein, pleinID);
                        Toast.makeText(CreatePlein.this, R.string.toastModifierPlein, Toast.LENGTH_SHORT).show();
                    }

                } else {

                    //appel des méthodes vdao et pdao si plein non complet
                    vdao.updateVehiculeKmageTotal(vehID, kmageTotal);

                    if (addPleinButton.getText().equals(getText(R.string.valid))) {
                        pdao.ajouter(monPlein, vehID);
                        Toast.makeText(CreatePlein.this, R.string.toastAjouterPlein, Toast.LENGTH_SHORT).show();
                    } else if (addPleinButton.getText().equals(getText(R.string.modif))) {
                        pdao.modifier(monPlein, pleinID);
                        Toast.makeText(CreatePlein.this, R.string.toastModifierPlein, Toast.LENGTH_SHORT).show();
                    }
                }
                Intent pleinMainPage = new Intent(CreatePlein.this, PleinsMainPage.class);
                pleinMainPage.putExtra("idVehicule", vehIDString);
                startActivity(pleinMainPage);

            }
        }
    }

}
