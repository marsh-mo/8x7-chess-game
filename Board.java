public class Board {
    private Piece[][] board;
    private static final int ROWS = 6 , COLS = 7 ;

    public Board() {

        initializeBoard();
    }

    private void initializeBoard() {//this function will set up the board 
        board = new Piece[ROWS][COLS];
        PieceType[][] types = {
                { PieceType.PLUS, PieceType.HOURGLASS, PieceType.TIME, PieceType.SUN, PieceType.TIME,
                    PieceType.HOURGLASS, PieceType.PLUS },
                { PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT,
                    PieceType.POINT },
                { PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE,
                    PieceType.SPACE },
                { PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE, PieceType.SPACE,
                    PieceType.SPACE },
                { PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT, PieceType.POINT,
                    PieceType.POINT },
                { PieceType.PLUS, PieceType.HOURGLASS, PieceType.TIME, PieceType.SUN, PieceType.TIME,
                    PieceType.HOURGLASS, PieceType.PLUS },

            };
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                PieceType type = types[r][c];
                PieceColor color = (r < 2) ? PieceColor.BLUE : (r > 3) ? PieceColor.YELLOW : PieceColor.DEFAULT;

                if (type != null && color != null) {
                    board[r][c] = Piece.factory(type, r, c, color);
                } else {
                    throw new IllegalArgumentException("Invalid piece type or color");
                }
            }
        }

    }

    public Piece getPiece(int row, int col) {//this function will return the piece form a certain position 
        if (board[row][col] != null) {
            return board[row][col];

        } else {

            return null;
        }
    }

    public void removePiece(int row, int col) {//this function will remove the piece from a certain posistino and rplace its posistion with space
        board[row][col] = Piece.factory(PieceType.SPACE, row, col, PieceColor.DEFAULT);
    }

    public boolean isValidMove(int toRow, int toCol) {// this function responsible for validiting the moves whether the moves is indside the board boundry 
        return toRow >= 0 && toRow < 6 && toCol >= 0 && toCol < 7;
    }

    public void placePiece(Piece piece, int row, int col) {//this function will place the moved piece at the desitination 
        board[row][col] = piece;
    }

    public void setPiece(int row, int col, Piece piece) {
        // Check if the row and col are within the board's bounds
        if (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
            board[row][col] = piece;
        } else {
            throw new IllegalArgumentException("Row or column out of bounds");
        }
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }

    public void flipBoard() {// this function responsibel to flipp the board
        Piece[][] newBoard = new Piece[ROWS][COLS];

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Piece piece = board[r][c];
                if (piece != null) {
                    // Calculate and place the pieces into new position after flip the board
                    int newR = ROWS - 1 - r;
                    int newC = COLS - 1 - c;

                    Piece rotatedPiece = Piece.factory(piece.getType(), newR, newC, piece.getColor());

                    // Place the rotated piece on the new board
                    newBoard[newR][newC] = rotatedPiece;

                }
            }
        }
        board = newBoard;

    }

    
}
