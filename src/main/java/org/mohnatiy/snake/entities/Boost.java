package org.mohnatiy.snake.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.mohnatiy.snake.GamePanel.*;

public class Boost {

    Random random = new Random();
    Point pos = new Point();
    Image image;
    private boolean isReadyToEat = true;
    private long secondsAfterEated = 0;

    public Boost() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            temp = ImageIO.read(new File("src/main/resources/images/energyMonster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = temp;

        pos.setLocation(random.nextInt(0, 20), random.nextInt(0, 20));
    }

    public void update(Snake snake, Food food, long secondsPassed) {
        if (isReadyToEat) {
            do {
                pos.setLocation(
                        random.nextInt(0, 20),
                        random.nextInt(0, 20));
                isReadyToEat = false;
                secondsAfterEated = secondsPassed;
            } while (snake.getBody().stream().anyMatch(bodyNode -> bodyNode.pos.equals(pos)) || food.pos.equals(pos));
        } else {
            if (secondsPassed - 3 > secondsAfterEated) {
                isReadyToEat = true;
                secondsAfterEated = secondsPassed;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, (pos.x * tileSize), (pos.y * tileSize) + 100, tileSize, tileSize, null);
    }

    public void eat() {
        isReadyToEat = true;
    }
}
