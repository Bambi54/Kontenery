package Klasy;

import Kontenery.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {

    private static Port port;
    private static List<Nadawca> nadawcy = new ArrayList<>();
    public static final Scanner s = new Scanner(System.in);
    public static final BufferedReader s2 = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedReader br;
    public static BufferedWriter bw;


    public static void start(){
        int kod;
        do{
            System.out.println(
                    "Bieżąca data: " + Date.getTime() +
                """
                
                \tWybierz polecenie:
                \t1. Załadowanie kontenera na wagon/do magazynu
                \t2. Załadowanie kontenera na statek
                \t3. Pokazanie informacji o statku
                \t4. Pokazanie informacji o magazynie
                \t5. Pokazanie informacji o pociągu
                \t6. Utylizacja kontenera z magazynu
                \t7. Odpłynięcie statku z portu
                \t8. Dodanie nowego statku
                \t9. Dodanie nowego kontenera
                \t10. Dodanie nowego nadawcy
                \t11. Zapisanie stanu aplikacji
                \t12. Wczytanie stanu aplikacji
                \t13. Koniec programu"""
            );
            kod = s.nextInt();

            switch(kod){
                case 1 -> kontToMag();
                case 2 -> kontToStatek();
                case 3 -> showStatek();
                case 4 -> showMagazyn();
                case 5 -> showPociag();
                case 6 -> utylizuj();
                case 7 -> departStatek();
                case 8 -> addStatek();
                case 9 -> addKontScan();
                case 10 -> addNadawca();
                case 11 -> save();
                case 12 -> read();
                case 13 -> {}
                default -> {
                    System.out.println("Niepoprawny numer. Spróbuj jeszcze raz.");
                    UI.start();
                    return;
                }
            }
        } while(kod != 13);
        System.out.println("Zamykanie aplikacji.");
        System.exit(0);
    }

    public static void kontToMag(){
        Kontener tempKont = null;
        Statek tempStatek = null;
        int idK;
        try {
            tempStatek = findStatek();
            idK = findKontId(tempStatek);
            tempKont = tempStatek.unload(idK);
        } catch (IncorrectIDException e) {
            e.printStackTrace();
            kontToMag();
            return;
        }

        System.out.println(
            """
            \tPodaj destynację:
            \t1. Wagon kolejowy
            \t2. Magazyn"""
        );
        int kod = s.nextInt();

        switch (kod){
            case 1 -> {
                try {
                    port.getPociag().addKont(tempKont);
                    System.out.println("Kontener dodano pomyślnie do wagonu transportowego.");
                } catch (OverloadedException e) {
                    e.printStackTrace();
                    try {
                        tempStatek.addKont(e.zwrot());
                    } catch (OverloadedException ex) {
                        ex.printStackTrace();
                    }

                }
            }
            case 2 -> {
                try {
                    port.getMagazyn().add(tempKont);
                    System.out.println("Kontener dodano pomyślnie do magazynu.");
                } catch (IrresponsibleSenderWithDangerousGoods e1) {
                    try {
                        tempStatek.addKont(e1.zwrot());
                    } catch (OverloadedException e) {
                        e.printStackTrace();
                    }

                } catch (MagazynOverloadedException e2){
                    e2.printStackTrace();
                    try {
                        tempStatek.addKont(e2.zwrot());
                    } catch (OverloadedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static void kontToStatek(){
        Kontener tempKont = null;
        Statek tempStatek = null;
        int idK = 0;
        try {
            tempStatek = findStatek();
            idK = findKontId();
        } catch (IncorrectIDException e) {
            e.printStackTrace();
        }

        try {
            tempKont = port.getMagazyn().unload(idK);
        } catch (IncorrectIDException e) {
            try {
                tempKont = port.getPociag().unload(idK);
            } catch (IncorrectIDException ex) {
                ex.printStackTrace();
            }
        }

        try {
            tempStatek.addKont(tempKont);
            System.out.println("Kontener dodano pomyślnie.");
        } catch (OverloadedException e) {
            e.printStackTrace();
        }

    }

    public static void showStatek(){
        try {
            System.out.println(findStatek());
        } catch (IncorrectIDException e) {
            e.printStackTrace();
            showStatek();
        }
    }

    public static Statek findStatek() throws IncorrectIDException {
        port.showStatki();
        System.out.println("\tPodaj id statku: ");
        int idS = s.nextInt();
        return port.findStatek(idS);
    }

    public static int findKontId(Statek statek) throws IncorrectIDException {
        System.out.println(statek);
        System.out.println("\tPodaj id kontenera: ");
        return s.nextInt();
    }

    public static int findKontId(){
        showMagazyn();
        showPociag();
        System.out.println("\tPodaj ID kontenera:");
        return s.nextInt();
    }

    public static void showMagazyn(){
        System.out.println("Stan magazynu: ");
        System.out.println(port.getMagazyn());
    }

    public static void showPociag(){
        System.out.println("Kontenery na pociągu:");
        System.out.println(port.getPociag());
    }

    public static void utylizuj(){
        showMagazyn();
        System.out.println("\tPodaj ID kontenera do utylizacji:");
        int id = s.nextInt();
        try {
            port.getMagazyn().utilize(id);
        } catch (IncorrectIDException e) {
            e.printStackTrace();
        }

    }

    public static void departStatek(){
        System.out.println("Wybierz statek który ma odpłynąć z portu.");
        Statek s;
        try {
            s = findStatek();
        } catch (IncorrectIDException e) {
            System.out.println("Niepoprawne id, wybierz jeszcze raz.");
            departStatek();
            return;
        }

        port.departStatek(s);

    }

    public static void addStatek(){
        System.out.println("Dodawanie statku:");

        try {
            System.out.println("\tNazwa: ");
            String nazwa = s2.readLine();
            System.out.println("\tPort macierzysty: ");
            String portMac = s2.readLine();

            System.out.println("\tMaksymalna dopuszczalna waga: (kg)");
            int maxWaga = s.nextInt();

            System.out.println("\tMaksymalna dopuszczalna ilość kontenerów: ");
            int maxIloscKont = s.nextInt();

            System.out.println("\tMaksymalna dopuszczalna ilość kontenerów ciężkich: ");
            System.out.println("info: " +
                    "suma maksymalnej ilości kontenerów wybuchowych i chłodniczych " +
                    "nie może przekroczyć liczby kontenerów ciężkich");
            int maxHeavy = s.nextInt();
            System.out.println("\tMaksymalna dopuszczalna ilość kontenerów wybuchowych: ");
            int maxWybuch = s.nextInt();
            System.out.println("\tMaksymalna dopuszczalna ilość kontenerów chłodniczych: ");
            int maxChlod = s.nextInt();
            System.out.println("\tMaksymalna dopuszczalna ilość kontenerów toksycznych:");
            int maxToks = s.nextInt();
            Statek statek = new Statek(nazwa, portMac, maxWaga, maxIloscKont,
                    maxHeavy, maxToks+maxWybuch, maxChlod);
            port.addStatek(statek);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void addKontScan(){
        System.out.println(
            """
            \tDodawanie kontenerów
            \tPodaj typ kontenera:
            \t1. Kontener podstawowy
            \t2. Kontener ciężki
            \t3. Kontener chłodniczy
            \t4. Kontener na materiały ciekłe
            \t5. Kontener na materiały wybuchowe
            \t6. Kontener na materiały toksyczne sypkie
            \t7. Kontener na materiały toksyczne ciekłe
            \t8. Wyjdź"""
        );
        int kod = s.nextInt();
        if(kod == 8){
            return;
        }
        System.out.println("\tCechy kontenera: ");
        System.out.println("\tWybierz nadawcę: ");
        showNadawcy();
        System.out.println("\tPodaj id nadawcy kontenera: ");
        int id = s.nextInt();
        Nadawca n = null;
        try {
            n = findNadawca(id);
        } catch (IncorrectIDException e) {
            e.printStackTrace();
            addKontScan();
            return;
        }
        System.out.println("\tPodaj wagę netto: (kg)");
        int netto = s.nextInt();
        System.out.println("\tPodaj wagę brutto: (kg)");
        int brutto = s.nextInt();
        System.out.println("\tPodaj numer certyfikatu: ");
        int cert = s.nextInt();
        switch (kod){
            case 1 -> {
                KontPodstawowy k = new KontPodstawowy(
                        n, netto, brutto, cert
                );
                putKontener(k);
            }
            case 2 ->  {

                try {
                    System.out.println("\tPodaj zabezpieczenia: ");
                    String zab = s2.readLine();
                    KontCiezki k = new KontCiezki(n, netto, brutto, cert, zab);
                    putKontener(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            case 3 -> {
                try {
                    System.out.println("\tPodaj zabezpieczenia: ");
                    String zab = s2.readLine();
                    System.out.println("\tPodaj napięcie pod jakim kontener ma być podłączony: (V)");
                    int volt = s.nextInt();
                    KontChlodniczy k = new KontChlodniczy(n, netto, brutto, cert, zab, volt);
                    putKontener(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> {
                System.out.println("\tPodaj gęstość cieczy w kontenerze: (kg/m^3)");
                double gestosc = s.nextDouble();
                KontCiekly k = new KontCiekly(n, netto, brutto, cert, gestosc);
                putKontener(k);
            }
            case 5 -> {
                try {
                    System.out.println("\tPodaj zabezpieczenia: ");
                    String zab = s2.readLine();
                    System.out.println("\tPodaj skalę wybuchowości w kilotonach: ");
                    int wybuch = s.nextInt();
                    KontWybuchowy k = new KontWybuchowy(n, netto, brutto, cert, zab, wybuch);
                    putKontener(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            case 6 -> {

                try {
                    System.out.println("\tPodaj zabezpieczenia: ");
                    String zab = s2.readLine();
                    System.out.println("\tPodaj w skali ld50 jak toksyczny jest materiał w kontenerze: (mg/kg masy ciała)");
                    double ld50 = s.nextDouble();
                    KontToksSypki k = new KontToksSypki(n, netto, brutto, cert, zab, ld50);
                    putKontener(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            case 7 -> {

                try {
                    System.out.println("\tPodaj zabezpieczenia: ");
                    String zab = s2.readLine();
                    System.out.println("\tPodaj w skali ld50 jak toksyczny jest materiał w kontenerze: (mg/kg masy ciała)");
                    double ld50 = s.nextDouble();
                    System.out.println("\tPodaj gęstość cieczy w kontenerze: (kg/m^3)");
                    double gestosc = s.nextDouble();
                    KontToksCiekly k = new KontToksCiekly(n, netto, brutto, cert, zab, ld50, gestosc);
                    putKontener(k);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            default -> {
                System.out.println("Niepoprawny numer. Spróbuj ponownie.");
                addKontScan();
            }

        }
        addKontScan();
    }

    public static void putKontener(Kontener k){
        System.out.println(
            """
            \tPodaj gdzie dodać kontener:
            \t1. Statek
            \t2. Magazyn
            \t3. Wagon kolejowy"""
        );

        int id2 = s.nextInt();
        switch (id2){
            case 1 -> {
                try {
                    Statek statek = findStatek();
                    statek.addKont(k);
                    System.out.println("Kontener dodano do statku pomyślnie.");
                } catch (OverloadedException | IncorrectIDException e) {
                    e.printStackTrace();
                }

            }
            case 2 -> {
                try {
                    port.getMagazyn().add(k);
                    System.out.println("Kontener dodano do magazynu pomyślnie.");
                } catch (MagazynOverloadedException | IrresponsibleSenderWithDangerousGoods e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                try {
                    port.getPociag().addKont(k);
                    System.out.println("Kontener dodano do pociągu pomyślnie.");
                } catch (OverloadedException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                System.out.println("Niepoprawny numer. Spróbuj ponownie.");
                putKontener(k);
            }
        }

    }

    public static void addNadawca(){
        System.out.println("Dodawanie nadawcy: ");
        try {
            System.out.println("Podaj imię:");
            String name = s2.readLine();
            System.out.println("Podaj nazwisko:");
            String sname = s2.readLine();
            System.out.println("Podaj numer pesel:");
            String pesel = s2.readLine();
            UI.addNadawca(new Nadawca(name, sname, pesel));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Nadawcę dodano pomyślnie.");
    }

    public static void save() {
        try {
            bw = new BufferedWriter(new FileWriter("src/data/genData.txt"));

            bw.write("|" + Date.getTime().toString() + "|\n");
            bw.write("warnings count |" + IrresponsibleSenderWithDangerousGoods.getWarningsCount() + "|\n");

            bw.close();

            bw = new BufferedWriter(new FileWriter("src/data/nadawcyData.txt"));

            for (Nadawca n : UI.nadawcy) {
                bw.write("nadawca|" + n.nadawcaSave() + "|\n");

            }
            bw.write("/");

            bw.close();

            bw = new BufferedWriter(new FileWriter("src/data/magazynData.txt"));

            bw.write("magazyn: max ilość kontenerów|" + port.getMagazyn().getIloscKontenerow() + "|\n" );
            bw.write(port.getMagazyn().save());

            bw.close();

            bw = new BufferedWriter(new FileWriter("src/data/pociagData.txt"));

            bw.write("pociąg\n");
            bw.write(port.getPociag().pociagSave());

            bw.close();

            bw = new BufferedWriter(new FileWriter("src/data/statkiData.txt"));

            for (Statek s : port.getStatki()) {
                bw.write("statek\n");
                bw.write(s.save());
                bw.write(s.saveKont());
            }
            bw.write("/");

            bw.close();


            bw = new BufferedWriter(new FileWriter("readableOutput.txt"));

            bw.write("Data: " + Date.getTime().toString() + "\n");
            bw.write("Ilość otrzymanych ostrzeżeń: " + IrresponsibleSenderWithDangerousGoods.getWarningsCount() + "\n");

            bw.write(port.getMagazyn().toString());
            bw.write("\n");

            bw.write(port.getPociag().toString());
            bw.write("\n");

            for (Statek s : port.getStatki()) {
                bw.write(s.toString());
                bw.write("\n");
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Stan aplikacji zapisano pomyślnie.");
    }

    public static void read(){
        try {
            br = new BufferedReader(new FileReader("src/data/genData.txt"));
            iterate('|');

            Date.setTime(appendDate());

            iterate('|');
            int warningsCount = appendInt('|');
            IrresponsibleSenderWithDangerousGoods.setWarningsCount(warningsCount);

            br.close();

            br = new BufferedReader(new FileReader("src/data/nadawcyData.txt"));

            for (int i = br.read(); i != '/' ; i = br.read()) {
                iterate('|');
                int id = appendInt('|');
                String imie = appendString('|');
                String naz = appendString('|');
                String pesel = appendString('|');
                Nadawca n = new Nadawca(imie, naz, pesel);
                n.setId(id);
                UI.addNadawca(n);
                br.readLine();

            }
            br.close();

            br = new BufferedReader(new FileReader("src/data/magazynData.txt"));

            iterate('|');
            int iloscmag = appendInt('|');
            Magazyn magazyn1 = new Magazyn(iloscmag);
            br.readLine();
            try {
                for (int i = br.read(); i != ';' && i != -1; i = br.read()) {
                    Kontener k = readKont();
                    LocalDate d = appendDate();
                    magazyn1.putKont(k,d);
                    br.readLine();
                }
            } catch (MagazynOverloadedException e) {
                System.out.println("Brak możliwości dodania do magazynu!");
            }
            System.out.println(magazyn1);

            br.close();

            br = new BufferedReader(new FileReader("src/data/pociagData.txt"));

            Pociag pociag1 = new Pociag();
            for (int i = br.read(); i != ';' && i != -1; i = br.read()) {
                try {
                    Kontener k = readKont();
                    pociag1.addKont(k);
                    br.readLine();
                } catch (OverloadedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(pociag1);

            br.close();

            Port port = new Port(
                    magazyn1,
                    pociag1,
                    new Thread(() -> {
                        while(!Thread.currentThread().isInterrupted()) {
                            Date.addDate();
                            UI.port.getMagazyn().check();
                        }
                    })
            );
            UI.setPort(port);

            br = new BufferedReader(new FileReader("src/data/statkiData.txt"));

            for (int i = br.read(); i != '/' && i != -1; i = br.read()) {
                br.readLine();
                iterate(':');
                String nazwa = appendString('|');
                iterate(':');
                int id = appendInt('|');
                iterate(':');
                String portMac = appendString('|');
                iterate(':');
                int maxWaga = appendInt('|');
                iterate(':');
                int maxIloscKont = appendInt('|');
                iterate(':');
                int maxHeavy = appendInt('|');
                iterate(':');
                int maxToksIWybuch = appendInt('|');
                iterate(':');
                int maxChlod = appendInt('|');


                Statek s = new Statek(
                        nazwa, portMac,
                        maxWaga, maxIloscKont,
                        maxHeavy, maxToksIWybuch,
                        maxChlod
                );
                s.setId(id);
                port.addStatek(s);

                while (i != ';') {
                    try {
                        Kontener k = readKont();
                        s.addKont(k);
                        br.readLine();
                        i = br.read();
                    } catch (OverloadedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(s);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Stan aplikacji wczytano pomyślnie.");

    }

    public static Kontener readKont() throws IOException {
        iterate(':');

        int idN = appendInt('|');
        String nadImie = appendString('|');
        String nadNazwisko = appendString('|');
        String nadPesel = appendString('|');


        Nadawca n1 = null;
        try {
            n1 = findNadawca(idN);
        } catch (IncorrectIDException e) {
           e.printStackTrace();
        }

        iterate(':');
        int kontType = appendInt('.');
        iterate(':');
        int idKont = appendInt('|');
        iterate(':');
        int wagaBrutto = appendInt('|');
        iterate(':');
        int wagaNetto = appendInt('|');
        iterate(':');
        int nrCert = appendInt('|');

        switch (kontType) {
            case 0 -> {
                KontPodstawowy k = new KontPodstawowy(
                        n1, wagaNetto, wagaBrutto, nrCert
                );
                k.setId(idKont);
                return k;
            }
            case 1 -> {
                iterate(':');
                String zab = appendString('|');
                KontCiezki k = new KontCiezki(
                        n1, wagaNetto, wagaBrutto, nrCert, zab
                );
                k.setId(idKont);
                return k;
            }
            case 2 -> {
                iterate(':');
                String zab = appendString('|');
                iterate(':');
                int wybuch = appendInt('|');
                KontWybuchowy k = new KontWybuchowy(
                        n1, wagaNetto, wagaBrutto, nrCert, zab, wybuch
                );
                k.setId(idKont);
                return k;
            }
            case 3-> {
                iterate(':');
                String zab = appendString('|');
                iterate(':');
                int napiecie = appendInt('|');
                KontChlodniczy k = new KontChlodniczy(
                        n1, wagaNetto, wagaBrutto, nrCert, zab, napiecie
                );
                k.setId(idKont);
                return k;
            }
            case 4 -> {
                iterate(':');
                double gestosc = appendDouble('|');
                KontCiekly k = new KontCiekly(
                        n1, wagaNetto, wagaBrutto, nrCert, gestosc
                );
                k.setId(idKont);
                return k;
            }
            case 5 ->{
                iterate(':');
                String zab = appendString('|');
                iterate(':');
                double ld50 = appendDouble('|');
                KontToksSypki k = new KontToksSypki(
                        n1, wagaNetto, wagaBrutto, nrCert, zab, ld50
                );
                k.setId(idKont);
                return k;
            }
            case 6 -> {
                iterate(':');
                String zab = appendString('|');
                iterate(':');
                double ld50 = appendDouble('|');
                iterate(':');
                double gestosc = appendDouble('|');
                KontToksCiekly k = new KontToksCiekly(
                        n1, wagaNetto, wagaBrutto, nrCert, zab, ld50, gestosc
                );
                k.setId(idKont);
                return k;
            }
            default -> {
                return null;
            }
        }
    }


    public static LocalDate appendDate() throws IOException {
        String year = appendString('-');
        if(year.equals("null")){
            return null;
        }
        String month = appendString('-');
        String day = appendString('|');
        return LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day)
        );
    }

    public static int appendInt(int endChar) throws IOException {
        StringBuilder s = new StringBuilder();
        for (int i = br.read(); i != endChar && i != -1 ; i = br.read()) {
            s.append((char)i);
        }

        return Integer.parseInt(s.toString());
    }

    public static double appendDouble(int endChar) throws IOException{
        StringBuilder s = new StringBuilder();
        for (int i = br.read(); i != endChar && i != -1 ; i = br.read()) {
            s.append((char)i);
        }
        return Double.parseDouble(s.toString());
    }

    public static String appendString(int endChar) throws IOException {
        StringBuilder s = new StringBuilder();
        for (int i = br.read(); i != endChar && i != -1 ; i = br.read()) {
            s.append((char)i);
            if(s.toString().equals("null")){
                return s.toString();
            }
        }
        return s.toString();
    }

    public static void iterate(int endChar) throws IOException {
        for (int i = br.read(); i != endChar && i != -1; i = br.read()) ;
    }

    public static void showNadawcy(){
        for (Nadawca n : nadawcy){
            System.out.println(n);
        }
    }

    public static Nadawca findNadawca(int id) throws IncorrectIDException {
        for (Nadawca n : nadawcy) {
            if (n.getId() == id) {
                return n;
            }
        }
        throw new IncorrectIDException("Niepoprawne ID nadawcy.");
    }

    public static void addNadawca(Nadawca n){
        nadawcy.add(n);
    }

    public static void setPort(Port port) {
        UI.port = port;
    }
}
