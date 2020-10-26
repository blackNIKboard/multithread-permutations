package matrix;
import java.lang.RuntimeException;

public class MatrixException extends RuntimeException {
    private static final long serialVersionUID = 1234L;
    private String str;
    
    public MatrixException(final String str) {
        this.str = str;
    }
    
    @Override
    public final String getMessage() {
        return this.str;
    }
}