package indl.lixn.lx7xl.simple_test;

/**
 * @author listen
 **/
public class ValueChangeAndGoIntoNextIf {

    public static void main(String[] args) {
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        int a = 3;
        int b = 4;
        a = b = 5;
        System.out.println(a);
        System.out.println(b);

    }

    private static class Node {
        private int prev;
        private int waitStatus;

        public static boolean compareAndSetWaitStatus(Node node, int expect, int update) {
            if (node.waitStatus == expect) {
                node.waitStatus = update;
                return true;
            }
            return false;
        }
    }

}
