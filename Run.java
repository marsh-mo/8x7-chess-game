public class Run {
    public static void main(String[] args) {
           BoardController boardController = new BoardController(new Board());
    
        new ChessBoardView(boardController,new Game(boardController));
        
    }
}
