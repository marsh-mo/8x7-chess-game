import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ChessBoardView extends JFrame {
    private BoardController boardController;
    // private String imagesPath = "images\\";
    private JLabel[][] cells;
    private JLabel YellowWonLabel , BlueWonLabel , playerTurn , confirmationLabel;
    private JPanel chessBoard , gameMenuButton ,NotificationPanel , GameStatus,confirmationPanel;
    private JButton saveButton , loadButton ,reseatButton , finishGameButton , restartGameButton,okButton;
    private ImageIcon imageIcon , pieceImage;
    private Game game;
    private ActionListener buttonActionListener ;
    private static final String FILE_NAME = "game_save.txt";
    private List<JLabel> highlightedCells;
    private String imagesPath = "images\\";


    
    

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ChessBoardView() {}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ChessBoardView(BoardController boardController,Game game) {
        this.boardController = boardController;
        this.game=game;
        this.buttonActionListener = new ButtonActionListener(boardController, this);
        highlightedCells = new ArrayList<>();
        initializeGUI();
        addMouseListenerToCells();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void addMouseListenerToCells() {//adding the action for moving the piece to the cells 
        if (cells != null) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    cells[row][col].addMouseListener(new PieceMovingListener(row, col, boardController, this));

                }
            }
        }

    }

    public void highlightCell(int row, int col, Color color) {//to highlight the valid moves on the board GUI
        JLabel cell = cells[row][col];
        cell.setBorder(BorderFactory.createLineBorder(color, 5));
        highlightedCells.add(cell);
    }

    public void clearHighlightedCells() {//remove the highlight from the cells 
        for (JLabel cell : highlightedCells) {
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        }
        highlightedCells.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void initializeGUI() {
        setLayout(new BorderLayout());
        chessBoard = new JPanel();
        chessBoard.setLayout(new GridLayout(6, 7));
        gameMenuButton = new JPanel();
        gameMenuButton.setLayout(new FlowLayout());
        GameStatus = new JPanel();
        GameStatus.setLayout(new FlowLayout());

        setTitle("Marsh Chess");
        cells = new JLabel[6][7];

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                cells[row][col] = new JLabel();
                cells[row][col].setHorizontalAlignment(JLabel.CENTER);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
                setPieceIcon(cells[row][col], row, col);
                chessBoard.add(cells[row][col]);
            }
        }
        setSize(800, 600);
        add(chessBoard, BorderLayout.CENTER);

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.setContentAreaFilled(false);
        saveButton.setForeground(Color.BLACK);
        saveButton.setBorderPainted(true);
        saveButton.setFocusable(false);
        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(100, 30));
        loadButton.setContentAreaFilled(false);
        loadButton.setForeground(Color.BLACK);
        loadButton.setBorderPainted(true);
        loadButton.setFocusable(false);
        reseatButton = new JButton("Reset");
        reseatButton.setPreferredSize(new Dimension(100, 30));
        reseatButton.setContentAreaFilled(false);
        reseatButton.setForeground(Color.BLACK);
        reseatButton.setBorderPainted(true);
        reseatButton.setFocusable(false);

        playerTurn = new JLabel("Player Turn: YELLOW");
        playerTurn.setHorizontalAlignment (JLabel. CENTER);


        gameMenuButton.add(saveButton, BorderLayout.NORTH);
        gameMenuButton.add(loadButton, BorderLayout.CENTER);
        gameMenuButton.add(reseatButton, BorderLayout.CENTER);
        GameStatus.add(playerTurn,BorderLayout.SOUTH);

       
        add(gameMenuButton, BorderLayout.NORTH);
        add(GameStatus,BorderLayout.SOUTH);

        try {
            imageIcon = new ImageIcon(imagesPath + "360_F_597889804_H18u5xomXqceVKiaZ6uf8IOG7g4pJmgv.jpg");
            setIconImage(imageIcon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveButton.addActionListener(buttonActionListener);
        loadButton.addActionListener(buttonActionListener);
        reseatButton.addActionListener(buttonActionListener);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    ///////////////////////////////////////////////////////////////////////////////////////
    public void updateBoardUI() {
        if (cells != null) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    updateCellIcon(cells[row][col], row, col);
                }
            }

            if (boardController != null && boardController.checkForWinner()) {
                showGameWonNotification();
            }

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public void saveGame() {//save game frame 
        //this function will call the frame to pop up after clicking save button for saving the game 
        game.saveGame();

        JFrame confirmationFrame = new JFrame("Game Saved");
        confirmationFrame.setLayout(new BorderLayout());

        confirmationLabel = new JLabel("Game saved successfully!");
        confirmationLabel.setHorizontalAlignment(JLabel.CENTER);

        okButton = new JButton("OK");
        okButton.addActionListener(e -> confirmationFrame.dispose());

        confirmationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmationPanel.add(okButton);

        confirmationFrame.add(confirmationLabel, BorderLayout.CENTER);
        confirmationFrame.add(confirmationPanel, BorderLayout.SOUTH);

        try {
            imageIcon = new ImageIcon(imagesPath + "360_F_597889804_H18u5xomXqceVKiaZ6uf8IOG7g4pJmgv.jpg");
            confirmationFrame.setIconImage(imageIcon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        confirmationFrame.setSize(300, 100);
        confirmationFrame.setLocationRelativeTo(this);
        confirmationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        confirmationFrame.setFocusable(true);
        confirmationFrame.setVisible(true);
    }

    public void loadGame() {//this function resposible for loading the game 
        System.out.println("Hello, I'm trying to load the game.");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            System.out.println("Starting to load...");

            String line;
            int row = 0;

            while ((line = reader.readLine()) != null && row < cells.length) {
                System.out.println("Reading the pieces...");

                String[] pieces = line.split(" ");
                for (int col = 0; col < pieces.length && col < cells[row].length; col++) {
                    Piece piece = game.createPieceFromString(pieces[col]);
                    boardController.placePiece(row, col, piece);
                    setPieceIcon(cells[row][col], row, col);

                }
                row++;
            }
            game.setUp();

        } catch (IOException e) {
            System.err.println("Error reading the game file: " + e.getMessage());
            // Optionally, terminate the program or handle the error gracefully.
        }

        System.out.println("Game loaded successfully.");
    }

    private void showGameWonNotification() {//this function for pop up a frame after the player captured the sun piece
        JFrame winnerFrame = new JFrame("We Have a Winner!");
        try {
            imageIcon = new ImageIcon(imagesPath + "360_F_597889804_H18u5xomXqceVKiaZ6uf8IOG7g4pJmgv.jpg");
            winnerFrame.setIconImage(imageIcon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        winnerFrame.setLayout(new BorderLayout());

        if (!boardController.isBlueSunAlive()) {
            YellowWonLabel = new JLabel("Yellow Player wins! Blue sun has been captured.");
            YellowWonLabel.setHorizontalAlignment(JLabel.CENTER);
            winnerFrame.add(YellowWonLabel, BorderLayout.CENTER);
        } else if (!boardController.isYellowSunAlive()) {
            BlueWonLabel = new JLabel("Blue Player wins! Yellow sun has been captured.");
            BlueWonLabel.setHorizontalAlignment(JLabel.CENTER);
            winnerFrame.add(BlueWonLabel, BorderLayout.CENTER);
        }

        NotificationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        restartGameButton = new JButton("Restart");
        restartGameButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        restartGameButton.setForeground(Color.ORANGE);
        restartGameButton.setBackground(new Color(255, 255, 255));
        restartGameButton.setFocusPainted(true);
        restartGameButton.setFocusable(false);
        restartGameButton.setPreferredSize(new Dimension(120, 40));
        restartGameButton.addActionListener(e -> {
                    winnerFrame.dispose();
                    restartGame();
            });

        finishGameButton = new JButton("Finish");
        finishGameButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        finishGameButton.setForeground(Color.ORANGE);
        finishGameButton.setBackground(new Color(255, 255, 255));
        finishGameButton.setFocusPainted(true);
        finishGameButton.setFocusable(false);
        finishGameButton.setPreferredSize(new Dimension(120, 40));
        finishGameButton.addActionListener(e -> {
                    winnerFrame.dispose();
                    System.exit(0);
            });

        NotificationPanel.add(restartGameButton);
        NotificationPanel.add(finishGameButton);

        winnerFrame.add(NotificationPanel, BorderLayout.SOUTH);

        winnerFrame.setSize(300, 150);
        winnerFrame.setLocationRelativeTo(this);
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    public void restartGame() {//function to call the restart tha game from the game class
        dispose();
        game.restartGame();

    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void setPieceIcon(JLabel cell, int row, int col) {//set the piece image
        pieceImage = new ImageIcon(getImagePathForRowAndCol(row, col));

        cell.setIcon(new ImageIcon(resizePieces(pieceImage)));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    private String getImagePathForRowAndCol(int row, int col) {//get the path of the piece
        Piece piece = getPieceTypeForRowAndCol(row, col);
        if (piece != null && piece.getType() != null) {
            return getImagePathForPieceType(piece);
        }
        return "hhhhhhhhhhhh";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private Piece getPieceTypeForRowAndCol(int row, int col) {//get the piece type

        return boardController.getPieceTypeAt(row, col);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private String getImagePathForPieceType(Piece details) {//setting the full path for the images for every piece

        String colorPrefix = (details.getColor() == PieceColor.BLUE) ? "Blue" : "Yellow";

        switch (details.getType()) {
            case PLUS:
                return   imagesPath + colorPrefix + "Plus.png";
            case POINT:
                return (boardController.getTurnCounter() % 2 == 0) ?
                    imagesPath + colorPrefix + "Point.png" :
                imagesPath + colorPrefix + "RPoint.png";
            case HOURGLASS:
                return imagesPath + colorPrefix + "Hourglass.png";
            case TIME:
                return imagesPath + colorPrefix + "Time.png";
            case SUN:
                return imagesPath + colorPrefix + "Sun.png";

            case SPACE:
                return "";
            default:
                break;
        }
        System.out.println(
            "PieceType: " + details.getType() + ", Color: " + details.getColor() + ", ImagePath: " + imagesPath);
        return imagesPath;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Image resizePieces(ImageIcon image) {//this function will resize the images
        try {
            Image originalImage = image.getImage();
            int newWidth = 70;
            int newHeight = 70;
            return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updateCellIcon(JLabel cell, int row, int col) {
        Piece piece = getPieceTypeForRowAndCol(row, col);
        if (piece != null && piece.getType() == PieceType.POINT) {
            // Update the image path based on the loaded turn counter
            String imagePath = getImagePathForPieceType(piece);
            ImageIcon updatedImage = new ImageIcon(imagePath);
            cell.setIcon(new ImageIcon(resizePieces(updatedImage)));
        } else {
            // For other pieces, use the original logic
            pieceImage = new ImageIcon(getImagePathForRowAndCol(row, col));
            cell.setIcon(new ImageIcon(resizePieces(pieceImage)));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updatePlayerTurn(){
        playerTurn.setText("Player Turn: "+(boardController.getCurrentTurn()));
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

