package indl.lixn.lx7xl.jvm;

/**
 * @author listen
 **/
public class FinalExample {

    // 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作不能重排序
    int i;// common field
    final int j; // final field
    static FinalExample obj;

    public FinalExample() {
        i = 1;
        j = 2;
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj;// 1.read obj reference
        int a = object.i;// 2.read common field
        int b = object.j;// 3.read final field
        // 1、3不能重排序
    }
}
