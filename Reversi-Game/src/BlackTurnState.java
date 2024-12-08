import java.awt.event.KeyEvent;

public class BlackTurnState implements GameStateBehavior {
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
        GameStateHelper.playTurn(gameWindow, position, GameWindow.GameState.BlackTurn);
    }

    @Override
    public void checkGameEnd(GameWindow gameWindow, boolean stillValidMoves) {
        gameWindow.checkGameEnd(stillValidMoves);
    }

    @Override
    public void updateGameState(GameWindow gameWindow) {
        gameWindow.gameStateStr = "Black Player Turn";
    }

}
