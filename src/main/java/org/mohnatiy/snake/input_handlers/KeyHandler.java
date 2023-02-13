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
        if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }
}