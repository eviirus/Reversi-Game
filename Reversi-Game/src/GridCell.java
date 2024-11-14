import java.awt.*;

public class GridCell {
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
        cellState = 0;
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
            g.setColor(new Color(255, 187, 22, 203));
            g.fillRect(position.x, position.y, width, height);
        }

        if (cellState == 0) {
            g.setColor(Color.GRAY);
            g.drawRect(position.x, position.y, width, height);
        } else {
            g.setColor(cellState == 1 ? Color.BLACK : Color.WHITE);
            g.fillOval(position.x, position.y, width, height);
        }
    }
}
