public class GameStateHelper {
    public static void playTurn(GameWindow gameWindow, Position position, GameWindow.GameState currentState) {
        if (gameWindow.getGameGrid().getAllValidMoves().contains(position)) {
            int currentPlayer = currentState == GameWindow.GameState.BlackTurn ?
                    GameWindow.PLAYER_ONE : GameWindow.PLAYER_TWO;

            gameWindow.getGameGrid().playMove(position, currentPlayer);

            boolean stillValidMoves = !gameWindow.getGameGrid().getAllValidMoves().isEmpty();

            if (!stillValidMoves) {
                int winner = gameWindow.getGameGrid().getWinner(stillValidMoves);
                gameWindow.handleGameEnd(winner);
            } else {
                gameWindow.setGameState(
                        currentState == GameWindow.GameState.BlackTurn ?
                                GameWindow.GameState.WhiteTurn :
                                GameWindow.GameState.BlackTurn
                );
            }

            gameWindow.repaint();
        }
    }
}
