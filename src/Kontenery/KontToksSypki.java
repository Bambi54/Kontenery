package Kontenery;

import Klasy.Nadawca;

public class KontToksSypki extends KontToksyczny {

    public KontToksSypki(Nadawca nadawca, int waga_netto, int waga_brutto,
                         int nrCertyfikatu, String zabezpieczenia, double ld50) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu, zabezpieczenia, ld50);
        setTyp(Typ.ToksSypki);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String save(){
        return super.save();
    }
}
