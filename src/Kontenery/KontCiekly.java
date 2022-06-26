package Kontenery;

import Klasy.Nadawca;

public class KontCiekly extends KontPodstawowy implements Ciekly{

    private final double gestosc; // kg/m3

    public KontCiekly(Nadawca nadawca, int waga_netto, int waga_brutto,
                      int nrCertyfikatu, double gestosc) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu);
        this.gestosc = gestosc;
        setTyp(Typ.Ciekly);
    }

    @Override
    public String toString() {
        return super.toString() + ", gęstość: " + gestosc + "kg/m^3";
    }

    @Override
    public double getGestosc() {
        return gestosc;
    }

    public String save(){
        return super.save() + "|gęstość:" + getGestosc();
    }


}
