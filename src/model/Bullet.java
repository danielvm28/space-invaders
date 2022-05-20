package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet extends HitBox{
    private Image image;
    private int xAdjustment;
    private int speed;

    public Bullet(Canvas canvas, int x, int y, int width, int height, int xAdjustment) {
        super(canvas, x + xAdjustment - width/2, y, width, height);
        this.speed = 20;
        this.xAdjustment = xAdjustment;
        image = new Image("images/bullet.png", width, height, true, true);
    }

    public void paint() {
        getGc().drawImage(image, getX(), getY());
        setY(getY() - speed);
        setCenterX((getWidth() / 2) + getX());
        setCenterY((getHeight() / 2) + getY());
    }
}
