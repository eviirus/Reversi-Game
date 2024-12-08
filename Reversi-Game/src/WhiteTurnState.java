import java.awt.event.KeyEvent;

public class WhiteTurnState implements GameStateBehavior {
    @Override
    public void handleInput(GameWindow gameWindow, int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_R) {
            gameWindow.restart();
        }
    }

    @Override
    public void playTurn(GameWindow gameWindow, Position position) {
        GameStateHelper.playTurn(gameWindow, position, GameWindow.GameState.WhiteTurn);
    }

    @Override
    public void checkGameEnd(GameWindow gameWindow, boolean stillValidMoves) {
        gameWindow.checkGameEnd(stillValidMoves);
    }

    @Override
    public void updateGameState(GameWindow gameWindow) {
        gameWindow.gameStateStr = "White Player Turn";
    }

}
