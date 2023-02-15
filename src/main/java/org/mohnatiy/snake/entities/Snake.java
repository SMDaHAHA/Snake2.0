package org.mohnatiy.snake.entities;

import org.mohnatiy.snake.Direction;
import org.mohnatiy.snake.input_handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static org.mohnatiy.snake.GamePanel.*;

public class Snake {
    private final Image[] imagesUp = new Image[4];
    private final Image[] imagesDown = new Image[4];
    private final Image[] imagesRight = new Image[4];
    private final Image[] imagesLeft = new Image[4];
    private final Image[] imagesCurved = new Image[4];
    private final LinkedList<SnakeNode> body = new LinkedList<>();
    private Direction direction = Direction.UP;
    private final int startLength;
    private boolean dead;

    public Snake(int sl) {
        startLength = sl;

        BufferedImage tileSet = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            tileSet = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("images/tileset.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int tileSize = 75;
        imagesUp[0] = tileSet.getSubimage(0, 0, tileSize, tileSize);
        imagesUp[1] = tileSet.getSubimage(0, tileSize, tileSize, tileSize);
        imagesUp[2] = tileSet.getSubimage(0, (tileSize * 2), tileSize, tileSize);

        imagesDown[0] = tileSet.getSubimage(tileSize, (tileSize * 4), tileSize, tileSize);
        imagesDown[1] = tileSet.getSubimage(tileSize, (tileSize * 3), tileSize, tileSize);
        imagesDown[2] = tileSet.getSubimage(tileSize, (tileSize * 2), tileSize, tileSize);

        imagesRight[0] = tileSet.getSubimage((tileSize * 5), 0, tileSize, tileSize);
        imagesRight[1] = tileSet.getSubimage((tileSize * 4), 0, tileSize, tileSize);
        imagesRight[2] = tileSet.getSubimage((tileSize * 3), 0, tileSize, tileSize);


        imagesLeft[0] = tileSet.getSubimage(tileSize, tileSize, tileSize, tileSize);
        imagesLeft[1] = tileSet.getSubimage((tileSize * 2), tileSize, tileSize, tileSize);
        imagesLeft[2] = tileSet.getSubimage((tileSize * 3), tileSize, tileSize, tileSize);

        imagesCurved[0] = tileSet.getSubimage((tileSize * 4), tileSize, tileSize, tileSize);
        imagesCurved[1] = tileSet.getSubimage(0, (tileSize * 4), tileSize, tileSize);
        imagesCurved[2] = tileSet.getSubimage(0, (tileSize * 3), tileSize, tileSize);
        imagesCurved[3] = tileSet.getSubimage((tileSize * 2), 0, tileSize, tileSize);

        int x = 10;
        int y = 2;
        for (int i = 0; i < startLength; i++) {
            body.add(new SnakeNode(direction, new Point(x, y)));
            y++;
        }
    }

    public void update(KeyHandler keyH, Food food, Boost boost) {
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
        body.element().onLeaving = direction;
        Point nextPoint = getNextPoint();
        if (nextPoint.equals(boost.pos)) {
            boost.eat();
        }
        if (nextPoint.equals(food.pos)) {
            // пытаемся захавать яблоко
            food.eat();
        } else if (nextPoint.equals(boost.pos)) {
            // пытаемся захавать энергос
            boost.eat();
        } else {
            // ежели похавать всё-таки не удалось — отрезаем хвост
            body.removeLast();
        }
        if (body.stream().anyMatch(bodyNode -> bodyNode.pos.equals(nextPoint))) {
            dead = true;
        }
        body.addFirst(new SnakeNode(direction, nextPoint));
    }

    public void draw(Graphics2D g2) {
        body.forEach(bodyNode -> g2.drawImage(getImage(bodyNode), bodyNode.pos.getLocation().x * tileSize, (bodyNode.pos.getLocation().y * tileSize) + 100, tileSize, tileSize, null));
    }

    public int getScore() {
        return body.size() - startLength;
    }

    private Image getImage(SnakeNode node) {
        if (body.indexOf(node) == 0) {
            // бошки дымятся
            switch (node.onEntering) {
                case UP -> {
                    return imagesUp[0];
                }
                case RIGHT -> {
                    return imagesRight[0];
                }
                case DOWN -> {
                    return imagesDown[0];
                }
                case LEFT -> {
                    return imagesLeft[0];
                }
            }
        } else if (body.indexOf(node) == body.size() - 1) {
            // хвостов не оставляем
            switch (node.onLeaving) {
                case UP -> {
                    return imagesUp[2];
                }
                case RIGHT -> {
                    return imagesRight[2];
                }
                case DOWN -> {
                    return imagesDown[2];
                }
                case LEFT -> {
                    return imagesLeft[2];
                }
            }
        } else {
            // дороги...
            switch (node.onEntering) {
                case UP -> {
                    switch (node.onLeaving) {
                        case UP -> {
                            return imagesUp[1];
                        }
                        case RIGHT -> {
                            return imagesCurved[1];
                        }
                        case LEFT -> {
                            return imagesCurved[2];
                        }
                    }
                }
                case RIGHT -> {
                    switch (node.onLeaving) {
                        case UP -> {
                            return imagesCurved[3];
                        }
                        case RIGHT -> {
                            return imagesRight[1];
                        }
                        case DOWN -> {
                            return imagesCurved[2];
                        }
                    }
                }
                case DOWN -> {
                    switch (node.onLeaving) {
                        case RIGHT -> {
                            return imagesCurved[0];
                        }
                        case DOWN -> {
                            return imagesDown[1];
                        }
                        case LEFT -> {
                            return imagesCurved[3];
                        }
                    }
                }
                case LEFT -> {
                    switch (node.onLeaving) {
                        case UP -> {
                            return imagesCurved[0];
                        }
                        case DOWN -> {
                            return imagesCurved[1];
                        }
                        case LEFT -> {
                            return imagesLeft[1];
                        }
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<SnakeNode> getBody() {
        return new ArrayList<>(body);
    }

    private Point getNextPoint() {
        Point curHead = new Point();
        curHead.setLocation(body.element().pos);
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

    public boolean isDead() {
        return dead;
    }
}
