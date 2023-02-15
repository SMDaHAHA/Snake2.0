package org.mohnatiy.snake;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final Color bg = new Color(252, 232, 73);
    public static void main(String[] args) {
        JFrame window = new JFrame("Змейка");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        gamePanel.setBackground(bg);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}