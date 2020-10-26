package permutation;

import java.util.Arrays;

public class PermutationDup {
    public int count = 0;
    private boolean debug;

    public void permutation(String s, int n, boolean debug) {
        char[] original = s.toCharArray();
        Arrays.sort(original);
        char[] clone = new char[s.length()];
        boolean[] mark = new boolean[s.length()];
        this.debug = debug;
        Arrays.fill(mark, false);
        permute(original, clone, mark, 0, n);
    }

    private void permute(char[] original, char[] clone, boolean[] mark, int length, int n) {
        if (length == n) {
            if (debug) {
                System.out.println(clone);
            }
            count++;
            return;
        }

        for (int i = 0; i < original.length; i++) {
            if (mark[i]) continue;
            // dont use this state. to keep order of duplicate character
            if (i > 0 && original[i] == original[i - 1] && !mark[i - 1]) continue;
            mark[i] = true;
            clone[length] = original[i];
            permute(original, clone, mark, length + 1, n);
            mark[i] = false;
        }
    }
}