package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Enemy extends HitBox implements Runnable{
    private int speedX;
    private int speedY;
    private Image image;
    private boolean isAlive;
    private boolean moveRight;

    public Enemy(Canvas canvas, int x, int y, int width, int height, int imgNum) {
        super(canvas, x, y, width, height);
        isAlive = true;
        moveRight = true;
        speedX = 2;
        speedY = 10;
        image = new Image("images/enemy" + imgNum + ".png", width, height, true, true);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void paint() {
        getGc().drawImage(image, getX(), getY());
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
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
