package Klasy;

public class IncorrectIDException extends Exception{

    public IncorrectIDException(){
        super("Niepoprawne ID kontenera.");
    }
    public IncorrectIDException(String s){
        super(s);
    }
}
