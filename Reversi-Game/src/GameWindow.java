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
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (keyCode == KeyEvent.VK_R) {
            restart();
        }
    }

    public void playTurn(Position gridPosition) {
        if (!gameGrid.getAllValidMoves().contains(gridPosition)) {
            return;
        }

        if (gameState == GameState.BlackTurn) {
            gameGrid.playMove(gridPosition, 1);
            setGameState(GameState.WhiteTurn);
        } else if (gameState == GameState.WhiteTurn) {
            gameGrid.playMove(gridPosition, 2);
            setGameState(GameState.BlackTurn);
        }

        checkGameEnd(true);
        repaint();
    }

    public void setGameState(GameState newState) {
        gameState = newState;
        gameGrid.updateValidMoves((gameState == GameState.BlackTurn) ? 1 : 2);

        if (gameGrid.getAllValidMoves().isEmpty()) {
            checkGameEnd(false);
        } else {
            gameStateStr = (gameState == GameState.BlackTurn) ? "Black Player Turn" : "White Player Turn";
        }
    }

    public void checkGameEnd(boolean stillValidMoves) {
        if (gameState == GameState.WhiteWins || gameState == GameState.BlackWins || gameState == GameState.Draw) {
            return;
        }

        if (gameGrid.getAllValidMoves().isEmpty()) {
            gameGrid.updateValidMoves((gameState == GameState.BlackTurn) ? 2 : 1);
            if (gameGrid.getAllValidMoves().isEmpty()) {
                int gameResult = gameGrid.getWinner(stillValidMoves);
                switch (gameResult) {
                    case 1:
                        setGameState(GameState.BlackWins);
                        gameStateStr = "Black Player Wins! Press R to restart.";
                        break;
                    case 2:
                        setGameState(GameState.WhiteWins);
                        gameStateStr = "White Player Wins! Press R to restart.";
                        break;
                    case 3:
                        setGameState(GameState.Draw);
                        gameStateStr = "Draw! Press R to restart.";
                        break;
                }
            }
        } else {
            gameStateStr = (gameState == GameState.BlackTurn) ? "Black Player Turn" : "White Player Turn";
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

            if (gridPosition != null) {
                playTurn(gridPosition);
            }
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
}
