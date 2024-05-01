
import java.util.ArrayList;
import java.util.List;

public class Time extends Piece {

    public Time(int col, int row, PieceColor color) {
        super(col, row, color, PieceType.TIME);
    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        return toRow >= 0 && toRow < 6 && toCol >= 0 && toCol < 7;
    }

    @Override
    public List<Position> getValidMoves(Board board) {

        List<Position> validMoves = new ArrayList<>();
        Piece details = board.getPiece(super.getCol(), super.getRow());

        if (details == null) {
            return validMoves;
        }

        if (details.getType() == PieceType.TIME) {

            for (int i = super.getCol()+1, j = super.getRow()+1; i <= 6 && j <= 7; i++, j++) {
                if (isValidMove(i, j)&&isOpponentPiece(board, i, j,details.getColor())&&isPathClearFromBegin1(board,super.getCol(),super.getRow(),i,j)) {
                    validMoves.add(new Position(i, j));
                }

            }

            for (int i = super.getCol()-1, j = super.getRow()-1; i >= 0 && j >= 0; i--, j--) {
                if (isValidMove(i, j)&&isOpponentPiece(board, i, j,details.getColor())&&isPathClearFromEnd1(board,super.getCol(),super.getRow(),i,j)) {
                    validMoves.add(new Position(i, j));
                }

            }

            for (int i = super.getCol()-1, j = super.getRow()+1; i >= 0 && j <= 7; i--, j++) {
                if (isValidMove(i, j) &&isOpponentPiece(board, i, j,details.getColor())&&isPathClearFromEnd2(board,super.getCol(),super.getRow(),i,j)) {
                    validMoves.add(new Position(i, j));
                }

            }

            for (int i = super.getCol()+1, j = super.getRow()-1; i <= 6 && j >= 0; i++, j--) {
                if (isValidMove(i, j) &&isOpponentPiece(board, i, j,details.getColor())&&isPathClearFromBegin2(board,super.getCol(),super.getRow(),i,j)) {
                    validMoves.add(new Position(i, j));
                }

            }
        }


        return validMoves;
    }

    private boolean isOpponentPiece(Board board, int col, int row, PieceColor currentColor) {
        Piece piece = board.getPiece(col, row);
        return piece == null || piece.getColor() != currentColor;
    }

    private boolean isPathClearFromBegin1(Board board, int col, int row, int toCol, int toRow) {
        while (col + 1 < toCol && row + 1 < toRow) {
            col++;
            row++;
            Piece piece = board.getPiece(col, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            } 
        }
        return true; // Path is clear
    }

    private boolean isPathClearFromBegin2(Board board, int col, int row, int toCol, int toRow) { 
        while (col + 1 < toCol && row - 1 > toRow) {
            col++;
            row--;
            Piece piece = board.getPiece(col, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            } 
        }
        return true; // Path is clear
    }

    private boolean isPathClearFromEnd1(Board board, int col, int row, int toCol, int toRow) {
        while (col - 1 > toCol && row - 1 > toRow) {
            col--;
            row--;
            Piece piece = board.getPiece(col, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }
        }
        return true; // Path is clear
    }

    private boolean isPathClearFromEnd2(Board board, int col, int row, int toCol, int toRow) {
        while (col - 1 > toCol && row + 1 < toRow) {
            col--;
            row++;
            Piece piece = board.getPiece(col, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }
        }
        return true; // Path is clear
    }


}
