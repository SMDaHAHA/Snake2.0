package org.mohnatiy.snake.entities;

import org.mohnatiy.snake.Direction;
import org.mohnatiy.snake.input_handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static org.mohnatiy.snake.GamePanel.*;

public class Snake {
    private final Image[] images = new Image[4];
    private final LinkedList<Point> body = new LinkedList<>();
    private Direction direction = Direction.UP;
    private final int startLength;
    Food food;

    public Snake(int sl) {
        startLength = sl;

        BufferedImage tileset = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            tileset = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/tileset.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int tileSize = 72;
        // head
        images[0] = tileset.getSubimage(0, 0, tileSize, tileSize);
        // straight body
        images[1] = tileset.getSubimage(0, tileSize, tileSize, tileSize);
        // curved body
        images[2] = tileset.getSubimage(0, tileSize * 3, tileSize, tileSize);
        // tail
        images[3] = tileset.getSubimage(0, tileSize * 2, tileSize, tileSize);
        int x = 10;
        int y = 7;
        for (int i = 0; i < startLength; i++) {
            body.add(new Point(x, y));
            y++;
        }
    }

    public void update(KeyHandler keyH, Food food) {
        // UP
        if (keyH.isUpPressed()) {
            if (direction != Direction.DOWN) {
                direction = Direction.UP;
            }
        }
        // RIGHT
        if (keyH.isRightPressed()) {
            if (direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
        }
        // DOWN
        if (keyH.isDownPressed()) {
            if (direction != Direction.UP) {
                direction = Direction.DOWN;
            }
        }
        // LEFT
        if (keyH.isLeftPressed()) {
            if (direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            }
        }
        Point nextPoint = getNextPoint();
        // либо хаваем яблоко либо отрезаем хвост
        if (nextPoint.equals(food.pos)) {
            food.eat();
        } else {
            body.removeLast();
        }
        body.addFirst(nextPoint);
    }

    public void draw(Graphics2D g2) {
        body.forEach(bodyNode -> g2.drawImage(getImage(bodyNode), bodyNode.x * tileSize, (bodyNode.y * tileSize) + 100, tileSize, tileSize, null));
    }

    public int getScore() {
        return body.size() - startLength;
    }

    public ArrayList<Point> getBody() {
        return new ArrayList<>(body);
    }

    private Image getImage(Point bodyNode) {
        if (body.indexOf(bodyNode) == 0) {
            return images[0];
        } else if (body.indexOf(bodyNode) == body.size() - 1) {
            return images[3];
        } else {
            return images[1];
        }
    }

    private Point getNextPoint() {
        Point curHead = new Point();
        curHead.setLocation(body.peekFirst());
        Point nextHead = new Point();
        switch (direction) {
            case UP -> {
                if (curHead.y == 0) {
                    nextHead.setLocation(curHead.x, 19);
                } else {
                    nextHead.setLocation(curHead.x, curHead.y - 1);
                }
            }
            case RIGHT -> {
                if (curHead.x == 19) {
                    nextHead.setLocation(0, curHead.y);
                } else {
                    nextHead.setLocation(curHead.x + 1, curHead.y);
                }
            }
            case DOWN -> {
                if (curHead.y == 19) {
                    nextHead.setLocation(curHead.x, 0);
                } else {
                    nextHead.setLocation(curHead.x, curHead.y + 1);
                }
            }
            case LEFT -> {
                if (curHead.x == 0) {
                    nextHead.setLocation(19, curHead.y);
                } else {
                    nextHead.setLocation(curHead.x - 1, curHead.y);
                }
            }
        }
        return nextHead;
    }
}
