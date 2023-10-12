package indl.lixn.lx7xl.juc;

/**
 * @author listen
 **/
public class EnumTest {

    public static void main(String[] args) {
        MyEnum me = MyEnum.A;

        Class<? super MyEnum> mec = MyEnum.class;
        while (mec != null) {
            System.out.println(mec.getName());
            mec = mec.getSuperclass();
        }

    }

}
