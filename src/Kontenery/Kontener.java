package Kontenery;

import Klasy.Nadawca;

import java.time.LocalDate;

public abstract class Kontener{

    private final Nadawca nadawca;
    private final int tara;
    private final int waga_netto;
    private final int waga_brutto;
    private int id;
    static private int counter = 1;
    private Typ typ;

    public Kontener(Nadawca nadawca, int waga_netto, int waga_brutto){
        this.nadawca = nadawca;
        this.waga_netto = waga_netto;
        this.waga_brutto = waga_brutto;
        tara = waga_brutto - waga_netto;
        id = counter++;
    }

    public String toString(){
        return "Kontener " + id + " od " + nadawca + ", typ kontenera: " + getTyp() + ",\n\t\t\twaga brutto: " + waga_brutto + "kg, tara: " + tara + "kg";
    }
    public void setTyp(Typ typ){
        this.typ = typ;
    }

    public Typ getTyp(){
        return typ;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getWaga_brutto(){
        return waga_brutto;
    }

    public int getWaga_netto() {
        return waga_netto;
    }

    public Nadawca getNadawca(){
        return nadawca;
    }

    public String save(){
        return "Nadawca:" + getNadawca().nadawcaSave() + "|"
                + "typ kontenera:" + getTyp().getValue()+ "." + getTyp() + "|"
                + "id kontenera:" + id + "|"
                + "waga brutto:" + getWaga_brutto() + "|"
                + "waga netto:" + getWaga_netto();
    }

    public LocalDate expireDate(LocalDate d){
        if (d == null){
            return null;
        }
        switch(this.getTyp()){
            case Wybuch -> {
                return d.plusDays(5);
            }
            case ToksSypki -> {
                return d.plusDays(14);
            }
            case ToksCiekly -> {
                return d.plusDays(10);
            }
            default -> {
                return null;
            }
        }
    }

    public LocalDate arrivalDate(LocalDate d){
        if (d == null){
            return null;
        }
        switch(this.getTyp()){
            case Wybuch -> {
                return d.minusDays(5);
            }
            case ToksSypki -> {
                return d.minusDays(14);
            }
            case ToksCiekly -> {
                return d.minusDays(10);
            }
            default -> {
                return null;
            }
        }
    }




}
