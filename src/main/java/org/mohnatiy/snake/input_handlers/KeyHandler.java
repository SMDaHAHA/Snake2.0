package org.mohnatiy.snake.input_handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean upPressed, rightPressed, downPressed, leftPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean isUpPressed() {
        boolean temp = upPressed;
        upPressed = false;
        return temp;
    }

    public boolean isRightPressed() {
        boolean temp = rightPressed;
        rightPressed = false;
        return temp;
    }

    public boolean isDownPressed() {
        boolean temp = downPressed;
        downPressed = false;
        return temp;
    }

    public boolean isLeftPressed() {
        boolean temp = leftPressed;
        leftPressed = false;
        return temp;
    }
}