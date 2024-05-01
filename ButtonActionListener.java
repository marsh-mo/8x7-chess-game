import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
    private ChessBoardView chessBoardView;
    private BoardController boardController;

    public ButtonActionListener(BoardController boardController, ChessBoardView chessBoardView) {
        this.boardController = boardController;
        this.chessBoardView = chessBoardView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Save"://if the save button clicked 
                chessBoardView.saveGame();//call the save function 
                break;
            case "Load"://if the load button clicked
                chessBoardView.loadGame();//call the load function
                chessBoardView.repaint();
                chessBoardView.updateBoardUI();//calling the function to update the GUI cells
                chessBoardView.updatePlayerTurn();//calling the function to update the player label on the main frame
                break;
            case "Reset"://if the reset button cliked 
                chessBoardView.restartGame();//calling the restart the game function 
                break;

        }
    }
}

