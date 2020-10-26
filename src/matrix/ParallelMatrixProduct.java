package matrix;

public class ParallelMatrixProduct extends Thread {

    private int beginRow, finishRow;
    private UsualMatrix a, b, res;
    private int secondRow;
    private int threadsCount;
    private static Thread[] threads;

    public ParallelMatrixProduct(int threadsCount, UsualMatrix a, UsualMatrix b, UsualMatrix res, int beginRow, int finishRow) {
        this.a = a;
        this.b = b;
        this.res = res;
        this.beginRow = beginRow;
        this.finishRow = finishRow;
        this.secondRow = b.getRow();
        this.threadsCount = threadsCount;
    }

    @Override
    public void run() {
            for (int row = beginRow; row <= finishRow; row++) {
                for (int i = 0; i < secondRow; i++) {
                    for (int column = 0; column < res.getColumn(); column++) {
                        res.setElement(row, column, res.getElement(row, column) + a.getElement(row, i) * b.getElement(i, column));
                    }
//                    if (!isInterrupted()) {
//                    } else {
//                        throw new InterruptedException();
//                    }
                }
            }
    }

    public static void stop(boolean stop, int threadNumber) {
        if (stop)
            threads[threadNumber].interrupt();
    }

    public static UsualMatrix multiply(UsualMatrix a, UsualMatrix b, int threadsCount) {
        if (a.getColumn() != b.getRow()) {
            throw new MatrixException("ProductError! Incorrect sizes of matrixes!\n");
        }

        UsualMatrix res = new UsualMatrix(a.getRow(), b.getColumn());

        int rowsCount = a.getRow() / threadsCount;
        int remaind = a.getRow() % threadsCount;
        threads = new Thread[threadsCount];
        int begin = 0, countWithRemaind = 0;
        for (int i = 0; i < threadsCount; i++) {
            if (i == 0) {
                countWithRemaind = rowsCount + remaind;
            } else {
                countWithRemaind = rowsCount;
            }
            threads[i] = new ParallelMatrixProduct(threadsCount, a, b, res, begin, begin + countWithRemaind - 1);
            begin += countWithRemaind;
            threads[i].start();
            /*try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop(true, 0);*/
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

//    private int getValue(int row, int column) {
//
//    }
}