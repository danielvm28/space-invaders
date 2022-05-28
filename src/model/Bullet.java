package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Bullet extends HitBox{
    private Image image;
    private int xAdjustment;
    private int speed;
    private boolean enemyBullet;

    public Bullet(Canvas canvas, double x, double y, int width, int height, int xAdjustment, boolean enemyBullet) {
        super(canvas, x + xAdjustment - width/2, y, width, height);
        this.speed = 5;
        this.xAdjustment = xAdjustment;
        this.enemyBullet = enemyBullet;

        if (!enemyBullet) {
            image = new Image("images/bullet.png", width, height, true, true);
        } else {
            image = new Image("images/enemy_bullet.png", width, height, true, true);
        }
    }

    public void paint() {
        getGc().drawImage(image, getX(), getY());

        if (enemyBullet) {
            setY(getY() + speed);
        } else {
            setY(getY() - speed);
        }

        setCenterX((getWidth() / 2) + getX());
        setCenterY((getHeight() / 2) + getY());
    }
}
