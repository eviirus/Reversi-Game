import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {
    public static void main(String[] args) {
        Game game = new Game();
    }

    private GameWindow gameWindow;

    public Game() {
        JFrame frame = new JFrame("Reversi Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        gameWindow = new GameWindow();
        frame.getContentPane().add(gameWindow);

        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameWindow.handleInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}