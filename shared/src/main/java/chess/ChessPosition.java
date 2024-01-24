package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int row, col;
    int hashCode;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col;
    }
    public ChessPosition addPosition(int[] toAdd)
    {
        return new ChessPosition(row +toAdd[0],col +toAdd[1]);
    }

    @Override
    public String toString()
    {
//char col = '`';
//col += this.col;
//return Integer.toString(this.row)+col;
        return "{"+row+", "+col+"}";
    }

    @Override
    public int hashCode() {
        return this.hashCode;
//        return super.hashCode();
    }
}
