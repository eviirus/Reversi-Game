public class DefaultGridCellFactory implements GridCellFactory {
    @Override
    public GridCell createGridCell(int x, int y, int state, int width, int height) {
        Position position = new Position(x, y);
        return new GridCell(position, width, height);
    }
}