package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Avatar extends HitBox{
    private Image image;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean moveUp;
    private boolean moveDown;
    private boolean limitedRightMovement;
    private boolean limitedLeftMovement;
    private int speed;
    private ArrayList<Bullet> bullets;
    private boolean alive;

    public Avatar(Canvas canvas, int x, int y, int width, int height) {
        super(canvas, x, y, width, height);
        bullets = new ArrayList<>();
        moveRight = false;
        moveLeft = false;
        moveUp = false;
        moveDown = false;
        limitedLeftMovement = false;
        limitedRightMovement = false;
        alive = true;
        speed = 2;
        image = new Image("images/space_ship.png", width, height, true, true);
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

    public boolean isLimitedRightMovement() {
        return limitedRightMovement;
    }

    public void setLimitedRightMovement(boolean limitedRightMovement) {
        this.limitedRightMovement = limitedRightMovement;
    }

    public boolean isLimitedLeftMovement() {
        return limitedLeftMovement;
    }

    public void setLimitedLeftMovement(boolean limitedLeftMovement) {
        this.limitedLeftMovement = limitedLeftMovement;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
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

        if (moveRight && !moveLeft && !limitedRightMovement) {
            moveX(speed);
        } else if(!moveRight && moveLeft && !limitedLeftMovement){
            moveX(-speed);
        }

        getGc().drawImage(image, getX(), getY());
    }

    public void moveX(int i) {
        setX(getX() + i);
        setCenterX(getCenterX() + i);
    }

    public void moveY(int i) {
        setY(getY() + i);
        setCenterY(getCenterY() + i);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public void resetBullets() {
        bullets.clear();
    }
}
