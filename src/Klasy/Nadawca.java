package Klasy;

import java.time.LocalDate;

public class Nadawca implements Comparable<Nadawca>{

    private final String imie;
    private final String nazwisko;
    private final int[] pesel = new int[11];
    private int id;
    private static int counter = 1;


    public Nadawca(String imie, String nazwisko, String pesel){
        this.imie = imie;
        this.nazwisko = nazwisko;
        for (int i = 0; i < this.pesel.length ; i++) {
            this.pesel[i] = Integer.parseInt(pesel.substring(i, i+1));
        }
        id = counter++;
    }

    public LocalDate getBirthdate(){
        int year = pesel[0]*10 + pesel[1];
        int month = pesel[2]*10 + pesel[3];
        int day = pesel[4]*10 + pesel[5];


        if(month <= 12){
            year += 1900;
        } else if(month <= 32){
            year += 2000;
            month -= 20;
        } else if(month <= 52){
            year += 2100;
            month -= 40;
        } else if(month <= 72){
            year += 2200;
            month -= 60;
        } else if(month <= 92){
            year += 1800;
            month -= 80;
        }

        return LocalDate.of(year, month, day);

    }

    @Override
    public int compareTo(Nadawca o) {
        return this.getImie().equals(o.getImie()) ?
                this.getNazwisko().compareTo(o.getNazwisko()) : this.getImie().compareTo(o.getImie());
    }

    public String peselToString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pesel.length; i++){
            s.append(pesel[i]);
        }
        return "" + s;
    }

    public String toString(){
        return id + ". " + imie + " " + nazwisko + " ur. " + getBirthdate();
    }

    public String nadawcaSave(){
        return id + "|" + imie + "|" + nazwisko + "|" + peselToString();
    }

    public void setId(int idN){
        this.id = idN;
    }


    public int getId(){
        return id;
    }

    public String getImie(){
        return imie;
    }

    public String getNazwisko(){
        return nazwisko;
    }
}
