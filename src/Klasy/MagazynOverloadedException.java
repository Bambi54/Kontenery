package Klasy;

import Kontenery.Kontener;

public class MagazynOverloadedException extends Exception implements Runnable{

    private final Kontener k;

    public MagazynOverloadedException(String s, Kontener k){
        super(s);
        this.k = k;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 6 && !Thread.currentThread().isInterrupted(); i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
            System.out.println("Pozostało: " + (30 - 5*i) +
                    " sekund na opróżnienie magazynu.");

        }

        System.out.println("Magazyn nie jest już pełny.");

    }
    public Kontener zwrot(){
        return k;
    }
}
