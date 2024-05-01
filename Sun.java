import java.util.ArrayList;
import java.util.List;

public class Sun extends Piece {


    public Sun(int x, int y, PieceColor color) {
        super(x, y, color, PieceType.SUN);
       
    }

    @Override
    public boolean isValidMove(int toRow, int toCol) {
        return toRow >= 0 && toRow < 6 && toCol >= 0 && toCol < 7;
           
        
    }

    @Override
    public List<Position> getValidMoves(Board board) {
         List<Position> validMoves = new ArrayList<>();
        Piece details = board.getPiece(super.getCol(), super.getRow());

         if (details == null ) {
        
        return validMoves;
    }


           if (details.getType() == PieceType.SUN) {
    
    for (int i = super.getCol() ; i <= super.getCol()+1; i++) {
        if (isValidMove(i, super.getRow())&&isOpponentPiece(board, i, super.getRow(),details.getColor())) {
            validMoves.add(new Position(super.getCol(), super.getRow()));
        }
        
    }

    
    for (int i = super.getCol() ; i >= super.getCol()-1; i--) {
        if (isValidMove(i, super.getRow())&&isOpponentPiece(board, i, super.getRow(),details.getColor())) {
            validMoves.add(new Position(i, super.getRow()));
        }
        
    }

    
    for (int i = super.getRow() ; i <=super.getRow()+1; i++) {
        if (isValidMove(super.getCol(), i)&&isOpponentPiece(board, super.getCol(), i,details.getColor())) {
            validMoves.add(new Position(super.getCol(), i));
        }
        
    }

    
    for (int i = super.getRow() ; i >= super.getRow()-1; i--) {
        if (isValidMove(super.getCol(), i)&&isOpponentPiece(board, super.getCol(), i,details.getColor())) {
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



}
