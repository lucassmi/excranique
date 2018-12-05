package com.bonobocorp.joker.litrocent.Vue;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bonobocorp.joker.litrocent.Controler.Calcul;
import com.bonobocorp.joker.litrocent.Model.ItemData;
import com.bonobocorp.joker.litrocent.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateEntretien extends AppCompatActivity {

    EditText kmageEntretienInput;
    EditText nomEntretienInput;
    EditText comEntretienInput;
    Spinner typeEntretien;
    RadioGroup fixeOuIntervalle;
    String vehIDString;
    String isFixe;
    Button addEntretienBtn;
    int vehID;
    private static TextView dateAAfficher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_entretien);

        kmageEntretienInput = findViewById(R.id.kmageEntretienInput);
        nomEntretienInput = findViewById(R.id.inputNomEntretien);
        fixeOuIntervalle = findViewById(R.id.rgkmageFixeOuNon);
        dateAAfficher = findViewById(R.id.dateEntretienSelectionner);
        addEntretienBtn = findViewById(R.id.buttonAddEntretien);
        comEntretienInput = findViewById(R.id.inputComEntretien);

        addEntretienBtn.setText(R.string.valid);
        isFixe = "false";

        // Hide Keyboard onCreate
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Adapter Type entretien
        typeEntretien = findViewById(R.id.spinnerTypeEntretien);
        /*ArrayAdapter<CharSequence> typeCarbAdapter = ArrayAdapter.createFromResource(this, R.array.typeEntretienTab, android.R.layout.simple_spinner_item);
        typeCarbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeEntretien.setAdapter(typeCarbAdapter);*/

        //TODO Génerer dans boucle
        ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData(getResources().getString(R.string.entretienCourant),R.drawable.entrecourant));
        list.add(new ItemData(getResources().getString(R.string.revision),R.drawable.revision));
        list.add(new ItemData(getResources().getString(R.string.reparation), R.drawable.reparation));
        SpinnerAdapter adapter=new SpinnerAdapter(this, R.layout.row_spinner,R.id.infoSpinnerEntretien,list);
        typeEntretien.setAdapter(adapter);

        final String vehiculeID = getIntent().getStringExtra("idVehicule");
        vehIDString = vehiculeID;

        if (isFixe.equals("true")) {
            fixeOuIntervalle.check(R.id.radioFixe);
            isFixe = "true";

        } else {
            fixeOuIntervalle.check(R.id.radioIntervalle);
            isFixe = "false";
        }

        //Creation datePicker
        dateAAfficher = findViewById(R.id.dateEntretienSelectionner);
        Button displayTimeButton = findViewById(R.id.buttonSelectDateEntretien);
        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment mDatePicker = new DatePickerFragment();
                mDatePicker.show(getFragmentManager(), "select date");
            }
        });

        // Action quand changement de radioButton
        this.fixeOuIntervalle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnIsFixeChanged(group, checkedId);
            }
        });

        //Action quand ajout entretien
        addEntretienBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntretien(v);
            }
        });
    }

    // Action quand changement de radioButton
    private void doOnIsFixeChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if (checkedRadioId == R.id.radioFixe) {
            isFixe = "true";
        } else if (checkedRadioId == R.id.radioIntervalle) {
            isFixe= "false";
        }
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

            dateAAfficher.setText(String.format("%s/%s/%s", valueDay, valueMonth, String.valueOf(year)));
        }
    }

    public void addEntretien (View view) {
        Spinner spinner = findViewById(R.id.spinnerTypeEntretien);
        ItemData selectionSpinner = (ItemData) spinner.getSelectedItem();
        String dateATransformer = dateAAfficher.getText().toString();
        String tabDate[] = dateATransformer.split("/");

        String typeEntretienString = selectionSpinner.getText();
        String nomEntretien = nomEntretienInput.getText().toString();
        String comEntretien = comEntretienInput.getText().toString();
        String dateDeLEntretien = tabDate[2] + "-" + tabDate[1] + "-" + tabDate[0];
        int kmage = Integer.parseInt(kmageEntretienInput.getText().toString());

        // TODO Au boulot feignasse

        if (isFixe.equals("true")) {
            //faire des trucs
        } else {
            //faire d'autres trucs
        }



    }
}
