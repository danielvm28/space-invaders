package screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Avatar;
import model.Enemy;
import model.HitBox;
import model.Bullet;

import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends BaseScreen{
	// Objects in the scenario
	private final int NUM_ENEMIES = 15;
	private final int NUM_ROWS = NUM_ENEMIES / 5;
	private Avatar avatar;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;

	public GameScreen(Canvas canvas) {
		super(canvas);
		bullets = new ArrayList<>();
		enemies = new ArrayList<>(NUM_ENEMIES);

		avatar = new Avatar(canvas, (int) Math.round(canvas.getWidth() / 2) - 90 / 2, 500, 90, 90);
		generateEnemies();
	}

	public void generateEnemies() {
		int yPos = 40;

		for (int i = 0, n = 0; i < NUM_ROWS; i++) {
			int xPos = (int) ((canvas.getWidth() / 2) - 280);

			for (int j = 0; j < 5 && n < NUM_ENEMIES; j++, n++) {
				enemies.add(new Enemy(canvas, xPos, yPos, 50, 50, (i % 3) + 1));
				new Thread(enemies.get(n)).start();

				xPos += 80;
			}

			yPos += 80;
		}
	}

	@Override
	public void paint() {
//		gc.setFill(Color.BLACK);
//		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.drawImage(new Image("images/pixel_space.png", canvas.getWidth(), canvas.getHeight(), true, true), 0, 0);

		avatar.paint();

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint();

			if (bullets.get(i).getY() < 0) {
				bullets.remove(i);
				i--;
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).paint();
		}

		// Calculate box distance
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {
				// Compare
				Enemy b = enemies.get(i);
				Bullet bullet = bullets.get(j);

				// Calculate radius distance
				double xDistance = Math.abs(b.getCenterX() - bullet.getCenterX());
				double yDistance = Math.abs(b.getCenterY() - bullet.getCenterY());

				double totalDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

				// Collision happened (Takes into account a 10 pixel tolerance)
				if (totalDistance <= ((b.getHeight() / 2) + (bullet.getHeight() / 2)) - 10) {
					enemies.get(i).setAlive(false);
					enemies.remove(i);
					bullets.remove(j);
					return;
				}
			}
		}

		boolean touchedBarrier = false;

		// Detect if an enemy touched a barrier
		for (int i = 0; i < enemies.size(); i++) {
			int center = enemies.get(i).getCenterX();
			int radius = enemies.get(i).getCenterX() - enemies.get(i).getX();

			if (center + radius >= canvas.getWidth() || center - radius <= 0) {
				touchedBarrier = true;
				break;
			}
		}

		// Changes direction of every enemy if a barrier was touched
		if (touchedBarrier) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).changeDirection();
			}
		}

	}

	@Override
	public void onKey(KeyEvent e) {
		if (e.getCode().equals(KeyCode.A)){
			avatar.setMoveLeft(true);
		} else if (e.getCode().equals(KeyCode.W)){
			avatar.setMoveUp(true);
		} else if (e.getCode().equals(KeyCode.S)){
			avatar.setMoveDown(true);
		} else if (e.getCode().equals(KeyCode.D)){
			avatar.setMoveRight(true);
		} else if(e.getCode().equals(KeyCode.SPACE)) {
			bullets.add(new Bullet(canvas, avatar.getX(), avatar.getY(), 30, 30, avatar.getWidth()/2));
		} else if (e.getCode().equals(KeyCode.RIGHT)) {
			avatar.setMoveRight(true);
		} else if (e.getCode().equals(KeyCode.LEFT)) {
			avatar.setMoveLeft(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getCode().equals(KeyCode.A)){
			avatar.setMoveLeft(false);
		} else if (e.getCode().equals(KeyCode.W)){
			avatar.setMoveUp(false);
		} else if (e.getCode().equals(KeyCode.S)){
			avatar.setMoveDown(false);
		} else if (e.getCode().equals(KeyCode.D)){
			avatar.setMoveRight(false);
		} else if (e.getCode().equals(KeyCode.RIGHT)) {
			avatar.setMoveRight(false);
		} else if (e.getCode().equals(KeyCode.LEFT)) {
			avatar.setMoveLeft(false);
		}
	}

	public void pauseAvatar() {
		avatar.setMoveRight(false);
		avatar.setMoveLeft(false);
		avatar.setMoveDown(false);
		avatar.setMoveUp(false);
	}
}
