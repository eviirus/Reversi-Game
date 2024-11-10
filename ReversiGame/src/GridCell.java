import java.awt.*;

public class GridCell extends Rectangle {
    private int cellState;
    private boolean highlight;

    public GridCell(Position position, int width, int height){
        super(position, width, height);
        reset();
    }

    public void reset(){
        cellState = 0;
        highlight = false;
    }

    public void setCellState(int newState){
        this.cellState = newState;
    }

    public int getCellState(){
        return cellState;
    }

    public void setHighlight(boolean highlight){
        this.highlight = highlight;
    }

    public void paint(Graphics g){
        if(highlight){
            g.setColor(new Color(255, 187, 22, 203));
            g.fillRect(position.x, position.y, width, height);
        }

        if(cellState == 0) return;

        g.setColor(cellState == 1 ? Color.BLACK : Color.WHITE);
        g.fillOval(position.x, position.y, width, height);
    }
}
