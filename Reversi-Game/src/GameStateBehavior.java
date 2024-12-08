public interface GameStateBehavior {
    void handleInput(GameWindow gameWindow, int keyCode);
    void playTurn(GameWindow gameWindow, Position position);
    void checkGameEnd(GameWindow gameWindow, boolean stillValidMoves);
    void updateGameState(GameWindow gameWindow);
}
