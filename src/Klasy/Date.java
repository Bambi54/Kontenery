package Klasy;

import java.time.LocalDate;

public class Date{

    private static LocalDate time;

    public static LocalDate getTime(){
        return time;
    }

    public static void setTime(LocalDate time){
        Date.time = time;
    }


    public static void addDate() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        time = time.plusDays(1);

    }


}
