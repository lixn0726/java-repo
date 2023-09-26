package indl.lixn.lx7xl.data_structure;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * @author listen
 * 双向链表：
 * 头节点：prev == null
 * 尾节点：next == null
 **/
public class LxLinkedList<E> extends AbstractSequentialList<E>
        implements Deque<E>, List<E>, Serializable {

    private static final Logger log = LoggerFactory.getLogger(LxLinkedList.class);

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

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    // everytime we want to get an element by iterator
    // use checkModification ensure will never be modified after creating Iterator
    private class ListItr implements ListIterator<E> {

        private Node<E> lastReturned; // 上一次作为API结果被返回的Node
        private Node<E> next; // next，下一个节点
        private int nextIndex; // 下一个（从左往右）节点的index
        private int expectedModCount = modCount; // 创建的时候进行赋值，如果LinkedList导致modCount变化，当前Itr失效

        ListItr(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            // 居然是这么判断的
            return nextIndex < size;
        }

        @Override
        public E next() {
            checkForModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.value;
        }

        final void checkForModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasPrevious() {
            // 下一个下标如果大于0，那么至少可以访问到head
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            checkForModification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            // ?
            lastReturned = next = (next == null) ? tail : next.prev;
            nextIndex--;
            return lastReturned.value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            checkForModification();
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
            // if the modification is operated by itself
            // add the variables
            expectedModCount++;
        }

        @Override
        public void set(E e) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            checkForModification();
            lastReturned.value = e;
        }

        @Override
        public void add(E e) {

        }
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
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        final E element = head.value;
        final Node<E> next = head.next;
        head.value = null;
        head.next = null; // help gc
        head = next;
        if (next == null) {
            tail = null;
        } else {
            // head的prev为null
            next.prev = null;
        }
        size--;
        modCount++;
        return element;
    }

    @Override
    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        final E element = tail.value;
        final Node<E> prev = tail.prev;
        tail.value = null;
        tail.prev = null;
        tail = prev;
        if (prev == null) {
            head = null;
        } else {
            // tail的next为null
            prev.next = null;
        }
        size--;
        modCount++;
        return element;
    }

    /**
     * unlinks non-null node x.
     */
    // 默认为public方法
    E unlink(Node<E> x) {
        final E element = x.value;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        // may be head
        if (prev == null) {
            log.info("Unlinking node [head]");
            head = next;
        } else {
            prev.next = next;
            // why should we execute this statement
            x.prev = null;
        }
        // may be tail
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.prev = null;
        }
        // can this help gc ?
        x.value = null;
        size--;
        modCount++;
        return element;
    }

    // returns the index of the first occurrence of the specified element in this list.
    // Or -1 if the list does not contain the element.
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            // replace for while();
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.value == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.value)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        // That's the key
        int index = size;
        if (o == null) {
            for (Node<E> x = tail; x != null; x = x.prev) {
                index--;
                if (x.value == null) {
                    return index;
                }
            }
        } else {
            for (Node<E> x = tail; x != null; x = x.prev) {
                index--;
                if (o.equals(x.value)) {
                    return index;
                }
            }
        }
        // better to throw NoSuchElementException
        return -1;
    }

    // poll will remove the element
    @Override
    public E pollFirst() {
        final Node<E> first = head;
        return first == null ? null : unlink(first);
    }

    @Override
    public E pollLast() {
        final Node<E> last = tail;
        return last == null ? null : unlink(last);
    }

    @Override
    public E getFirst() {
        final Node<E> l = head;
        if (l == null) {
            throw new NoSuchElementException();
        }
        return l.value;
    }

    @Override
    public E getLast() {
        final Node<E> l = tail;
        if (l == null) {
            throw new NoSuchElementException();
        }
        return l.value;
    }

    @Override
    public E peekFirst() {
        final Node<E> first = head;
        return (first == null) ? null : first.value;
    }

    @Override
    public E peekLast() {
        final Node<E> last = tail;
        return (last == null) ? null : last.value;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // That's the key
        int index = size;
        if (o == null) {
            for (Node<E> x = tail; x != null; x = x.prev) {
                if (x.value == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = tail; x != null; x = x.prev) {
                index--;
                if (o.equals(x.value)) {
                    unlink(x);
                    return true;
                }
            }
        }
        // better to throw NoSuchElementException ?
        return false;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        final Node<E> first = head;
        return first == null ? null : unlink(first);
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        final Node<E> f = head;
        return f == null ? null : f.value;
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return getFirst();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
