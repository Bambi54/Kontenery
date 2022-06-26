package Klasy;

import Kontenery.Kontener;

import java.util.ArrayList;
import java.util.List;

public class Pociag {

    private List<Kontener> kontenery;
    private int maxKont;
    private Thread t1;

    public Pociag(){
        kontenery = new ArrayList<>();
        maxKont = 10;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for (Kontener k : kontenery){
            s.append("\t\t-").append(k).append("\n\n");
        }

        return "Pociąg: \n" + "\tKontenery:\n" + s;
    }

    public void addKont(Kontener k) throws OverloadedException {
        if(maxKont <= 0){
            throw new OverloadedException("Skład kolejowy pełny! " +
                    "Poczekaj na odjazd przed załadowaniem kolejnego kontenera", k);
        }
        kontenery.add(k);
        maxKont--;
        if (maxKont == 0){
            depart();
        }

    }

    public void depart(){
        t1 = new Thread(() -> {
            System.out.println("Skład kolejowy pełny. Odliczanie 30s przed odjazdem pociągu.");
            for (int i = 1; i <= 6 && !Thread.currentThread().isInterrupted(); i++) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
                System.out.println("Pozostało: " + (30 - 5*i) +
                        " do odjazdu składu kolejowego.");
            }
            System.out.println("Pociąg odjechał.");
            kontenery.clear();
            maxKont = 10;
        });
        t1.start();
    }

    public Kontener unload(int n) throws IncorrectIDException{
        for (Kontener k : kontenery){
            if (k.getId() == n){
                return k;
            }
        }
        throw new IncorrectIDException();
    }

    public String pociagSave(){
        StringBuilder s = new StringBuilder();

        for (Kontener k : kontenery){
            s.append(k.save()).append("|\n");
        }
        return s + ";";
    }


}
