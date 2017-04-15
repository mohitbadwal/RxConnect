package mohitbadwal.rxconnect;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by JohnConnor on 15-Apr-17.
 */

public class RequestQueue<T> implements Queue {

    ArrayList<T> queue;
    public RequestQueue()
    {
        queue=new ArrayList<>();
    }
    @Override
    public boolean add(Object o) {
        if(o==null)
            return false;
        queue.add((T)o);
        return true;
    }


    @Override
    public boolean addAll(Collection collection) {
        return false;
    }


    @Override
    public void clear() {
        queue.clear();
    }


    @Override
    public boolean contains(Object object) {
        return false;
    }


    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }


    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }


    @Override
    public boolean remove(Object object) {
        return false;
    }


    @Override
    public int size() {
        return queue.size();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }


    @NonNull
    @Override
    public Object[] toArray(Object[] array) {
        return new Object[0];
    }


    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }


    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }


    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }


    @Override
    public boolean offer(Object o) {
        if(o==null)
            return false;
        queue.add((T)o);
        return true;
    }


    @Override
    public Object remove() {
      return queue.remove(0);
    }


    @Override
    public Object poll() {
        return queue.get(0);
    }


    @Override
    public Object element() {
        return null;
    }


    @Override
    public Object peek() {
        return null;
    }
}
