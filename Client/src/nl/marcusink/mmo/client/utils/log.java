package nl.marcusink.mmo.client.utils;


import java.util.Date;

public class log {

    private static int level = -1;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void E(String log){
        if( level < 4 ) System.out.println(ANSI_RED + whatIsTheTime() +" ERROR: " + log + ANSI_RESET);
    }

    public static void W(String log){
        if( level < 3 ) System.out.println(ANSI_PURPLE + whatIsTheTime() +" WARN: " + log + ANSI_RESET);
    }

    public static void I(String log){
        if( level < 2 ) System.out.println(ANSI_BLACK + whatIsTheTime() +" INFO: " + log + ANSI_RESET);
    }

    public static void D(String log){
        if( level < 1 ) System.out.println(ANSI_GREEN + whatIsTheTime() +" DEBUG: " + log + ANSI_RESET);
    }

    public static void T(String log){
         if( level < 0 ) System.out.println(ANSI_BLUE + whatIsTheTime() +" TRACE: " + log + ANSI_RESET);
    }

    private static String whatIsTheTime(){
        return new Date().toLocaleString() ;
    }
}
