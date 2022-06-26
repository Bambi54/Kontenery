package Klasy;

import Kontenery.Kontener;

public class OverloadedException extends Exception{

    private final Kontener k;

    public OverloadedException(String s, Kontener k){
        super(s);
        this.k = k;
    }

    public Kontener zwrot(){
        return k;
    }
}
