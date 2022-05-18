package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet extends Thread{
    private Canvas canvas;
    private GraphicsContext gc;
    private Image image;
    private int xAdjustment;
    private int x,y;
    private int speed;
    private int width;
    private int height;
    private int centerX;
    private int centerY;

    public Bullet(Canvas canvas, int x, int y, int width, int height, int xAdjustment) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.x = x + xAdjustment - width/2;
        this.y = y;
        this.speed = 3;
        this.xAdjustment = xAdjustment;
        this.width = width;
        this.height = height;
        centerX = (width / 2) + x;
        centerY = (height / 2) + y;
        image = new Image("images/bullet.png", width, height, true, true);
    }

    public void paint() {
        gc.drawImage(image, x, y);
        y-=speed;
        centerX = (width / 2) + x;
        centerY = (height / 2) + y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }
}
