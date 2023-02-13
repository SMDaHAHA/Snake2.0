package org.mohnatiy.snake;

public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);
    private final int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public int get() {
        return direction;
    }
}
