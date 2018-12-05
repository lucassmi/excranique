package com.bonobocorp.joker.litrocent.Controler;

public class Calcul {

    public float calculConsommation (float qttyCarb, int kmage) {
        float conso;
        conso = (qttyCarb* 100) / kmage;
        return conso;
    }
}