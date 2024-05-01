import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Game{
    private BoardController boardController;
    private static final String FILE_NAME = "game_save.txt";//the text file name
    private static final String CURRENT_TURN_PREFIX = "Current Turn: ";
    private static final String TURN_COUNTER_PREFIX = "Turn Counter: ";

    public Game(BoardController boardController){
        this.boardController= boardController;
    }

    public void saveGame() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("game_save.txt"));

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    Piece piece = boardController.getPieceTypeAt(row, col);
                    int counter = boardController.getTurnCounter();
                    PieceColor playerTurn = boardController.getCurrentTurn();
                    String pieceStr = formatPieceForSaving(piece, playerTurn, counter);
                    writer.write(pieceStr + " ");
                }
                writer.newLine();
            }

            // Save other state if necessary, like current player's turn and turn counter
            writer.write("Current Turn: " + boardController.getCurrentTurn());
            writer.newLine();
            writer.write("Turn Counter: " + boardController.getTurnCounter());
            writer.newLine();
            writer.close();
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String formatPieceForSaving(Piece piece,PieceColor playerTurn , int turnCounter) {
        // If piece is null, it represents an empty space on the board
        if (piece == null) {
            return "NONE";
        }

        String pieceType = piece.getType().toString();
        String pieceColor = (piece.getColor() != null) ? piece.getColor().toString() : "null";

        
        
        // Return the formatted stringboa
        return pieceType + "," + piece.getCol() + "," + piece.getRow() + "," + pieceColor ;
    }

    public Piece createPieceFromString(String pieceStr) {
        if (pieceStr.equals("NONE")) {
            return null; // No piece in this cell
        }

        String[] parts = pieceStr.split(",");

        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid piece string: " + pieceStr);
        }

        PieceType type = PieceType.valueOf(parts[0]);
        int col = Integer.parseInt(parts[1]);
        int row = Integer.parseInt(parts[2]);
        PieceColor color = "null".equals(parts[3]) ? null : PieceColor.valueOf(parts[3]);

        
       
        // Use the factory method to create the appropriate Piece instance
        Piece piece = Piece.factory(type, col, row, color);

        return piece;
    }

    public void setUp(){
        System.out.println("Hello, I'm trying to load the game.");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            System.out.println("Starting to load...");

            String line;

            while ((line = reader.readLine()) != null ) {
                System.out.println("Reading the pieces...");

                String[] parts = line.split(": ");
                for (int row = 0; row < 8; row++) {
                    if (line.startsWith(CURRENT_TURN_PREFIX)) {
                        PieceColor currentTurn = PieceColor.valueOf(parts[1]);
                        boardController.setCurrentTurn(currentTurn);
                        System.out.println("current turn : "+currentTurn);
                        continue;
                    }if (line.startsWith(TURN_COUNTER_PREFIX)) {
                        int turnCounter = Integer.parseInt(parts[1]);
                        boardController.setTurnCounter(turnCounter);
                        System.out.println("Turn counter: " + turnCounter);
                        continue;

                    }

                }

            }

        } catch (IOException e) {
            System.err.println("Error reading the game file: " + e.getMessage());

        }

    }
    public void restartGame() {
        new Run().main(null);
    }


}