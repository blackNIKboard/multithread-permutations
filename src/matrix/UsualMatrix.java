package matrix;
import java.util.Random;

public class UsualMatrix {
    private volatile int[][] mtx;
    private volatile int row;
    private volatile int column;

    public UsualMatrix(int row, int column) {
        if (row <= 0 || column <= 0) {
            throw new MatrixException("ConstructorError! Invalid size value!\n");
        }
        this.row = row;
        this.column = column;
        mtx = new int[row][column];
        randFillMatrix();
    }

    public UsualMatrix(int row, int column, boolean rand) {
        if (row <= 0 || column <= 0) {
            throw new MatrixException("ConstructorError! Invalid size value!\n");
        }
        this.row = row;
        this.column = column;
        mtx = new int[row][column];
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setElement(int row, int column, int value) {
        if (row < 0 || column < 0 || row >= getRow() || column >= getColumn()) {
            throw new MatrixException("SetError! Out of bound!\n");
        }
        mtx[row][column] = value;
    }

    public int getElement(int row, int column) {
        if (row < 0 || column < 0 || row >= getRow() || column >= getColumn()) {
            throw new MatrixException("GetError! out of bound!\n");
        }
        return mtx[row][column];
    }

    public void randFillMatrix() {
        Random rand = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mtx[i][j] = rand.nextInt() % 10;
            }
        }
    }

    public UsualMatrix product(final UsualMatrix mt) {
        if (column != mt.row) {
            throw new MatrixException("ProductError! Incorrect sizes of matrixes!\n");
        }
        UsualMatrix res = new UsualMatrix(row, mt.column, false);
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < mt.row; k++) {
                for (int j = 0; j < mt.column; j++) {
                    res.mtx[i][j] += mtx[i][k] * mt.mtx[k][j];
                }
            }
        }
        return res;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                str.append(this.mtx[i][j] + " ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean equals(Object ob) {
        if (this == ob)
            return true;
        if (ob == null)
            return false;
        if (!(ob instanceof UsualMatrix))
            return false;
        UsualMatrix mt = (UsualMatrix) ob;
        if (this.row != mt.row || this.column != mt.column)
            return false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.mtx[i][j] != mt.mtx[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}