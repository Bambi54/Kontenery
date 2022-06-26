package Klasy;

import Kontenery.Kontener;

public class IrresponsibleSenderWithDangerousGoods extends Exception{

    private static int warningsCount = 0;
    private Kontener k;

    public IrresponsibleSenderWithDangerousGoods(String s) {
        super(s);
        warningsCount++;
    }

    public IrresponsibleSenderWithDangerousGoods(Kontener k){
        System.out.println("Kontener: " + k.getId() +
                " nie został dodany do magazynu z uwagi na za dużą liczbę ostrzeżeń. " +
                "Odsyłanie kontenera " + k.getId() + " do nadawcy.");
        this.k = k;
    }

    public Kontener zwrot(){
        return k;
    }

    public static int getWarningsCount() {
        return warningsCount;
    }

    public static void setWarningsCount(int warningsCount) {
        IrresponsibleSenderWithDangerousGoods.warningsCount = warningsCount;
    }
}
