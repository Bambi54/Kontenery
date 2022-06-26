package Kontenery;

import Klasy.Nadawca;

public class KontPodstawowy extends Kontener{

    private final int nrCertyfikatu;

    public KontPodstawowy(Nadawca nadawca, int waga_netto, int waga_brutto, int nrCertyfikatu) {
        super(nadawca, waga_netto, waga_brutto);
        this.nrCertyfikatu = nrCertyfikatu;
        setTyp(Typ.Podstawowy);
    }

    @Override
    public String toString() {
        return super.toString() + ", nr certyfikatu: " + nrCertyfikatu;
    }

    public String save(){
        return super.save() + "|nr certyfikatu:" + nrCertyfikatu;
    }
}
