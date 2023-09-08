package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controls implements KeyListener {

    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, ATTACK = 4;

    private final int[] controls;

    public Controls() {
        controls = new int[5];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        updateKey(e, 1);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateKey(e, 0);
    }

    private void updateKey(KeyEvent e, int value) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            controls[UP] = value;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            controls[DOWN] = value;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            controls[LEFT] = value;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            controls[RIGHT] = value;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            controls[ATTACK] = value;
        }
    }

    public int[] getControls() {
        if (false) {
            StringBuilder s = new StringBuilder("[ ");
            for (int x : controls) {
                s.append(x).append(" ");
            }
            s.append("]");
            System.out.println(s);
        }
        return controls;
    }
}
