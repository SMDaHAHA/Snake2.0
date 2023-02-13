package org.mohnatiy.snake;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Змейка");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}