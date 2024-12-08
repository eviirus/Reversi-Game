public interface GridCellFactory {
    GridCell createGridCell(int x, int y, int state, int width, int height);
}