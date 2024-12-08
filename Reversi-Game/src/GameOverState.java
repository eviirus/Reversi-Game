import java.awt.event.KeyEvent;

public class GameOverState implements GameStateBehavior {
    @Override
    public void handleInput(GameWindow gameWindow, int keyCode) {
        KeyEvent KeyEvent = null;
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_R) {
            gameWindow.restart();
        }
    }

    @Override
    public void playTurn(GameWindow gameWindow, Position position) {
    }

    @Override
    public void checkGameEnd(GameWindow gameWindow, boolean stillValidMoves) {
    }

    @Override
    public void updateGameState(GameWindow gameWindow) {
        if (gameWindow.getCurrentGameState() == GameWindow.GameState.Draw) {
            gameWindow.gameStateStr = "Draw! " + GameWindow.RESET_MESSAGE;
        } else if (gameWindow.getCurrentGameState() == GameWindow.GameState.WhiteWins) {
            gameWindow.gameStateStr = "White Player Wins! " + GameWindow.RESET_MESSAGE;
        } else if (gameWindow.getCurrentGameState() == GameWindow.GameState.BlackWins) {
            gameWindow.gameStateStr = "Black Player Wins! " + GameWindow.RESET_MESSAGE;
        }
    }

}
