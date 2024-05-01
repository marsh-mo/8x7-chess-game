import java.util.ArrayList;
import java.util.List;

public class Hourglass extends Piece {

    public Hourglass(int col, int row, PieceColor color) {
        super(col, row, color, PieceType.HOURGLASS);
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
            System.out.println("im here ");
            return validMoves;
        }

        if (details.getType() == PieceType.HOURGLASS) {

            int[] possibleMoves = {-2, -1, 1, 2};

            for (int i : possibleMoves) {
                for (int j : possibleMoves) {

                    if (Math.abs(i) + Math.abs(j) == 3) {
                        int newRow = super.getRow() + i;
                        int newCol = super.getCol() + j;

                        if (isValidMove(newCol, newRow) && isOpponentPiece(board, newCol, newRow, details.getColor())) {
                            validMoves.add(new Position(newCol, newRow));
                            System.out.println("you can go to row : "+newCol);
                            System.out.println("you can go to col : "+newRow);
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    private boolean isOpponentPiece(Board board, int col, int row, PieceColor currentColor) {
        Piece piece = board.getPiece(col, row);
        return piece == null || piece.getColor() != currentColor;
    }



}
