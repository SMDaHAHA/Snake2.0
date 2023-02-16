package org.mohnatiy.snake;

import org.mohnatiy.snake.entities.Boost;
import org.mohnatiy.snake.entities.Food;
import org.mohnatiy.snake.entities.Snake;
import org.mohnatiy.snake.input_handlers.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.mohnatiy.snake.Main.bg;

public class GamePanel extends JPanel implements Runnable {
    public static final int screenWidth = 800, screenHeight = 900;
    public static int FPS = 10;
    public static final int tileSize = 40;
    public static int snakeStartLength = 4;
    public static long secondsPassed = 0;
    public static GameState gameState = GameState.PLAY;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Snake snake;
    Food food;
    Boost boost;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);

        initGame();
    }

    public void setBackground() {
        setBackground(bg);
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
        long timer = 0;

        while (gameThread != null && !gameThread.isInterrupted()) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                secondsPassed = TimeUnit.SECONDS.convert(timer, TimeUnit.NANOSECONDS);
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
                snake.update(keyH, food, boost);
                food.update(snake);
                boost.update(snake, food, secondsPassed);
            }
            case GAMEOVER -> {
                if (keyH.isKeyPressed()) {
                    gameState = GameState.PLAY;
                    initGame();
                }
            }
        }
        if (snake.isDead()) gameState = GameState.GAMEOVER;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (gameState) {
            case PLAY -> {
                snake.draw(g2);
                food.draw(g2);
                boost.draw(g2);
                g2.setFont(new Font("Times new Roman", Font.BOLD, 50));
                g2.drawString(String.format("СЧЁТ: %d", snake.getScore()), 50, 50);
            }
            case GAMEOVER -> {
                snake.draw(g2);
                g2.setColor(Color.red);
                g2.setFont(new Font("Times new Roman", Font.BOLD, 50));
                g2.drawString("Игра закончилась", screenWidth / 3, 50);
                g2.drawString(String.format("СЧЁТ: %d", snake.getScore()), 35, 50);
            }
        }

        g2.dispose();
    }

    public void initGame() {
        snake = new Snake(snakeStartLength);
        food = new Food();
        boost = new Boost();
    }
}