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
    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * <tt>true</tt> upon success and throwing an <tt>IllegalStateException</tt>
     * if no space is currently available.
     *
     * @param o the element to add
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     * @throws IllegalStateException    if the element cannot be added at this
     *                                  time due to capacity restrictions
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this queue
     * @throws NullPointerException     if the specified element is null and
     *                                  this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this queue
     */
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

    /**
     * Attempts to add all of the objects contained in {@code Collection}
     * to the contents of this {@code Collection} (optional). If the passed {@code Collection}
     * is changed during the process of adding elements to this {@code Collection}, the
     * behavior is not defined.
     *
     * @param collection the {@code Collection} of objects.
     * @return {@code true} if this {@code Collection} is modified, {@code false}
     * otherwise.
     * @throws UnsupportedOperationException if adding to this {@code Collection} is not supported.
     * @throws ClassCastException            if the class of an object is inappropriate for this
     *                                       {@code Collection}.
     * @throws IllegalArgumentException      if an object cannot be added to this {@code Collection}.
     * @throws NullPointerException          if {@code collection} is {@code null}, or if it
     *                                       contains {@code null} elements and this {@code Collection} does
     *                                       not support such elements.
     */
    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    /**
     * Removes all elements from this {@code Collection}, leaving it empty (optional).
     *
     * @throws UnsupportedOperationException if removing from this {@code Collection} is not supported.
     * @see #isEmpty
     * @see #size
     */
    @Override
    public void clear() {
        queue.clear();
    }

    /**
     * Tests whether this {@code Collection} contains the specified object. Returns
     * {@code true} if and only if at least one element {@code elem} in this
     * {@code Collection} meets following requirement:
     * {@code (object==null ? elem==null : object.equals(elem))}.
     *
     * @param object the object to search for.
     * @return {@code true} if object is an element of this {@code Collection},
     * {@code false} otherwise.
     * @throws ClassCastException   if the object to look for isn't of the correct
     *                              type.
     * @throws NullPointerException if the object to look for is {@code null} and this
     *                              {@code Collection} doesn't support {@code null} elements.
     */
    @Override
    public boolean contains(Object object) {
        return false;
    }

    /**
     * Returns if this {@code Collection} contains no elements.
     *
     * @return {@code true} if this {@code Collection} has no elements, {@code false}
     * otherwise.
     * @see #size
     */
    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }

    /**
     * Returns an instance of {@link Iterator} that may be used to access the
     * objects contained by this {@code Collection}. The order in which the elements are
     * returned by the iterator is not defined. Only if the instance of the
     * {@code Collection} has a defined order the elements are returned in that order.
     *
     * @return an iterator for accessing the {@code Collection} contents.
     */
    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    /**
     * Removes one instance of the specified object from this {@code Collection} if one
     * is contained (optional). The element {@code elem} that is removed
     * complies with {@code (object==null ? elem==null : object.equals(elem)}.
     *
     * @param object the object to remove.
     * @return {@code true} if this {@code Collection} is modified, {@code false}
     * otherwise.
     * @throws UnsupportedOperationException if removing from this {@code Collection} is not supported.
     * @throws ClassCastException            if the object passed is not of the correct type.
     * @throws NullPointerException          if {@code object} is {@code null} and this {@code Collection}
     *                                       doesn't support {@code null} elements.
     */
    @Override
    public boolean remove(Object object) {
        return false;
    }

    /**
     * Returns a count of how many objects this {@code Collection} contains.
     *
     * @return how many objects this {@code Collection} contains, or Integer.MAX_VALUE
     * if there are more than Integer.MAX_VALUE elements in this
     * {@code Collection}.
     */
    @Override
    public int size() {
        return queue.size();
    }

    /**
     * Returns a new array containing all elements contained in this {@code Collection}.
     * <p>
     * If the implementation has ordered elements it will return the element
     * array in the same order as an iterator would return them.
     * <p>
     * The array returned does not reflect any changes of the {@code Collection}. A new
     * array is created even if the underlying data structure is already an
     * array.
     *
     * @return an array of the elements from this {@code Collection}.
     */
    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Returns an array containing all elements contained in this {@code Collection}. If
     * the specified array is large enough to hold the elements, the specified
     * array is used, otherwise an array of the same type is created. If the
     * specified array is used and is larger than this {@code Collection}, the array
     * element following the {@code Collection} elements is set to null.
     * <p>
     * If the implementation has ordered elements it will return the element
     * array in the same order as an iterator would return them.
     * <p>
     * {@code toArray(new Object[0])} behaves exactly the same way as
     * {@code toArray()} does.
     *
     * @param array the array.
     * @return an array of the elements from this {@code Collection}.
     * @throws ArrayStoreException if the type of an element in this {@code Collection} cannot be
     *                             stored in the type of the specified array.
     */
    @NonNull
    @Override
    public Object[] toArray(Object[] array) {
        return new Object[0];
    }

    /**
     * Removes all objects from this {@code Collection} that are not also found in the
     * {@code Collection} passed (optional). After this method returns this {@code Collection}
     * will only contain elements that also can be found in the {@code Collection}
     * passed to this method.
     *
     * @param collection the collection of objects to retain.
     * @return {@code true} if this {@code Collection} is modified, {@code false}
     * otherwise.
     * @throws UnsupportedOperationException if removing from this {@code Collection} is not supported.
     * @throws ClassCastException            if one or more elements of {@code collection}
     *                                       isn't of the correct type.
     * @throws NullPointerException          if {@code collection} contains at least one
     *                                       {@code null} element and this {@code Collection} doesn't support
     *                                       {@code null} elements.
     * @throws NullPointerException          if {@code collection} is {@code null}.
     */
    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    /**
     * Removes all occurrences in this {@code Collection} of each object in the
     * specified {@code Collection} (optional). After this method returns none of the
     * elements in the passed {@code Collection} can be found in this {@code Collection}
     * anymore.
     *
     * @param collection the collection of objects to remove.
     * @return {@code true} if this {@code Collection} is modified, {@code false}
     * otherwise.
     * @throws UnsupportedOperationException if removing from this {@code Collection} is not supported.
     * @throws ClassCastException            if one or more elements of {@code collection}
     *                                       isn't of the correct type.
     * @throws NullPointerException          if {@code collection} contains at least one
     *                                       {@code null} element and this {@code Collection} doesn't support
     *                                       {@code null} elements.
     * @throws NullPointerException          if {@code collection} is {@code null}.
     */
    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    /**
     * Tests whether this {@code Collection} contains all objects contained in the
     * specified {@code Collection}. If an element {@code elem} is contained several
     * times in the specified {@code Collection}, the method returns {@code true} even
     * if {@code elem} is contained only once in this {@code Collection}.
     *
     * @param collection the collection of objects.
     * @return {@code true} if all objects in the specified {@code Collection} are
     * elements of this {@code Collection}, {@code false} otherwise.
     * @throws ClassCastException   if one or more elements of {@code collection} isn't of the
     *                              correct type.
     * @throws NullPointerException if {@code collection} contains at least one {@code null}
     *                              element and this {@code Collection} doesn't support {@code null}
     *                              elements.
     * @throws NullPointerException if {@code collection} is {@code null}.
     */
    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * @param o the element to add
     * @return <tt>true</tt> if the element was added to this queue, else
     * <tt>false</tt>
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this queue
     * @throws NullPointerException     if the specified element is null and
     *                                  this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this queue
     */
    @Override
    public boolean offer(Object o) {
        if(o==null)
            return false;
        queue.add((T)o);
        return true;
    }

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll poll} only in that it throws an exception if this
     * queue is empty.
     *
     * @return the head of this queue
     * @throws if this queue is empty
     */
    @Override
    public Object remove() {
      return queue.remove(0);
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns <tt>null</tt> if this queue is empty.
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
    @Override
    public Object poll() {
        return queue.get(0);
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws if this queue is empty
     */
    @Override
    public Object element() {
        return null;
    }

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns <tt>null</tt> if this queue is empty.
     *
     * @return the head of this queue, or <tt>null</tt> if this queue is empty
     */
    @Override
    public Object peek() {
        return null;
    }
}
