import java.util.ArrayList;
import java.util.List;

public class Space extends Piece {

    public Space(int col, int row) {
        super(col, row, null, PieceType.SPACE);
    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        Piece details = board.getPiece(super.getCol(), super.getRow());
        if(details.getType()==PieceType.SPACE)
            validMoves.add(new Position(super.getRow(),super.getCol()));
        return validMoves;

    }

}
