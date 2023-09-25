package indl.lixn.lx7xl.data_structure;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

/**
 * @author listen
 **/
public class LxLinkedList<E> extends AbstractSequentialList<E>
        implements Deque<E>, List<E>, Serializable {

    public static void main(String[] args) {
        List<String> sl = new ArrayList<String>() {{
            add("a");
            add("b");
            add("c");
        }};

        LxLinkedList<String> scll = new LxLinkedList<>(sl);
        scll.addAll(2, sl);

        scll.showElements();
//        scll.addFirst("negative-infinity");
//        scll.addLast("positive-infinity");
    }

    private int size = 0;

    private Node<E> head;

    private Node<E> tail;

    private int modCount = 0;

    public LxLinkedList() {
    }

    public LxLinkedList(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return;
        }
        addAll(size, c);
    }

    private void showElements() {
        Node<E> tHead = head;
        while (tHead != null) {
            System.out.println(tHead.value);
            System.out.println("-=-=-=-");
            tHead = tHead.next;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(this.size, c);
    }

    // true: make modification false: do nothing
    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPosition(index);
//         这里有一个效率问题，两种类型转换
        // 1. for (E e : (E[])oa)
        // 2. 如下
        // 哪种效率高呢？
        /*
        如果要对整个数组进行相同的类型转换操作，那么一次性将整个数组的类型进行转换的销量更高
        而在JDK的源码中，也是采用循环中逐个转换的方法，不知道是为什么
        todo
         */
        E[] ea = (E[]) c.toArray();
        int numNew = ea.length;
        Node<E> virtualHead, real;
        if (index == size) {
            real = null;
            virtualHead = tail;
        } else {
            // That's where real is not null.
            real = node(index);
            virtualHead = real.prev;
        }
        // never move the [real] node ref. It's just for getting the
        for (E e : ea) {
            // next is null. Because newNode means the latest node.
            // prev is virtualHead. Because virtualHead is the previous node of the newNode that
            //  will be inserted to linkedList.
            Node<E> newNode = new Node<>(e, virtualHead, null);
            if (virtualHead == null) {
                // means that there is no element in this linkedList
                // straight set head value
                head = newNode;
            } else {
                // means that there are element already in this linkedList
                // virtualHead is the last remaining node
                virtualHead.next = newNode;
            }
            virtualHead = newNode;
        }
        // That's where i forget to write
        // If real is null, means this addAll method add from the last.
        // so just mark tail.
        if (real == null) {
            tail = virtualHead;
        } else {
            // TODO Why ???
            // That, because the addAll method will not delete the old element
            // Just a insert. So there is to joint the last(剩余的) ndoes.
            virtualHead.next = real;
            real.prev = virtualHead;
        }
        size += numNew;
        modCount++;
        return true;
    }


    private void checkPosition(int p) {
        if (!positionInRange(p)) {
            throw new IndexOutOfBoundsException("Position:" + p + ", Size:" + this.size);
        }
    }

    private boolean positionInRange(int position) {
        return position >= 0 && position <= this.size;
    }

    // There is a mistake i think in JDK.
    // The doc says that returns the element at the specified position in the list
    // But returns the element at the value (arg - 1) of index.
    /**
     * 返回某个位置的元素
     *
     * @param index 位置
     * @return 实际位置的范围是 [0, size)
     */
    public Node<E> node(int index) {
        // index靠近 head
        Node<E> res;
        if (index < (size >> 1)) {
            res = head;
            for (int i = 0; i < index; i++) {
                res = res.next;
            }
        } else {
            // 不然就是靠近 tail
            res = tail;
            for (int i = size - 1; i > index; i--) {
                res = res.prev;
            }
        }
        return res;
    }

    @Getter
    @Setter
    private static class Node<E> {

        private E value;

        private Node<E> prev;

        private Node<E> next;

        public Node(E value) {
            this(value, null, null);
        }

        public Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private class LinkedListIterator<E> implements Iterator<E> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public void addFirst(E e) {
        final Node<E> f = head;
        final Node<E> newNode = new Node<>(e, null, f);
        head = newNode;
        // before add first node, it's an empty linkedList
        if (f == null) {
            tail = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public void addLast(E e) {
        // hold the reference
        final Node<E> t = tail;
        // allocate memory to create object
        final Node<E> newNode = new Node<>(e, t, null);
        tail = newNode;
        // because t is unmodifiable
        // means that before add this value, it's an empty linkedList
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        return null;
    }

    @Override
    public E pollLast() {
        return null;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
