package main;

import matrix.*;
import permutation.*;


public class Main {
    public static void main(String[] args) {
        // Usual and multithreading comparison
        runPermutations();
    }

    private static void runPermutations() {
        //  ********************
        //  *    Test values   *
        //  ********************
        var test1 = "abc";
        var test2 = "abcde";
        var test3 = "qwertyuiopasdfghjk1234567890";
        var selected = test3;
        var length = 5;
        var threads = 2;
        var debug = false;
        //  ********************

        PermutationDup p = new PermutationDup();
        long begin1 = System.nanoTime();
        p.permutation(selected, length, debug);
        var all1 = (System.nanoTime() - begin1) / 1e8;
        System.out.println(p.count);
        System.out.println("-------");
        long begin2 = System.nanoTime();
        ParallelPermute.permute(selected, length, threads, debug);
        var all2 = (System.nanoTime() - begin2) / 1e8;
        System.out.println(ParallelPermute.count);

        System.out.print(all1 + " vs ");
        System.out.println(all2);
    }

    private static void runMatrix() {
        int size = 500;
        UsualMatrix a = new UsualMatrix(size, size);
        UsualMatrix b = new UsualMatrix(size, size);

        // Different thread count testing
//        long[] times = new long[10];
//
//        for (int i = 1; i < 10; i++) {
//            long start = System.nanoTime();
//            UsualMatrix c = ParallelMatrixProduct.multiply(a, b, i);
//            times[i] = System.nanoTime() - start;
//        }
//
//        for (int i = 1; i < 10; i++) {
//            System.out.println(times[i]);
//        }
//        System.out.println(indexOfSmallest(times, 1));

        long begin1 = System.nanoTime();
        UsualMatrix c = ParallelMatrixProduct.multiply(a, b, 4);
        var all1 = (System.nanoTime() - begin1) / 1e9;

        long begin2 = System.nanoTime();
        UsualMatrix d = a.product(b);
        var all2 = (System.nanoTime() - begin2) / 1e9;

        System.out.println(c.equals(d));
//        System.out.println("A:\n" + a);
//        System.out.println("B:\n" + b);
//        System.out.println("Res:\n" + c);

        System.out.println("Threads product time: \n" + all1);
        System.out.println("Usual product time: \n" + all2);
    }

    public static int indexOfSmallest(long[] array, int start) {
        // add this
        if (array.length == 0)
            return -1;

        int index = start;
        long min = array[index];

        for (int i = 1; i < array.length; i++) {
            if (array[i] <= min) {
                min = array[i];
                index = i;
            }
        }

        return index;
    }
}