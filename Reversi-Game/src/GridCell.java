import java.awt.*;

public class GridCell {
    public static final int EMPTY = 0;
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    private static final Color HIGHLIGHT_COLOR = new Color(255, 187, 22, 203);
    private static final Color EMPTY_COLOR = Color.GRAY;
    private static final Color PLAYER_ONE_COLOR = Color.BLACK;
    private static final Color PLAYER_TWO_COLOR = Color.WHITE;

    private int cellState;
    private boolean highlight;
    private Position position;
    private int width, height;

    public GridCell(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        reset();
    }

    public void reset() {
        cellState = EMPTY;
        highlight = false;
    }

    public void setCellState(int newState) {
        this.cellState = newState;
    }

    public int getCellState() {
        return cellState;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void paint(Graphics g) {
        if (highlight) {
            g.setColor(HIGHLIGHT_COLOR);
            g.fillRect(position.x, position.y, width, height);
        } else {
            if (cellState == EMPTY) {
                g.setColor(EMPTY_COLOR);
                g.drawRect(position.x, position.y, width, height);
            } else {
                g.setColor(cellState == PLAYER_ONE ? PLAYER_ONE_COLOR : PLAYER_TWO_COLOR);
                g.fillOval(position.x, position.y, width, height);
            }
        }
    }
}