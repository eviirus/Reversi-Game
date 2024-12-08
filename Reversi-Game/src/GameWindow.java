import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameWindow extends JPanel implements MouseListener {
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    public static final String RESET_MESSAGE = "Press R to restart.";
    private static final int NO_VALID_MOVES = 0;
    private GameGrid gameGrid;
    private GameState gameState;
    String gameStateStr;
    private GameStateBehavior gameStateBehavior;
    public GameWindow() {
        setPreferredSize(new Dimension(GameConfig.PANEL_WIDTH, GameConfig.PANEL_HEIGHT));
        setBackground(GameConfig.BACKGROUND_COLOR);

        GridCellFactory cellFactory = new DefaultGridCellFactory();

        gameGrid = new GameGrid(new Position(0, 0), GameConfig.PANEL_WIDTH, GameConfig.PANEL_HEIGHT - GameConfig.STATUS_BAR_HEIGHT, GameConfig.GRID_WIDTH, GameConfig.GRID_HEIGHT, new DefaultGridCellFactory());
        gameStateStr = "Black Player Turn";
        setGameState(GameState.BlackTurn);
        addMouseListener(this);
    }

    public GameGrid getGameGrid() {
        return gameGrid;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameGrid.paint(g);
        drawGameState(g);
    }

    public void restart() {
        gameGrid.reset();
        setGameState(GameState.BlackTurn);
        repaint();
    }

    public void handleInput(int keyCode) {
        gameStateBehavior.handleInput(this, keyCode);
    }

    public void playTurn(Position gridPosition) {
        gameStateBehavior.playTurn(this, gridPosition);
    }

    public void checkGameEnd(boolean stillValidMoves) {
        gameStateBehavior.checkGameEnd(this, stillValidMoves);
    }

    public void setGameState(GameState newState) {
        switch (newState) {
            case BlackTurn:
                gameStateBehavior = new BlackTurnState();
                break;
            case WhiteTurn:
                gameStateBehavior = new WhiteTurnState();
                break;
            case Draw, WhiteWins, BlackWins:
                gameStateBehavior = new GameOverState();
                break;
        }
        gameState = newState;
        gameStateBehavior.updateGameState(this);
        repaint();
    }

    public void handleGameEnd(int winner) {
        switch (winner) {
            case PLAYER_ONE:
                setGameState(GameState.BlackWins);
                gameStateStr = "Black Player Wins! " + RESET_MESSAGE;
                break;
            case PLAYER_TWO:
                setGameState(GameState.WhiteWins);
                gameStateStr = "White Player Wins! " + RESET_MESSAGE;
                break;
            case NO_VALID_MOVES:
                setGameState(GameState.Draw);
                gameStateStr = "Draw! " + RESET_MESSAGE;
                break;
        }
    }

    private void drawGameState(Graphics g) {
        g.setColor(GameConfig.TEXT_COLOR);
        g.setFont(GameConfig.STATUS_FONT);
        int strWidth = g.getFontMetrics().stringWidth(gameStateStr);
        g.drawString(gameStateStr, GameConfig.PANEL_WIDTH / 2 - strWidth / 2, GameConfig.PANEL_HEIGHT - 40);
    }

    public GameState getCurrentGameState() {
        return gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position gridPosition = gameGrid.convertMouseToGridPosition(new Position(e.getX(), e.getY()));

        if (gridPosition != null) {
            playTurn(gridPosition);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public enum GameState {
        WhiteTurn, BlackTurn, Draw, WhiteWins, BlackWins
    }
}