package permutation;

import java.util.Arrays;

public class ParallelPermute extends Thread {
    public static int count = 0;
    private final int n;
    private final char[] original;
    private final char[] clone;
    private final boolean[] mark;
    int start, end;
    private boolean debug;

    public ParallelPermute(char[] original, int n, int start, int end, boolean debug) {
        this.original = original;
        this.clone = new char[original.length];
        this.mark = new boolean[original.length];
        this.n = n;
        this.start = start;
        this.end = end;
        this.debug = debug;
    }

    public static void permute(String s, int n, int threadCount, boolean debug) {
        var sourceLength = s.length();
        int perThread = sourceLength / threadCount;
        int remains = sourceLength - perThread * threadCount;

        char[] original = s.toCharArray();
        Arrays.sort(original);
        Thread[] threads = new Thread[threadCount];
        var active = 0;
        for (int i = 0; i < sourceLength; i += perThread) {
            if (active == 0) {
                threads[active] = new ParallelPermute(original, n, i, i + perThread + remains, debug);
                i += remains;
            } else {
                threads[active] = new ParallelPermute(original, n, i, i + perThread, debug);
            }
            threads[active].start();
            active++;
        }
        try {
            for (Thread thread : threads) {
//                thread.join();
                thread.join(10000000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        generate(this.original, this.clone, this.mark, 0);
    }

    private void generate(char[] original, char[] clone, boolean[] mark, int length) {
        if (length == n) {
            if (debug) {
                System.out.println(clone);
            }
            count++;
            return;
        }

        for (int i = ((length == 0) ? start : 0); i < ((length == 0) ? end : original.length); i++) {
            if (mark[i]) continue;
            // dont use this state. to keep order of duplicate character
            if (i > 0 && original[i] == original[i - 1] && !mark[i - 1]) continue;
            mark[i] = true;
            clone[length] = original[i];
            generate(original, clone, mark, length + 1);
            mark[i] = false;
        }
    }
}

