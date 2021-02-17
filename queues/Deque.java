/* *****************************************************************************
 *  Name: Keita Ono
 *  Date: 2020/06/10
 *  Description: Cousera homework
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) oldFirst.prev = first;

        if (size() == 0) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.prev = oldLast;
        if (oldLast != null) oldLast.next = last;

        if (size() == 0) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");

        System.out.println("Get last: " + deque.removeLast());
        System.out.println("Get first: " + deque.removeFirst());
        System.out.println("Get first: " + deque.removeFirst());

        deque.addFirst("3");
        System.out.println("Get last: " + deque.removeLast());

        deque.addLast("3");
        System.out.println("Get last: " + deque.removeLast());

        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");
        deque.addFirst("6");

        for (String s : deque) {
            System.out.println("next: " + s);
        }
    }
}
