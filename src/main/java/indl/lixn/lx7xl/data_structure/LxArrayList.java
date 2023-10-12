package indl.lixn.lx7xl.data_structure;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/**
 * @author listen
 *
 * resizable-array implementation of the List interface.
 * implements all optional list operation, and permits all elements, including null
 * this class provides methods to manipulate the size of the array that is used internally to store the list.
 * this class is roughly(粗略的) equivalent to Vector, except that it's unsynchronized.
 * and ...
 * Ths
 *      size
 *      isEmpty
 *      get
 *      set
 *      iterator
 *      listIterator
 * operations run in constant time.
 * The
 *      add
 * operation runs in amortized constant time.
 **/
public class LxArrayList<E> extends AbstractList<E>
        implements RandomAccess, List<E>, Serializable {

    private int size;

    private Object[] table;



    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
