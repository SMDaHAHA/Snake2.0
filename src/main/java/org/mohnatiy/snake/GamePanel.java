package org.mohnatiy.snake;

import org.mohnatiy.snake.entities.Food;
import org.mohnatiy.snake.entities.Snake;
import org.mohnatiy.snake.input_handlers.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable {
    public static final int screenWidth = 800, screenHeight = 900;
    public static final int FPS = 6;
    public static final int tileSize = 40;
    public static int snakeStartLength = 4;
    public static GameState gameState = GameState.PLAY;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Snake snake;
    Food food;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);

        initGame();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / (double) FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && !gameThread.isInterrupted()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

        System.exit(0);
    }

    public void update() {
        switch (gameState) {
            case PLAY -> {
                snake.update(keyH, food);
                food.update(snake);
            }
            case GAMEOVER -> System.out.println("suck some dick");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (gameState) {
            case PLAY -> {
                snake.draw(g2);
                food.draw(g2);
                g2.setFont(new Font("Times new Roman", Font.BOLD, 50));
                g2.drawString(String.format("СЧЁТ: %d", snake.getScore()), 50, 50);
            }
            case GAMEOVER -> {
                snake.draw(g2);
                g2.setColor(Color.red);
                g2.setFont(new Font("Times new Roman", Font.BOLD, 50));
                g2.drawString("Игра закончилась", screenWidth >> 1, screenHeight >> 1);
            }
        }

        g2.dispose();
    }

    public void initGame() {
        snake = new Snake(snakeStartLength);
        food = new Food();
    }
}