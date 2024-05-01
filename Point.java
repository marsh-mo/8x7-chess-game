import java.util.ArrayList;
import java.util.List;

public class Point extends Piece {

    public Point(int x, int y, PieceColor color) {
        super(x, y, color, PieceType.POINT);

    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        // Check if the destination is within the board boundaries
        return toRow >= 0 && toRow < 6 && toCol >= 0 && toCol < 7;
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        Piece details = board.getPiece(super.getCol(), super.getRow());

        if (details == null || details.getType() != PieceType.POINT) {

            return validMoves;
        }

        if (details.getType() == PieceType.POINT) {
    
            for (int i = super.getCol() ; i >= super.getCol()-2; i--) {
                if (isValidMove(i, super.getRow())&&isOpponentPiece(board, i,super.getRow(),details.getColor())&&isPathClearFromEnd(board,super.getCol(), super.getRow(),i, super.getRow())) {
                    validMoves.add(new Position(i, super.getRow()));
                }

            }
        }


        return validMoves;
    }


    private boolean isOpponentPiece(Board board, int col, int row, PieceColor currentColor) {
        Piece piece = board.getPiece(col, row);
        return piece == null || piece.getColor() != currentColor;
    }


    private boolean isPathClearFromEnd(Board board, int col, int row, int toCol, int toRow) {
        for (int i = col - 1; i >= toCol + 1; i--) {
            Piece piece = board.getPiece(i, row);
            if (piece != null && piece.getType() != PieceType.SPACE) {
                return false; // Path is not clear
            }
        }
        return true; // Path is clear
    }


}
