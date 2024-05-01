import java.util.List;

public abstract class Piece {

    private int col;
    private int row;
    private PieceColor color;
    private PieceType type;

    public Piece() {

    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int col) {
        this.row = col;
    }

    public Piece(int col, int row, PieceColor color, PieceType type) {
        this.col = col;
        this.row = row;
        this.color = color;
        this.type = type;
    }

    public static Piece factory(PieceType pt, int col, int row, PieceColor color) {
        switch (pt) {
            case SPACE:
                return new Space(col, row);
            case POINT:
                return new Point(col, row, color);
            case SUN:
                return new Sun(col, row, color);
            case TIME:
                return new Time(col, row, color);
            case PLUS:
                return new Plus(col, row, color);
            case HOURGLASS:
                return new Hourglass(col, row, color);
            case FLIPPED_POINT:
                return new Point(col, row, color); 
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pt);
        }
    }

    public abstract List<Position> getValidMoves(Board board);

    public abstract boolean isValidMove(int toRow, int toCol);

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }



}
