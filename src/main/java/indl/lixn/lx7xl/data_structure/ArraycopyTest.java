package indl.lixn.lx7xl.data_structure;

/**
 * @author listen
 **/
public class ArraycopyTest {

    public static void main(String[] args) {
        int[] a = new int[10];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 3;
        /*
       native
       0: src
       1: srcPos
       2: dest
       3: destPos

       4: length
         */
        // 将a数组从3开始，后面的元素复制到a的3开始，复制的长度为3
        System.arraycopy(a, 3, a, 3, 3);
        a[2] = 99;
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

}
