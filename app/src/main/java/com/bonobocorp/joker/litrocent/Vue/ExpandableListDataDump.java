package com.bonobocorp.joker.litrocent.Vue;

import android.content.Context;

import com.bonobocorp.joker.litrocent.R;
import com.bonobocorp.joker.litrocent.Model.VehiculeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataDump {

    Context context;

    public ExpandableListDataDump (Context context) {
        this.context = context;

    }

    public HashMap<String, List<ChampText>> getData(List<VehiculeModel> list) {

        HashMap<String, List<ChampText>> expandableListDetail = new HashMap<>();


            for (VehiculeModel vehicule : list) {
                List<ChampText> expTextViewVehicule = new ArrayList<>();
                ChampText mesPleins = new ChampText(context);
                ChampText mesEntretiens = new ChampText(context);
                ChampText supprimer = new ChampText(context);
                ChampText stats = new ChampText(context);
                ChampText modif = new ChampText(context);
                String nomVehicule;

                String vehid = String.valueOf(vehicule.getId());
                nomVehicule = vehicule.getVehiculeNom() + "\n" + vehicule.getVehiculeType();

                mesPleins.setText(R.string.expMesPleins);
                mesPleins.setId(vehicule.getId());
                mesPleins.setIdveh(vehid);

                expTextViewVehicule.add(mesPleins);

                mesEntretiens.setText(R.string.expEntretiens);
                mesEntretiens.setId(vehicule.getId());
                mesEntretiens.setIdveh(vehid);

                expTextViewVehicule.add(mesEntretiens);

                stats.setText(R.string.expStats);
                stats.setId(vehicule.getId());
                stats.setIdveh(vehid);

                expTextViewVehicule.add(stats);

                modif.setText(R.string.modifVehicule);
                modif.setId(vehicule.getId());
                modif.setIdveh(vehid);

                expTextViewVehicule.add(modif);

                supprimer.setText(R.string.expSupprimer);
                supprimer.setId(vehicule.getId());
                supprimer.setIdveh(vehid);

                expTextViewVehicule.add(supprimer);

                expandableListDetail.put(nomVehicule, expTextViewVehicule);

            }

        return expandableListDetail;
    }

}