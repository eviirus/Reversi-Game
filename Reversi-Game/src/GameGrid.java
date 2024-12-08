import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameGrid {
    private GridCell[][] grid;
    private List<Position> validMoves;
    private int cellWidth, cellHeight;
    private int gridWidth, gridHeight;
    private GridCellFactory gridCellFactory;
    private static final Position[] DIRECTIONS = {
            Position.UP, Position.DOWN, Position.LEFT, Position.RIGHT,
            Position.UP_LEFT, Position.UP_RIGHT, Position.DOWN_LEFT, Position.DOWN_RIGHT
    };
    private static final int PLAYER_BLACK = 1;
    private static final int PLAYER_WHITE = 2;
    private static final int EMPTY = 0;
    private static final int DRAW = 3;

    public GameGrid(Position position, int width, int height, int gridWidth, int gridHeight, GridCellFactory gridCellFactory) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellWidth = (width - position.x) / gridWidth;
        this.cellHeight = (height - position.y) / gridHeight;
        grid = new GridCell[gridWidth][gridHeight];
        validMoves = new ArrayList<>();

        forEachGridCell((i, j, cell) -> {
            int x = position.x + i * cellWidth;
            int y = position.y + j * cellHeight;
            grid[i][j] = gridCellFactory.createGridCell(x, y, GridCell.EMPTY, cellWidth, cellHeight);
        });

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
        resetGridCells();
        initializeStartingPieces();
        updateValidMoves(PLAYER_BLACK);
    }

    private void resetGridCells() {
        forEachGridCell((x, y, cell) -> cell.reset());
    }

    private void initializeStartingPieces() {
        int centerX = gridWidth / 2;
        int centerY = gridHeight / 2;
        grid[centerX - 1][centerY - 1].setCellState(PLAYER_BLACK);
        grid[centerX][centerY].setCellState(PLAYER_BLACK);
        grid[centerX - 1][centerY].setCellState(PLAYER_WHITE);
        grid[centerX][centerY - 1].setCellState(PLAYER_WHITE);
    }

    public void updateValidMoves(int player) {
        validMoves.clear();
        forEachGridCell((x, y, cell) -> {
            Position pos = new Position(x, y);
            if (isValidMove(pos, player)) {
                validMoves.add(pos);
                cell.setHighlight(true);
            } else {
                cell.setHighlight(false);
            }
        });
    }

    public boolean isValidMove(Position pos, int player) {
        if (!isInBounds(pos) || grid[pos.x][pos.y].getCellState() != EMPTY) {
            return false;
        }

        int opponent = (player == PLAYER_BLACK) ? PLAYER_WHITE : PLAYER_BLACK;

        for (Position dir : DIRECTIONS) {
            if (checkDirection(pos, dir, player, opponent)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOpponentCell(Position pos, int opponent) {
        return isInBounds(pos) && grid[pos.x][pos.y].getCellState() == opponent;
    }

    private boolean isPlayerCell(Position pos, int player) {
        return isInBounds(pos) && grid[pos.x][pos.y].getCellState() == player;
    }

    private boolean checkDirection(Position start, Position direction, int player, int opponent) {
        Position current = start.add(direction);
        boolean foundOpponent = false;

        while (isOpponentCell(current, opponent)) {
            foundOpponent = true;
            current = current.add(direction);
        }

        return foundOpponent && isPlayerCell(current, player);
    }

    public void playMove(Position pos, int player) {
        if (!isValidMove(pos, player)) {
            return;
        }
        grid[pos.x][pos.y].setCellState(player);
        flipPieces(pos, player);
        updateValidMoves(player == PLAYER_BLACK ? PLAYER_WHITE : PLAYER_BLACK);
    }

    private void flipPieces(Position pos, int player) {
        int opponent = (player == PLAYER_BLACK) ? PLAYER_WHITE : PLAYER_BLACK;

        for (Position dir : DIRECTIONS) {
            flipInDirection(pos, dir, player, opponent);
        }
    }

    private void flipInDirection(Position start, Position direction, int player, int opponent) {
        List<Position> toFlip = new ArrayList<>();
        Position current = start.add(direction);

        while (isInBounds(current) && grid[current.x][current.y].getCellState() == opponent) {
            toFlip.add(current);
            current = current.add(direction);
        }

        if (isInBounds(current) && grid[current.x][current.y].getCellState() == player) {
            for (Position p : toFlip) {
                grid[p.x][p.y].setCellState(player);
            }
        }
    }

    private boolean isInBounds(Position pos) {
        return pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight;
    }

    public int getWinner(boolean stillValidMoves) {
        final int[] pieceCounts = {0, 0};

        forEachGridCell((x, y, cell) -> {
            int state = cell.getCellState();
            if (state == PLAYER_BLACK) pieceCounts[0]++;
            else if (state == PLAYER_WHITE) pieceCounts[1]++;
        });

        int blackCount = pieceCounts[0];
        int whiteCount = pieceCounts[1];

        if (blackCount > whiteCount) return PLAYER_BLACK;
        if (whiteCount > blackCount) return PLAYER_WHITE;
        return DRAW;

    }

    public List<Position> getAllValidMoves() {
        return new ArrayList<>(validMoves);
    }

    public void paint(Graphics g) {
        forEachGridCell((x, y, cell) -> cell.paint(g));
    }

    private void forEachGridCell(CellAction action) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                action.perform(i, j, grid[i][j]);
            }
        }
    }

    @FunctionalInterface
    interface CellAction {
        void perform(int x, int y, GridCell cell);
    }
}