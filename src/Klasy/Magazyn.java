package Klasy;

import Kontenery.Kontener;

import java.time.LocalDate;
import java.util.*;

public class Magazyn{

    private Map<Kontener, LocalDate> magazyn = new LinkedHashMap<>();
    private int iloscKontenerow;
    private Thread t1;

    public Magazyn(int iloscKontenerow){
        this.iloscKontenerow = iloscKontenerow;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for (Map.Entry<Kontener, LocalDate> e : magazyn.entrySet()){
            s.append("\t\t-").append(e.getKey())
                    .append(", data utylizacji: ")
                    .append(e.getValue()).append("\n\n");
        }

        return "Magazyn: \n" + "\tKontenery:\n" + s;
    }

    public void add(Kontener k) throws MagazynOverloadedException, IrresponsibleSenderWithDangerousGoods{
        if(IrresponsibleSenderWithDangerousGoods.getWarningsCount() >= 2){
            throw new IrresponsibleSenderWithDangerousGoods(k);
        }
        putKont(k, k.expireDate(Date.getTime()));

    }

    public void putKont(Kontener k, LocalDate date) throws MagazynOverloadedException {
        if(iloscKontenerow <= 0){
            MagazynOverloadedException e1 = new MagazynOverloadedException(
                    "Odliczanie 30s po zapełnieniu składu, \nOpróżnij magazyn!",
                    k
            );
            this.t1 = new Thread(e1);
            t1.start();
            throw e1;
        }

        magazyn.put(k,date);
        sort();
        iloscKontenerow--;
    }

    public void sort(){            //https://stackoverflow.com/questions/12184378/sorting-linkedhashmap

        List<Map.Entry<Kontener, LocalDate>> entries = new ArrayList<>(magazyn.entrySet());

        entries.sort(((o1, o2) -> {
            LocalDate o1arrival = o1.getKey().arrivalDate(o1.getValue());
            LocalDate o2arrival = o2.getKey().arrivalDate(o2.getValue());
            Nadawca o1N = o1.getKey().getNadawca();
            Nadawca o2N = o2.getKey().getNadawca();

            if(o1.getValue() == null | o2.getValue() == null){
                return o1N.compareTo(o2N);
            }
            return o1arrival.equals(o2arrival) ?
                    o1N.compareTo(o2N) :
                    o1arrival.compareTo(o2arrival);
        }));

        Map<Kontener, LocalDate> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Kontener, LocalDate> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        magazyn = sortedMap;
    }


    public Kontener unload(int id) throws IncorrectIDException{
        for (Map.Entry<Kontener, LocalDate> e: magazyn.entrySet()){
            if(e.getKey().getId() == id){
                Kontener temp = e.getKey();
                magazyn.remove(e.getKey());
                iloscKontenerow++;
                if(!(t1 == null)) {
                    if (!t1.isInterrupted()) {
                        t1.interrupt();
                    }
                }
                return temp;
            }
        }
        throw new IncorrectIDException();
    }

    public void utilize(int id) throws IncorrectIDException{
        for (Map.Entry<Kontener, LocalDate> e: magazyn.entrySet()){
            if(e.getKey().getId() == id){
                magazyn.remove(e.getKey());
                iloscKontenerow++;
                System.out.println("Kontener: " + e.getKey().getId() +
                        " został zutylizowany pomyślnie.");
                return;
            }
        }
        throw new IncorrectIDException();
    }

    public void check(){
        for (Map.Entry<Kontener, LocalDate> e : magazyn.entrySet()){
            if(e.getValue() == null){
                continue;
            }

            if(e.getValue().equals(Date.getTime())){
                magazyn.remove(e.getKey());
                System.out.println("Usunięto kontener z powodu zbyt długiego magazynowania.");
                try {
                    throw new IrresponsibleSenderWithDangerousGoods(
                            "Kontener: " + e.getKey().getId() +
                            " został zutylizowany dnia: " + Date.getTime() +
                            ", data przybycia: " + e.getKey().arrivalDate(e.getValue()));
                } catch (IrresponsibleSenderWithDangerousGoods ex) {
                    ex.printStackTrace();
                    check();
                }
                return;
            }
        }

    }

    public int getIloscKontenerow() {
        int temp = 0;
        for (Map.Entry<Kontener, LocalDate> ignored : magazyn.entrySet()){
            temp++;
        }
        return iloscKontenerow + temp;
    }

    public String save(){
        StringBuilder s = new StringBuilder();

        for (Map.Entry<Kontener, LocalDate> e : magazyn.entrySet()){
            s.append(e.getKey().save()).append("|")
                    .append(e.getValue()).append("|\n");
        }
        return s + ";";

    }
}
