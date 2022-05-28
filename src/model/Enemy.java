package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Enemy extends HitBox implements Runnable{
    private double speedX;
    private int speedY;
    private Image image;
    private boolean isAlive;
    private boolean moveRight;
    private int timer;

    public Enemy(Canvas canvas, int x, int y, int width, int height, int imgNum, double speedX) {
        super(canvas, x, y, width, height);
        timer = (int) (Math.random() * (6)) + 2;
        isAlive = true;
        moveRight = true;
        this.speedX = speedX;
        speedY = 20;
        image = new Image("images/enemy" + imgNum + ".png", width, height, true, true);

        new Thread(() -> {
            while (isAlive) {
                if (timer != 0) {
                    timer--;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void paint() {
        getGc().drawImage(image, getX(), getY());

        if (timer == 0) {
            timer = (int) (Math.random() * (6)) + 2;
        }
    }

    /**
     * Changes the direction of the movement and moves down according to speed
     */
    public void changeDirection() {
        moveRight = !moveRight;
        setY(getY() + speedY);
        setCenterY(getCenterY() + speedY);

        // Move to avoid collision with the limits more than once
        if (moveRight) {
            setX(getX() + speedX);
            setCenterX(getCenterX() + speedX);
        } else {
            setX(getX() - speedX);
            setCenterX(getCenterX() - speedX);
        }
    }

    @Override
    public void run() {
        while (isAlive()) {

            if (moveRight) {
                setX(getX() + speedX);
                setCenterX(getCenterX() + speedX);
            } else {
                setX(getX() - speedX);
                setCenterX(getCenterX() - speedX);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
