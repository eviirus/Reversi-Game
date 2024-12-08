public class Position {
    public static final Position DOWN = new Position(0, 1);
    public static final Position UP = new Position(0, -1);
    public static final Position LEFT = new Position(-1, 0);
    public static final Position RIGHT = new Position(1, 0);
    public static final Position DOWN_LEFT = new Position(-1, 1);
    public static final Position DOWN_RIGHT = new Position(1, 1);
    public static final Position UP_LEFT = new Position(-1, -1);
    public static final Position UP_RIGHT = new Position(1, -1);

    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Position position = (Position) obj;
        if (this.x == position.x && this.y == position.y) {
            return true;
        } else {
            return false;
        }
    }

    public Position add(Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }
}
