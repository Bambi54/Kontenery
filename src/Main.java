import Klasy.*;
import Kontenery.*;

import java.time.LocalDate;

/*
UWAGI:

watki - 2 co najmniej, synchronizacja
nazwy package'ow z malej litery


 */





public class Main{

    public static void main(String[] args) {
        System.out.println("Czy chcesz wczytać stan aplikacji?");
        System.out.println("1. Tak");
        System.out.println("2. Nie");

        int kod = UI.s.nextInt();

        if(kod == 1){
            UI.read();
        } else {
            Date.setTime(LocalDate.now());
            Magazyn magazyn1 = new Magazyn(50);

            Thread t1 = new Thread(() -> {
                while(!Thread.currentThread().isInterrupted()) {
                    Date.addDate();
                    magazyn1.check();
                }
            });

            Port port = new Port(magazyn1, new Pociag(), t1);

            UI.setPort(port);

            Nadawca n1 = new Nadawca("Jan", "Kowalski", "02312107056");
            Nadawca n2 = new Nadawca("Jane", "Doe", "00311751868");
            Nadawca n3 = new Nadawca("Anna", "Zalewska", "64070981526");
            Nadawca n4 = new Nadawca("Kazimierz", "Polak", "93013047337");
            Nadawca n5 = new Nadawca("Włodzimierz", "Lewandowski", "89121291858");

            UI.addNadawca(n1);
            UI.addNadawca(n2);
            UI.addNadawca(n3);
            UI.addNadawca(n4);
            UI.addNadawca(n5);

            try {
                port.getMagazyn().add(new KontWybuchowy(
                        n2, 950, 1200, 1234,
                        "brak", 3
                ));
                port.getMagazyn().add(new KontWybuchowy(
                        n4, 900, 1100, 5598,
                        "brak", 5
                ));
                port.getMagazyn().add(new KontToksCiekly(
                        n5, 750, 900, 9230,
                        "klamry mocujące", 230, 6.5
                ));
                port.getMagazyn().add(new KontPodstawowy(
                        n3, 650, 800, 9921
                ));


            } catch (MagazynOverloadedException | IrresponsibleSenderWithDangerousGoods e) {
                e.printStackTrace();
            }

            try {

                port.getPociag().addKont(new KontChlodniczy(
                        n1, 900, 1200, 5555,
                        "klatka Faradaya", 350
                ));
                port.getPociag().addKont(new KontToksCiekly(
                        n4, 800, 950, 4321,
                        "klamry mocujące, ubranie chroniące przed substancjami toksycznymi", 4.5, 7.8
                ));
            } catch (OverloadedException e) {
                e.printStackTrace();
            }

            Statek s1 = new Statek(
                    "Czarna bandera", "Sopot",
                    40_000, 20, 10, 3, 5
            );
            Statek s2 = new Statek(
                    "Sokole oko", "Gdańsk",
                    45_000, 25, 15, 8, 5
            );
            Statek s3 = new Statek(
                    "Barbarossa", "Szanghaj",
                    20_000, 15, 5, 2, 2
            );
            Statek s4 = new Statek(
                    "Syrenka", "Singapur",
                    50_000, 30, 15, 10, 5
            );

            port.addStatek(s1);
            port.addStatek(s2);
            port.addStatek(s3);
            port.addStatek(s4);

            try {
                s1.addKont(new KontChlodniczy(
                        n5, 900, 1000, 3219,
                        "klatka Faradaya", 450
                ));

                s1.addKont(new KontWybuchowy(
                        n1, 1000, 1200, 8877,
                        "brak", 8
                ));

                s1.addKont(new KontCiezki(
                        n2, 1800, 2100, 5515, "bloki mocujące"
                ));



                s2.addKont(new KontChlodniczy(
                        n3, 750, 850, 4401,
                        "klatka Faradaya, obuwie gumowe", 230
                ));

                s2.addKont(new KontWybuchowy(
                        n4, 1200, 1400, 9905,
                        "brak", 4
                ));




                s3.addKont(new KontWybuchowy(
                        n3, 1100, 1300, 7590,
                        "klamry mocujące", 5
                ));
                s3.addKont(new KontWybuchowy(
                        n5, 1000, 1100, 3421,
                        "brak", 2
                ));



                s4.addKont(new KontChlodniczy(
                        n2, 900, 1000, 8890,
                        "klatka Faradaya", 450
                ));

            } catch (OverloadedException e) {
                e.printStackTrace();
            }

        }

        UI.start();

    }
}
