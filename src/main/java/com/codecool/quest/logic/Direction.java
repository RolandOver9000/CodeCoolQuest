package com.codecool.quest.logic;

public enum Direction {
    UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(0, 1),
    RIGHT_UP(1, -1), RIGHT_DOWN(1, 1), LEFT_UP(-1, -1), LEFT_DOWN(-1, 1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
