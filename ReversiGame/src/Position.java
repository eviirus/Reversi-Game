public class Position {
    public static final Position DOWN = new Position(0, 1);
    public static final Position UP = new Position(0, -1);
    public static final Position LEFT = new Position(-1, 0);
    public static final Position RIGHT = new Position(1, 0);

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public void add(Position pos) {
        this.x += pos.x;
        this.y += pos.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }
}
