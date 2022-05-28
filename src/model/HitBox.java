package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HitBox {
    private Canvas canvas;
    private GraphicsContext gc;

    private double x;
    private double y;
    private int width;
    private int height;
    private double centerX;
    private double centerY;

    public HitBox(Canvas canvas, double x, double y, int width, int height) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        centerX = ((double) width / 2.0) + x;
        centerY = ((double) height / 2.0) + y;
    }

    public void paint() {
        gc.setFill(Color.WHITE);
        gc.fillRect(x, y, width, height);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
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

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }
}
