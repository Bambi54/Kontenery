package Kontenery;

import Klasy.Nadawca;

public class KontCiezki extends KontPodstawowy{

    private final String zabezpieczenia;

    public KontCiezki(Nadawca nadawca, int waga_netto, int waga_brutto, int nrCertyfikatu, String zabezpieczenia) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu);
        this.zabezpieczenia = zabezpieczenia;
        setTyp(Typ.Ciezki);
    }

    @Override
    public String toString() {
        return super.toString() + ", zabezpieczenia: " + zabezpieczenia;
    }

    public String save(){
        return super.save() + "|zabezpieczenia:" + zabezpieczenia;
    }



}
