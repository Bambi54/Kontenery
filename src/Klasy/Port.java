package Klasy;

import java.util.ArrayList;
import java.util.List;

public class Port {

    private final Magazyn magazyn;
    private List<Statek> statki;
    private final Pociag pociag;

    public Port(Magazyn magazyn, Pociag pociag, Thread t1){
        this.magazyn = magazyn;
        statki = new ArrayList<>();
        this.pociag = pociag;
        t1.start();
    }

    public void addStatek(Statek statek){
        statki.add(statek);
        statki.sort(((o1, o2) -> o1.compareTo(o2)));

    }

    public List<Statek> getStatki(){
        return statki;
    }

    public void departStatek(Statek statek){
        System.out.println(statek.quickPrint() + " odpływa z portu.");
        statki.remove(statek);
    }

    public void showStatki(){
        for (Statek s : statki){
            System.out.println(s.quickPrint());
        }
    }

    public Statek findStatek(int id) throws IncorrectIDException{
        for (Statek s : statki){
            if(s.getId() == id){
                return s;
            }
        }
        throw new IncorrectIDException("Niepoprawne ID statku.");
    }

    public Magazyn getMagazyn(){
        return magazyn;
    }

    public Pociag getPociag(){
        return pociag;
    }

}
