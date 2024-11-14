import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameGrid {
    private GridCell[][] grid;
    private List<Position> validMoves;
    private int cellWidth, cellHeight;
    private int gridWidth, gridHeight;

    public GameGrid(Position position, int width, int height, int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellWidth = (width - position.x) / gridWidth;
        this.cellHeight = (height - position.y) / gridHeight;
        grid = new GridCell[gridWidth][gridHeight];
        validMoves = new ArrayList<>();

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                int x = position.x + i * cellWidth;
                int y = position.y + j * cellHeight;
                grid[i][j] = new GridCell(new Position(x, y), cellWidth, cellHeight);
            }
        }

        reset();
    }

    public Position convertMouseToGridPosition(Position mousePosition) {
        int gridX = mousePosition.x / cellWidth;
        int gridY = mousePosition.y / cellHeight;

        if (gridX >= 0 && gridX < grid.length && gridY >= 0 && gridY < grid[0].length) {
            return new Position(gridX, gridY);
        }
        return null;
    }

    public void reset() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].reset();
            }
        }

        int centerX = gridWidth / 2;
        int centerY = gridHeight / 2;
        grid[centerX - 1][centerY - 1].setCellState(1);
        grid[centerX][centerY].setCellState(1);
        grid[centerX - 1][centerY].setCellState(2);
        grid[centerX][centerY - 1].setCellState(2);

        updateValidMoves(1);
    }

    public void updateValidMoves(int player) {
        validMoves.clear();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Position pos = new Position(i, j);
                if (isValidMove(pos, player)) {
                    validMoves.add(pos);
                    grid[i][j].setHighlight(true);
                } else {
                    grid[i][j].setHighlight(false);
                }
            }
        }
    }

    public boolean isValidMove(Position pos, int player) {
        if (!isInBounds(pos) || grid[pos.x][pos.y].getCellState() != 0) {
            return false;
        }

        int opponent = (player == 1) ? 2 : 1;
        Position[] directions = {
                Position.UP, Position.DOWN, Position.LEFT, Position.RIGHT,
                Position.UP_LEFT, Position.UP_RIGHT, Position.DOWN_LEFT, Position.DOWN_RIGHT
        };

        boolean validMoveFound = false;

        for (Position dir : directions) {
            Position current = pos.add(dir);
            boolean foundOpponent = false;

            while (isInBounds(current) && grid[current.x][current.y].getCellState() == opponent) {
                foundOpponent = true;
                current = current.add(dir);
            }

            if (foundOpponent && isInBounds(current) && grid[current.x][current.y].getCellState() == player) {
                validMoveFound = true;
                break;
            }
        }
        return validMoveFound;
    }

    public void playMove(Position pos, int player) {
        if (!isValidMove(pos, player)) {
            return;
        }
        grid[pos.x][pos.y].setCellState(player);
        flipPieces(pos, player);
        updateValidMoves(player == 1 ? 2 : 1);
    }

    private void flipPieces(Position pos, int player) {
        int opponent = (player == 1) ? 2 : 1;
        Position[] directions = { Position.UP, Position.DOWN, Position.LEFT, Position.RIGHT,
                Position.UP_LEFT, Position.UP_RIGHT, Position.DOWN_LEFT, Position.DOWN_RIGHT };

        for (Position dir : directions) {
            List<Position> toFlip = new ArrayList<>();
            Position current = pos.add(dir);

            while (isInBounds(current) && grid[current.x][current.y].getCellState() == opponent) {
                toFlip.add(current);
                current = current.add(dir);
            }

            if (isInBounds(current) && grid[current.x][current.y].getCellState() == player) {
                for (Position p : toFlip) {
                    grid[p.x][p.y].setCellState(player);
                }
            }
        }
    }

    private boolean isInBounds(Position pos) {
        return pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight;
    }

    public int getWinner(boolean stillValidMoves) {
        int blackCount = 0;
        int whiteCount = 0;

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                int state = grid[i][j].getCellState();
                if (state == 1) blackCount++;
                else if (state == 2) whiteCount++;
            }
        }

        System.out.println("Black piece count: " + blackCount);
        System.out.println("White piece count: " + whiteCount);
        if (blackCount > whiteCount) return 1;
        if (whiteCount > blackCount) return 2;
        return 3;
    }

    public List<Position> getAllValidMoves() {
        return new ArrayList<>(validMoves);
    }

    public void paint(Graphics g) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grid[i][j].paint(g);
            }
        }
    }
}
