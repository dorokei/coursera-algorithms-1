/* *****************************************************************************
 *  Name: Keita Ono
 *  Date: 2020/06/10
 *  Description: Cousera homework
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;
    private int dequedItemCount;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n - dequedItemCount;
    }

    private void resize(int size) {
        // System.out.println("Start resize, size:" + size);
        Item[] copy = (Item[]) new Object[size];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (items[i] != null) copy[j++] = items[i];
        }
        n = j;
        dequedItemCount = 0;
        items = copy;

        // System.out.println("After resize, n:" + n);
        // itemsCheck();
    }

    /*
    private void itemsCheck() {
        for (int i = 0; i < n; i++) {
            System.out.println("items " + i + ": " + items[i]);
        }
    }*/

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (items.length == n) resize(2 * items.length);
        items[n++] = item;

        // System.out.println("Added: " + item);
        // itemsCheck();
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        // System.out.println("n: " + n + ", dequedItemCount: " + dequedItemCount);
        if (n > 0 && (n - dequedItemCount) <= n / 2) resize(items.length);
        // System.out.println("AFTER n: " + n + ", dequedItemCount: " + dequedItemCount);
        // itemsCheck();

        Item item = null;
        while (item == null) {
            int index = StdRandom.uniform(n);
            item = items[index];
            items[index] = null;
        }
        dequedItemCount++;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        if (n > 0 && (n - dequedItemCount) <= n / 2) resize(items.length);

        Item item = null;
        while (item == null) {
            int index = StdRandom.uniform(n);
            item = items[index];
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator(items, size());
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] items;
        private int[] indice;
        private int m;

        public ListIterator(Item[] originals, int size) {
            items = (Item[]) new Object[size];
            indice = new int[size];
            int j = 0;
            for (int i = 0; i < originals.length; i++) {
                if (originals[i] != null) {
                    items[j] = originals[i];
                    indice[j] = j;
                    j++;
                }
            }
            // System.out.println("j: " + j);
            // for (int i : indice) {
            //     System.out.println("before i: " + i);
            // }
            StdRandom.shuffle(indice);
            // for (int i : indice) {
            //     System.out.println("after i: " + i);
            // }
            m = 0;
        }

        public boolean hasNext() {
            return m != indice.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = items[indice[m++]];
            // System.out.println("after m: " + m);
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        int n = 100;
        System.out.println(n + " times enqueue and dequeue");
        for (int i = 0; i < n; i++) {
            randomizedQueue.enqueue("A");
            System.out.println("size(" + (i + 1) + "): " + randomizedQueue.size());
        }

        for (int i = 0; i < n; i++) {
            randomizedQueue.dequeue();
            System.out.println("size(" + (100 - i - 1) + "): " + randomizedQueue.size());
        }

        assert randomizedQueue.isEmpty();
        int m = 5;
        for (int i = 0; i < m; i++) {
            randomizedQueue.enqueue("A" + i);
        }

        System.out.println("size: " + randomizedQueue.size());
        Iterator<String> stringIterator = randomizedQueue.iterator();

        int i = 1;
        while (stringIterator.hasNext()) {
            System.out.println(i + "th: " + stringIterator.next());
            i++;
        }
    }
}
