package quicksort;

//import java.util.Arrays;

public class QuickSort extends Thread {
    private Comparable[] mas;
    private int threadsCount;
    private int left;
    private int right;
    private Thread[] threads;

    public QuickSort(Comparable[] mas, int threadsCount, int left, int right) {
        this.mas = mas;
        this.threadsCount = threadsCount;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        sort(left, right, threadsCount);
    }

    private void swap(Comparable[] mas, int i, int j) {
        Comparable tmp = mas[i];
        mas[i] = mas[j];
        mas[j] = tmp;
    }

    private int partition(int left, int right) {
        Comparable pivot = mas[right];
        int j = left;
        for (int i = left; i < right; i++) {
            if (mas[i].compareTo(pivot) < 0) {
                j++;
                swap(mas, j - 1, i);  
            }
        }
        if (j < mas.length) {
            swap(mas, j, right);
        }
        return j;
    }

    public void quickSort() {
        threads = new Thread[threadsCount];
        
        int index = 0;
        for(int i = 0; i < threadsCount - 1; i++) {
            index = partition(left, right);
            threads[i] = new QuickSort(mas, threadsCount, left, index - 1);
            threads[i].start();
            left = index + 1;
            //System.out.println(Arrays.toString(mas));
        }
        
        threads[threadsCount - 1] = new QuickSort(mas, threadsCount, index, right);
        threads[threadsCount - 1].start();
    
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sort(int left, int right, int threadsCount) {
        if (left < right) {
            int index = partition(left, right);
    
            if (left < index) {
                sort(left, index - 1, threadsCount);
            }
            if (index + 1 < right) {
                sort(index + 1, right, threadsCount);
            }
        }
    }
}