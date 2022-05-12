package Utils;

public class Utilities {

    public static boolean validPPS(String text) {
        if((text.length()==9))
            return (text.substring(0,7).matches("[0-9]+"))&&(text.substring(7,9).matches("[a-zA-Z]+"));
           else
            return false;

    }
    public static boolean validBoothID(String text){
        if(text.length()==2)
        return Character.isLetter(text.charAt(0)) && Character.isDigit(text.charAt(1));
        else
            return false;

    }


    public static boolean max10Chars(String string){ return string.length() <= 10;}

    public static boolean validIndex(int index, CoolLinkedList list) {
        return ((index >= 0) && (index < list.size()));
    }




}