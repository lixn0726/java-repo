package indl.lixn.lx7xl.juc;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author listen
 **/
public class AtomicStampedRefUsage {

    public static void main(String[] args) {
        StringRefsWrapper wrapper = new StringRefsWrapper("a", "b");
        StringRefsWrapper changed = new StringRefsWrapper("b", "c");
        AtomicStampedReference<StringRefsWrapper> wrapperAtomicRef = new AtomicStampedReference<>(wrapper, 1);
        System.out.println(wrapperAtomicRef.compareAndSet(wrapper, changed, 1, 2));
        System.out.println(wrapperAtomicRef.compareAndSet(changed, changed, 2, 3));
        System.out.println(wrapper.equals(wrapperAtomicRef.get(new int[] {Integer.MAX_VALUE})));
    }

    static final class StringRefsWrapper {
        String ref1;
        String ref2;

        public StringRefsWrapper(String ref1, String ref2) {
            this.ref1 = ref1;
            this.ref2 = ref2;
        }

        public String getRef1() {
            return ref1;
        }

        public void setRef1(String ref1) {
            this.ref1 = ref1;
        }

        public String getRef2() {
            return ref2;
        }

        public void setRef2(String ref2) {
            this.ref2 = ref2;
        }
    }

}
