import java.util.Collections;
import java.util.List;

public class BoardController {
    private Board board;
    private int turnCounter;
    private PieceColor currentTurn ;
    private Piece destinationPiece ;
    private boolean blueSunAlive , yellowSunAlive ;

    public void handleMove(Position src, Position dst) {//this function will move the piece 
        if (board != null) {
            Piece  movedPiece = board.getPieceAt(src.getX(), src.getY());
            System.out.println(src.getX());
            destinationPiece = board.getPieceAt(dst.getX(), dst.getY());

            if (movedPiece != null && movedPiece.getColor() == currentTurn) {
                // Standard move for other piece types
                movedPiece.setCol(dst.getX());
                movedPiece.setRow(dst.getY());
                board.removePiece(src.getX(), src.getY());
                board.placePiece(movedPiece, dst.getX(), dst.getY());
                currentTurn = (currentTurn == PieceColor.YELLOW) ? PieceColor.BLUE : PieceColor.YELLOW;

                // Check for suns and update their status
                updateSunStatus();

                turnCounter++;
                // Check if it's time to swap the pieces
                if (turnCounter % 4 == 0) {
                    swapPieces();
                }

                // Flip the board after each move
                board.flipBoard();

            } else if (movedPiece != null && movedPiece.getColor() != currentTurn) {
                System.err.println("It's not the current player's turn.");
            } else {
                System.err.println("Error: No piece found at " + src.getX() + ", " + src.getY());
            }
        } else {
            System.err.println("Error: Board reference is null.");
        }
    }

    private void updateSunStatus() {//this function will update the sun status whather is captured or not 
        blueSunAlive = false;
        yellowSunAlive = false;

        for (int row = 0; row < Board.getRows(); row++) {
            for (int col = 0; col < Board.getCols(); col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getType() == PieceType.SUN) {
                    if (piece.getColor() == PieceColor.BLUE) {
                        setBlueSunAlive(true);
                    } else if (piece.getColor() == PieceColor.YELLOW) {
                        setYellowSunAlive(true);
                    }
                }
            }
        }
    }

    private void swapPieces() {//this function will be responsibel for the swithching time piece into plus piece and vice versa 

        int numRows = 6; 
        int numCols = 7; 

        // Iterate over the entire board and swap Time and Plus pieces
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Piece currentPiece = board.getPiece(row, col);
                if (currentPiece != null) {
                    if (currentPiece.getType() == PieceType.TIME) {
                        board.removePiece(row, col);
                        currentPiece.setType(PieceType.PLUS);
                        board.placePiece(currentPiece, row, col);
                    } else if (currentPiece.getType() == PieceType.PLUS) {
                        board.removePiece(row, col);
                        currentPiece.setType(PieceType.TIME);
                        board.placePiece(currentPiece, row, col);
                    }
                }
            }
        }
    }

    public boolean valdatingTheMoves(Position src, Position dst) {//this function responsible for validating the moves logics
        Piece piece = getPieceTypeAt(src.getX(), src.getY());
        if(piece.getType()==PieceType.SPACE){
            return false;
        }else{

            System.out.println("src : " + src.getX() + " " + src.getY());
            if (piece != null && piece.getType() != PieceType.SPACE) {
                System.out.println("destination: " + dst.getX() + ", col: " + dst.getY());

                List<Position>  validMoves = piece.getValidMoves(board);

                if (!validMoves.isEmpty()) {

                    for (Position validMove : validMoves) {

                        if (validMove.getX() == dst.getX() && validMove.getY() == dst.getY()) {//if the move is in the valid moves for this piece we will return true
                            handleMove(src, dst);//calling the function to move the piece
                            return true;
                        }

                    }
                    System.out.println("Selected destination is not a valid move for the piece.");
                } else {
                    System.out.println("No valid moves for the piece.");
                }
            } else {
                System.out.println("Not a valid piece.");
            }
        }
        return false;

    }

    public BoardController(Board board) {

        this.board = board;
        this.turnCounter = 0;//setting the turn counter to 0 
        this.currentTurn = PieceColor.YELLOW; // Set the starting turn to yellow
        this.blueSunAlive = false;//set the statue of the blue sun captured to false 
        this.yellowSunAlive = false;//set the statue of the yellow sun captured to false

    }

    public boolean checkForWinner() {//this function will determine the winner beside of the sun statue 
        if (destinationPiece != null && destinationPiece.getType() == PieceType.SUN) {
            return true;
        }
        return false;
    }

    public List<Position> getValidMovesForPiece(Position src) {
        Piece piece = getPieceTypeAt(src.getX(), src.getY());
        if (piece != null) {
            return piece.getValidMoves(board);
        }
        return Collections.emptyList();
    }

    public void setDestinationPiece(Piece destinationPiece) {
        this.destinationPiece = destinationPiece;
    }

    public Piece getDestinationPiece() {
        return destinationPiece;
    }

    public boolean isBlueSunAlive() {
        return blueSunAlive;
    }

    public boolean isYellowSunAlive() {
        return yellowSunAlive;
    }

    public void setBlueSunAlive(boolean blueSunAlive) {
        this.blueSunAlive = blueSunAlive;
    }

    public void setYellowSunAlive(boolean yellowSunAlive) {
        this.yellowSunAlive = yellowSunAlive;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public Piece getPieceTypeAt(int row, int col) {

        return board.getPiece(row, col);
    }

    public void placePiece(int row, int col, Piece piece){
        board.placePiece(piece,row,col);
    }

    public PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public void setCurrentTurn(PieceColor currentTurn) {
        this.currentTurn = currentTurn;
    }



}
