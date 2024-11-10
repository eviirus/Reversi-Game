import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameWindow extends JPanel implements MouseListener {
    public enum GameState {
        WhiteTurn,
        BlackTurn,
        Draw,
        WhiteWins,
        BlackWins
    }

    private static final int PANEL_HEIGHT = 600;
    private static final int PANEL_WIDTH = 500;

    private GameGrid gameGrid;
    private GameState gameState;
    private String gameStateStr;

    public GameWindow() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.LIGHT_GRAY);

        gameGrid = new GameGrid(new Position(0, 0), PANEL_WIDTH, PANEL_HEIGHT - 100, 8, 8);
        setGameState(GameState.BlackTurn);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        gameGrid.paint(g);
        drawGameState(g);
    }

    public void restart() {
        gameGrid.reset();
        setGameState(GameState.BlackTurn);
    }

    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_R) {
            restart();
            repaint();
        }
    }

    public void playTurn(Position gridPosition) {
        if (!gameGrid.isValidMove(gridPosition)) {
            return;
        } else if (gameState == GameState.BlackTurn) {
            gameGrid.playMove(gridPosition, 1);
            setGameState(GameState.WhiteTurn);
        } else if (gameState == GameState.WhiteTurn) {
            gameGrid.playMove(gridPosition, 2);
            setGameState(GameState.BlackTurn);
        }
    }

    public void setGameState(GameState newState) {
        gameState = newState;
        switch (gameState) {
            case WhiteTurn:
                if (!gameGrid.getAllValidMoves().isEmpty()) {
                    gameStateStr = "White Player Turn";
                } else {
                    gameGrid.updateValidMoves(1);
                    if (!gameGrid.getAllValidMoves().isEmpty()) {
                        setGameState(GameState.BlackTurn);
                    } else {
                        checkGameEnd(false);
                    }
                }
                break;
            case BlackTurn:
                if (!gameGrid.getAllValidMoves().isEmpty()) {
                    gameStateStr = "Black Player Turn";
                } else {
                    gameGrid.updateValidMoves(2);
                    if (!gameGrid.getAllValidMoves().isEmpty()) {
                        setGameState(GameState.WhiteTurn);
                    } else {
                        checkGameEnd(false);
                    }
                }
                break;
            case WhiteWins:
                gameStateStr = "White Player Wins! Press R to restart.";
                break;
            case BlackWins:
                gameStateStr = "Black Player Wins! Press R to restart.";
                break;
            case Draw:
                gameStateStr = "Draw! Press R to restart.";
                break;
        }
    }

    public void checkGameEnd(boolean stillValidMoves) {
        int gameResult = gameGrid.getWinner(stillValidMoves);
        if (gameResult == 1) {
            setGameState(GameState.BlackWins);
        } else if (gameResult == 2) {
            setGameState(GameState.WhiteWins);
        } else if (gameResult == 3) {
            setGameState(GameState.Draw);
        }
    }

    private void drawGameState(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        int strWidth = g.getFontMetrics().stringWidth(gameStateStr);
        g.drawString(gameStateStr, PANEL_WIDTH / 2 - strWidth / 2, PANEL_HEIGHT - 40);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameState == GameState.WhiteTurn || gameState == GameState.BlackTurn) {
            Position gridPosition = gameGrid.convertMouseToGridPosition(new Position(e.getX(), e.getY()));
            playTurn(gridPosition);
            checkGameEnd(true);
        }
        repaint();
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
}
