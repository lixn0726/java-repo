package indl.lixn.lx7xl.jvm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author listen
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    private String name;

    public static void main(String[] args) {
//        Dog aDog = new Dog("Max");
//        Dog oldDog = aDog;
//        // pass the object to foo
//        foo(aDog);
//
//        // aDog variable is still pointing to the "Max" dog when foo() returns
//        System.out.println("aDog name is Max: " + aDog.getName().equals("Max"));
//        System.out.println("aDog name is Fifi: " + aDog.getName().equals("Fifi"));
//        System.out.println(aDog == oldDog);

        List<String> list = new ArrayList<>();
        list.add("Not");
        list.add("Change");
        list.add("Yet");
        System.out.println(list);
        changeListElement(list);
        System.out.println(list);

    }

    public static void foo(Dog d) {
        System.out.println("d name is Max: " + d.getName().equals("Max"));
        // change d inside of foo() to point to a new Dog instance "Fifi"
        d = new Dog("Fifi");
        System.out.println("d name is Fifi: " + d.getName().equals("Fifi"));
    }

    public static void changeListElement(List<String> strL) {
        strL.clear();;
        strL.add("I");
        strL.add("am");
        strL.add("changed");
    }

}
