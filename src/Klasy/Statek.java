package Klasy;

import Kontenery.Kontener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Statek implements Comparable<Statek>{

    private int iloscToksIWybuch;
    private int iloscCiezki;
    private int iloscChlodniczy;
    private final int maxIloscToksIWybuch;
    private final int maxIloscCiezki;
    private final int maxIloscChlodniczy;
    private int iloscKontenerow;
    private final int maxIloscKontenerow;
    private int maxWaga;
    private final String nazwa;
    private final String portMacierzysty;
    private int id;
    static private int counter = 1;
    private List<Kontener> list = new ArrayList<>();

    public Statek(String nazwa, String portMacierzysty, int maxWaga,
                  int maxKont, int maxHeavy, int maxToksIWybuch, int maxChlod){
        this.nazwa = nazwa;
        this.portMacierzysty = portMacierzysty;
        this.maxWaga = maxWaga;
        this.iloscKontenerow = maxKont;
        maxIloscKontenerow = maxKont;
        this.iloscCiezki = maxHeavy;
        this.iloscToksIWybuch = maxToksIWybuch;
        this.iloscChlodniczy = maxChlod;
        maxIloscCiezki = maxHeavy;
        maxIloscToksIWybuch = maxToksIWybuch;
        maxIloscChlodniczy = maxChlod;
        id = counter++;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Kontener k : list) {
            s.append("\t\t-").append(k).append("\n\n");

        }
        return "Statek: "+ id + ". " + nazwa +
                ", port macierzysty: " + portMacierzysty +
                ", max waga: " + maxWaga +
                ", max ilość kontenerów: " + maxIloscKontenerow +
                ", max ilość kontenerów ciężkich: " + maxIloscCiezki +
                ", max ilość kontenerów toksycznych i wybuchowych: " + maxIloscToksIWybuch +
                ", max ilość kontenerów chłodniczych: " + maxIloscChlodniczy +
                ",\n\tKontenery: \n" + s;
    }

    public String quickPrint(){
        return "Statek: "+ id + ". " + nazwa;
    }

    public void addKont(Kontener k) throws OverloadedException {
        if(iloscKontenerow <= 0){
            throw new OverloadedException("Za dużo kontenerów! Statek pełny.", k);
        }
        if(maxWaga - k.getWaga_brutto() < 0){
            throw new OverloadedException("Za duża waga ładunku! Statek pełny.", k);
        }

        switch (k.getTyp()){
            case Ciezki : {
                if(iloscCiezki <= 0){
                    throw new OverloadedException(
                            "Za dużo ciężkich kontenerów!", k
                    );
                } else {
                    iloscCiezki--;
                }
            }
            case Wybuch, ToksSypki, ToksCiekly : {
                if(iloscToksIWybuch <= 0){
                    throw new OverloadedException(
                            "Za dużo toksycznych i/lub wybuchowych kontenerów!", k
                    );
                } else {
                    iloscToksIWybuch--;
                }
            }
            case Chlodniczy : {
                if (iloscChlodniczy <= 0) {
                    throw new OverloadedException(
                            "Za dużo kontenerów wymagających podłączenia do sieci elektrycznej!", k
                    );
                } else {
                    iloscChlodniczy--;
                }
            }

        }


        list.add(k);
        list.sort((o1, o2) -> o1.getWaga_brutto() - o2.getWaga_brutto());
        iloscKontenerow--;
        maxWaga = maxWaga - k.getWaga_brutto();

    }


    public Kontener unload(int id) throws IncorrectIDException{

        for (Kontener k : list){
            if(k.getId() == id){
                Kontener temp = k;
                list.remove(k);
                iloscKontenerow++;
                maxWaga += k.getWaga_brutto();
                kontCheckUnload(k);
                return temp;
            }
        }
        throw new IncorrectIDException();
    }


    public void kontCheckUnload(Kontener k){
        switch(k.getTyp()){
            case Wybuch, ToksSypki, ToksCiekly : {
                iloscToksIWybuch++;
            }
            case Ciezki : {
                iloscCiezki++;
            }
            case Chlodniczy : {
                iloscChlodniczy++;
            }
        }
    }

    public int getId(){
        return id;
    }

    public String getNazwa(){
        return nazwa;
    }

    @Override
    public int compareTo(Statek o) {
        return this.getNazwa().compareTo(o.getNazwa());
    }

    public String save(){
        return "Nazwa:" + nazwa +
                "|id:" + id +
                "|port macierzysty:" + portMacierzysty +
                "|maksymalna waga:" + maxWaga +
                "|maksymalna ilość kontenerów:" + maxIloscKontenerow +
                "|ilość kontenerów ciężkich:" + maxIloscCiezki +
                "|ilość kontenerów toksycznych i wybuchowych:" + maxIloscToksIWybuch +
                "|ilość kontenerów chłodniczych:" + maxIloscChlodniczy + "|\n";

    }

    public String saveKont(){
        StringBuilder s = new StringBuilder();

        for (Kontener k : list){
            s.append(k.save()).append("|\n");
        }
        return s + ";";
    }

    public void setId(int id) {
        this.id = id;
    }
}
