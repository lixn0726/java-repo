package indl.lixn.lx7xl.simple_test;

import java.util.Arrays;

/**
 * @author listen
 **/
public class StringSplit {

    public static void main(String[] args) {
        String s = "a.b.c";
        String[] ss = s.split(".");
        System.out.println(Arrays.asList(ss));
    }

}
