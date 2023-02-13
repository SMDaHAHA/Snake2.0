package org.mohnatiy.snake.entities;

import org.mohnatiy.snake.Direction;
import org.mohnatiy.snake.input_handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import static org.mohnatiy.snake.GamePanel.*;

public class Snake {
    private final Image[] images = new Image[4];
    private final LinkedList<Point> body = new LinkedList<>();
    private Direction direction = Direction.UP;
    private final int startLength;

    public Snake(int sl) throws IOException {
        startLength = sl;
        BufferedImage tileset = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
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

    public void update(KeyHandler keyH) {
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
                System.out.println("direction is set to right");
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
        Point curHead = new Point();
        Point newHead = new Point();
        curHead.setLocation(body.peekFirst());
        switch (direction) {
            case UP -> newHead.setLocation(curHead.x, curHead.y - 1);
            case RIGHT -> newHead.setLocation(curHead.x + 1, curHead.y);
            case DOWN -> newHead.setLocation(curHead.x, curHead.y + 1);
            case LEFT -> newHead.setLocation(curHead.x - 1, curHead.y);
        }
        body.removeLast();
        body.addFirst(newHead);
    }

    public void draw(Graphics2D g2) {
        body.forEach(bodyNode -> g2.drawImage(getImage(bodyNode), bodyNode.x * tileSize, bodyNode.y * tileSize, tileSize, tileSize, null));
    }

    public int getScore() {
        return body.size() - startLength;
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
}
