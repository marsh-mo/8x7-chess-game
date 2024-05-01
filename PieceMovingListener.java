import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javafx.scene.paint.Color;


public class PieceMovingListener extends MouseAdapter   {
    private ChessBoardView chessBoardView;
    private BoardController boardController;
    private static Position position1 , position2 ;
    private int row, col ;

   
    public PieceMovingListener(int row, int col, BoardController boardController, ChessBoardView chessBoardView) {
        this.row = row;
        this.col = col;
        this.boardController = boardController;
        this.chessBoardView = chessBoardView;
  
      

    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
        if (position1 == null) {
         
            
            Piece piece = boardController.getPieceTypeAt(row, col);
            if(piece.getType()==PieceType.SPACE){
                System.out.println("why you Clicked on empty space!");
                position1=null;
                
               
            }else{
                position1 = new Position(row, col);
                System.out.println("valid piece");
                System.out.println("choose you destination now:");
                highlightValidMoves(position1);
        
            }


        } else {
          
            
            position2 = new Position(row, col);

            if (boardController.valdatingTheMoves(position1, position2)) {
                chessBoardView.updatePlayerTurn();
                chessBoardView.updateBoardUI();
                chessBoardView.updatePlayerTurn();
                
               
                
                
                 
            }
             System.out.println("you can now choose another piece");
            position1 = null;
            position2 = null;
            removeHighlight();

        }
        
    }
    private void highlightValidMoves(Position sourcePosition) {
        List<Position> validMoves = boardController.getValidMovesForPiece(sourcePosition);
        for (Position move : validMoves) {
            int moveRow = move.getX();
            int moveCol = move.getY();
            chessBoardView.highlightCell(moveRow, moveCol, java.awt.Color.GREEN);
        }
    }

    private void removeHighlight() {
        chessBoardView.clearHighlightedCells();
    }

    }
    
