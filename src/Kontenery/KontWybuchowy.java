package Kontenery;

import Klasy.Nadawca;

public class KontWybuchowy extends KontCiezki{

    private final int wybuchowosc; // kT

    public KontWybuchowy(Nadawca nadawca, int waga_netto, int waga_brutto,
                         int nrCertyfikatu, String zabezpieczenia, int wybuchowosc) {
        super(nadawca, waga_netto, waga_brutto, nrCertyfikatu, zabezpieczenia);
        this.wybuchowosc = wybuchowosc;
        setTyp(Typ.Wybuch);
    }

    @Override
    public String toString() {
        return super.toString() + ", wybuchowość: " + wybuchowosc + "kt";
    }

    public String save(){
        return super.save() + "|wybuchowość:" + wybuchowosc;
    }

}
