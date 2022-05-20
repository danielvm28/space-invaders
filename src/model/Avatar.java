package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Avatar extends HitBox{
    private Image image;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean moveUp;
    private boolean moveDown;
    private int speed;

    public Avatar(Canvas canvas, int x, int y, int width, int height) {
        super(canvas, x, y, width, height);
        moveRight = false;
        moveLeft = false;
        moveUp = false;
        moveDown = false;
        speed = 5;
        image = new Image("images/space_ship.png", width, height, true, true);
    }

    /**
     * Paints the avatar depending on movement booleans
     */
    public void paint(){
        if (moveDown && !moveUp) {
            moveY(speed);
        } else if(!moveDown && moveUp){
            moveY(-speed);
        }

        if (moveRight && !moveLeft) {
            moveX(speed);
        } else if(!moveRight && moveLeft){
            moveX(-speed);
        }

        getGc().drawImage(image, getX(), getY());
    }

    public void moveX(int i) {
        setX(getX() + i);
    }

    public void moveY(int i) {
        setY(getY() + i);
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }
}
