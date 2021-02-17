/* *****************************************************************************
 *  Name: Keita Ono
 *  Date: 2020/06/10
 *  Description: Cousera homework
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        // System.out.println("args[0]" + args[0]);
        int n = Integer.parseInt(args[0]);
        // String inputs = StdIn.readString();
        // System.out.println("inputs" + inputs);
        // String[] strings = new String[n];
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            // strings[i] = StdIn.readString();
            randomizedQueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < n; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
