package org.mohnatiy.snake.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.mohnatiy.snake.GamePanel.*;

public class Food {

    Random random = new Random();
    Point pos = new Point();
    Image image;
    private boolean isEaten = false;

    public Food() {
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        try {
            temp = ImageIO.read(new File("src/main/resources/images/tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = temp.getSubimage(160, 385, 60, 55);
        pos.setLocation(1, 1);
    }

    public void update(Snake snake) {
        if (isEaten) {
            do {
                pos.setLocation(
                        random.nextInt(0, 20),
                        random.nextInt(0, 20));
                isEaten = false;
            } while (snake.getBody().stream().anyMatch(bodyNode -> bodyNode.equals(pos)));
        }
    }

    public void eat() {
        isEaten = true;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, (pos.x * tileSize), (pos.y * tileSize) + 100, tileSize, tileSize, null);
    }
}
