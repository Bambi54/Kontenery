package Kontenery;

import Klasy.Nadawca;

public abstract class KontToksyczny extends KontCiezki{

    private final double ld50; // mg/kg masy ciała, poziom toksyczności

    public KontToksyczny(Nadawca nadawca, int waga_netto, int waga_brutto, int nrCertyfikatu, String zabezpieczenia, double ld50) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu, zabezpieczenia);
        this.ld50 = ld50;
    }

    @Override
    public String toString() {
        return super.toString() + ", ld50: " + ld50 + "mg/kg masy ciała";
    }

    public String save(){
        return super.save() + "|poziom toksyczności:" + ld50;
    }

}
