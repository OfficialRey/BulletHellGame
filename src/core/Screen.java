package core;

import entity.explosion.Explosion;
import sprites.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class Screen extends JFrame {

    private Graphics graphics;
    private BufferStrategy bs;

    public Screen() {
        setSize(1920, 1080);
        setName("Insane Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setFullScreen(false);

        setVisible(true);

        createBuffer();
    }

    private void createBuffer() {
        if (bs == null) {
            createBufferStrategy(3);
        }
    }

    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        } else {
            setExtendedState(JFrame.NORMAL);
            setUndecorated(false);
        }
    }

    public void drawImage(BufferedImage image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }

    public void drawSprite(Sprite sprite) {
        graphics.drawImage(sprite.getImage(), sprite.getPosition().getIntX(), sprite.getPosition().getIntY(), null);
    }

    public void drawExplosion(Explosion explosion) {
        graphics.drawImage(explosion.getImage(), explosion.getPosition().getIntX(), explosion.getPosition().getIntY(), null);
    }

    public void startRender() {
        bs = getBufferStrategy();
        graphics = bs.getDrawGraphics();
        //graphics.clearRect(0, 0, 1920, 1080);
    }

    public void stopRender() {
        if (bs != null) {
            graphics.dispose();
            bs.show();
        }
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    public void animateClear() {
        if (true) {
            return;
        }
        BufferedImage content = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < content.getHeight() - 1; y += 5) {
            for (int x = 0; x < content.getWidth() - 1; x++) {
                content.setRGB(x, y, 0);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startRender();
            drawImage(content.getSubimage(0, 0, content.getWidth(), y + 1), 0, 0);
            stopRender();
        }
    }
}