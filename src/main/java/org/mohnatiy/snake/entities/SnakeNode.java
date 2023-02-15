package org.mohnatiy.snake.entities;

import org.mohnatiy.snake.Direction;

import java.awt.*;

public class SnakeNode {
    final Direction onEntering;
    final Point pos;
    Direction onLeaving;

    public SnakeNode(Direction onEntering, Point pos) {
        this.onEntering = onEntering;
        this.pos = pos;
        this.onLeaving = onEntering;
    }
}
