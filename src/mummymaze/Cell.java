package mummymaze;

public class Cell {
    private int column;
    private int line;

    public Cell(int line, int column) {
        this.column = column;
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Cell clone() {
        return new Cell(this.getLine(), this.getColumn());
    }

    public boolean equals(Cell cell) {
        if (cell == null) {
            return false;
        }

        return this.getLine() == cell.getLine() && this.getColumn() == cell.getColumn();

    }
}
