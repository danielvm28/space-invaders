package model;

import control.MainWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Avatar {
    private GraphicsContext gc;
    private Canvas canvas;
    private int x = 250;
    private int y = 250;
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private Image image;

    public Avatar(Canvas canvas, int width, int height) {
        this.canvas=canvas;
        gc = canvas.getGraphicsContext2D();
        this.width = width;
        this.height = height;
        centerX = (width / 2) + x;
        centerY = (height / 2) + y;
        image = new Image("images/space_ship.png", width, height, true, true);
    }

    public void paint(){
        gc.drawImage(image, x, y);
//        if (MainWindow.FRAMES % 1000 == 0) {
//            x = 50;
//            y = 50;
//        }
    }

    public void moveX(int i) {
        x+=i;
    }

    public void moveY(int i) {
        y+=i;
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
