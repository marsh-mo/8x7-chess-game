import java.util.ArrayList;
import java.util.List;

public class Plus extends Piece {

    public Plus(int col, int row, PieceColor color) {
        super(col, row, color, PieceType.PLUS);
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
            System.out.println("oilsjfosijfios");
            return validMoves;
        }

        if (details.getType() == PieceType.PLUS) {
            for (int i = super.getCol() + 1; i <= 6; i++) {
                if (isValidMove(i, super.getRow())&&isOpponentPiece(board, i, super.getRow(),details.getColor())&&
                isPathClearFromBeginRow(board,super.getCol(), super.getRow(),i, super.getRow())) {
                    validMoves.add(new Position(i, super.getRow()));
                }

            }

            for (int i = super.getCol() - 1; i >= 0; i--) {
                if (isValidMove(i, super.getRow())&&isOpponentPiece(board, i, super.getRow(),details.getColor())&&
                isPathClearFromEndRow(board,super.getCol(), super.getRow(),i, super.getRow())) {
                    validMoves.add(new Position(i, super.getRow()));
                }

            }

            for (int i = super.getRow() + 1; i <= 6; i++) {
                if (isValidMove(super.getCol(), i)&&isOpponentPiece(board, super.getCol(), i,details.getColor())&&
                isPathClearFromBeginClo(board,super.getCol(), super.getRow(),super.getCol(), i)) {
                    validMoves.add(new Position(super.getCol(), i));
                }

            }

            for (int i = super.getRow() - 1; i >= 0; i--) {
                if (isValidMove(super.getCol(), i)&&isOpponentPiece(board, super.getCol(), i,details.getColor())&&
                isPathClearFromEndCol(board,super.getCol(), super.getRow(),super.getCol(), i)) {
                    validMoves.add(new Position(super.getCol(), i));
                }

            }
        }

        return validMoves;
    }

    private boolean isOpponentPiece(Board board, int col, int row, PieceColor currentColor) {
        Piece piece = board.getPiece(col, row);
        return piece == null || piece.getColor() != currentColor;
    }

    private boolean isPathClearFromBeginRow(Board board, int col, int row, int toCol, int toRow) {
        for (int i = col + 1; i <= toCol - 1; i++) {

            Piece piece = board.getPiece(i, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear

            }
        }
        return true; // Path is clear
    }

    private boolean isPathClearFromBeginClo(Board board, int col, int row, int toCol, int toRow) {

        for (int j = row + 1; j <= toRow - 1; j++) {
            Piece piece = board.getPiece(col, j);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }

        }
        return true; // Path is clear
    }

    private boolean isPathClearFromEndRow(Board board, int col, int row, int toCol, int toRow) {
        for (int i = col - 1; i >= toCol + 1; i--) {

            Piece piece = board.getPiece(i, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }

        }
        return true; // Path is clear
    }

    private boolean isPathClearFromEndCol(Board board, int col, int row, int toCol, int toRow) {

        for (int j = row - 1; j >= toRow + 1; j--) {
            Piece piece = board.getPiece(col, j);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }

        }
        return true; // Path is clear
    }

}
