package nl.marcusink.mmo.server.utils;


import java.util.Date;

public class log {

    public static void E(String log){
        System.out.println(whatIsTheTime() +" ERROR: " + log);
    }
    public static void I(String log){
        System.out.println(whatIsTheTime() +" INFO: " + log);
    }
    public static void W(String log){
        System.out.println(whatIsTheTime() +" WARN: " + log);
    }

    private static String whatIsTheTime(){
        return new Date().toLocaleString() ;
    }
}
