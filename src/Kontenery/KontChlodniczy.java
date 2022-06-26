package Kontenery;

import Klasy.Nadawca;

public class KontChlodniczy extends KontCiezki{

    private final int napiecie; // V

    public KontChlodniczy(Nadawca nadawca, int waga_netto, int waga_brutto,
                          int nrCertyfikatu, String zabezpieczenia, int napiecie) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu, zabezpieczenia);
        this.napiecie = napiecie;
        setTyp(Typ.Chlodniczy);
    }

    @Override
    public String toString() {
        return super.toString() + ", napięcie: " + napiecie + "V" ;
    }

    public String save(){
        return super.save() + "|napięcie:" + napiecie;
    }

}
