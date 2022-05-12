package Utils;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class coolLinkedListTest {
    public CoolLinkedList<String> list = new CoolLinkedList<>();
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        for(int i = 0;i<100000;++i){
            String add = RandomString.getAlphaNumericString(10);
            list.add(add);
        }
        System.out.println("Done Setup");
    }

    @org.junit.jupiter.api.Test
    void add() {

        assertTrue(list.contains("Hello0"));
        assertTrue(list.contains("Hello9"));
        assertFalse(list.contains("Hello11"));
    }

    @org.junit.jupiter.api.Test
    void get() {
        assertEquals("Hello0",list.get(0));
        assertEquals("Hello3",list.get(3));
        assertEquals("Hello4",list.get(4));
        assertEquals("Hello5",list.get(5));
        assertEquals("Hello6",list.get(6));
        assertEquals("Hello8",list.get(8));
        assertEquals("Hello9",list.get(9));

    }
    @Test
    void delete() {
        assertEquals("Hello0",list.get(0));
        list.remove(0);
        assertNotEquals("Hello0",list.get(0));
        assertEquals("Hello5",list.get(4));
        list.remove(4);
        assertNotEquals("Hello5",list.get(4));
        assertEquals("Hello9",list.get(7));
        list.remove(7);
        assertNotEquals("Hello9",list.get(7));

    }
    @org.junit.jupiter.api.Test
    void contains() {

        assertTrue(list.contains("Hello5"));}

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }
    @Test
    void replace() {
        assertEquals("Hello3", list.get(3));
        list.set(3, "New!");
        assertNotEquals("Hello3", list.get(3));
        assertEquals("New!", list.get(3));

        assertEquals("Hello7", list.get(7));
        list.set(7, "New!");
        assertNotEquals("Hello7", list.get(7));
        assertEquals("New!", list.get(7));


    }

    @Test
    void sort(){
        list.mergeSort(Comparator.comparing(String::toString));
        System.out.println("--Sorted--");
        for(String word:list)
            System.out.println(word);
    }

    public class RandomString {

        // function to generate a random string of length n
        static String getAlphaNumericString(int n)
        {

            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }

        public static void main(String[] args)
        {

            // Get the size n
            int n = 20;

            // Get and display the alphanumeric string
            System.out.println(RandomString
                    .getAlphaNumericString(n));
        }
    }
}