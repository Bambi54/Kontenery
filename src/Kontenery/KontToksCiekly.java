package Kontenery;

import Klasy.Nadawca;

public class KontToksCiekly extends KontToksyczny implements Ciekly{

    private final double gestosc;

    public KontToksCiekly(Nadawca nadawca, int waga_netto, int waga_brutto,
                          int nrCertyfikatu, String zabezpieczenia, double ld50,
                          double gestosc) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu, zabezpieczenia, ld50);
        this.gestosc = gestosc;
        setTyp(Typ.ToksCiekly);
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
