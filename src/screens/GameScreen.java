package screens;

import control.MainWindow;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Avatar;
import model.Enemy;
import model.Bullet;
import model.ScoreData;

import java.util.ArrayList;

public class GameScreen extends BaseScreen{
	// Objects in the scenario
	private int NUM_ENEMIES;
	private int NUM_ROWS;
	public int LEVEL;
	private Image background;
	private Avatar avatar;
	private boolean movingRight;
	private boolean shooting;
	private ArrayList<Enemy> enemies;
	private boolean playing;
	private int bulletTimer;
	private int score;
	private Label scoreLabel;
	private TransitionScreen transitionScreen;
	private ArrayList<Bullet> enemyBullets;
	private double speedX;
	private ScoreData scoreData;

	public GameScreen(Canvas canvas, Label scoreLabel, TransitionScreen transitionScreen, int speedX) {
		super(canvas);
		this.scoreLabel = scoreLabel;
		this.transitionScreen = transitionScreen;
		this.speedX = speedX;
		NUM_ENEMIES = 5;
		NUM_ROWS = 1;
		LEVEL = 1;
		enemies = new ArrayList<>(NUM_ENEMIES);
		movingRight = true;
		bulletTimer = 0;
		playing = true;
		shooting = false;
		enemyBullets = new ArrayList<>();

		scoreData = new ScoreData();
		scoreData.loadJSON();

		background = new Image("images/pixel_space.png", canvas.getWidth(), canvas.getHeight(), true, true);
		avatar = new Avatar(canvas, (int) Math.round(canvas.getWidth() / 2) - 30, 800, 60, 100);
		generateEnemies(speedX);
		startBulletTimer();
	}

	private void startBulletTimer() {
		new Thread(() -> {
			while (playing) {
				if (bulletTimer > 0) {
					bulletTimer--;
				}

				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void resetGame() {
		if (avatar.isAlive()) {
			if ((LEVEL + 1) <= 3) {
				NUM_ENEMIES += 5;
				NUM_ROWS++;
			} else {
				speedX+=0.3;
			}
		} else {
			score = 0;
			LEVEL = 0;
			NUM_ENEMIES = 5;
			NUM_ROWS = 1;
			speedX = 2;
			Platform.runLater(() -> scoreLabel.setText(score + ""));
		}

		avatar = new Avatar(canvas, (int) Math.round(canvas.getWidth() / 2) - 30, 800, 60, 100);

		avatar.resetBullets();
		enemyBullets.clear();
		enemies = new ArrayList<>(NUM_ENEMIES);
		generateEnemies(speedX);
		MainWindow.SCREEN = 0;
		LEVEL++;

		movingRight = true;
		bulletTimer = 0;
		playing = true;
		shooting = false;

		startBulletTimer();
	}

	public void generateEnemies(double speedX) {
		int yPos = 40;

		for (int i = 0, n = 0; i < NUM_ROWS; i++) {
			int xPos = (int) ((canvas.getWidth() / 2) - 280);

			for (int j = 0; j < 5 && n < NUM_ENEMIES; j++, n++) {
				enemies.add(new Enemy(canvas, xPos, yPos, 50, 50, (i % 3) + 1, speedX));
				new Thread(enemies.get(n)).start();

				xPos += 80;
			}

			yPos += 80;
		}
	}

	@Override
	public void paint() {
		// Check winning condition
		if (enemies.size() == 0) {
			playing = false;
			pauseGame();
			MainWindow.SCREEN = 2;
			transitionScreen.setWon(true);
		} else if (!avatar.isAlive()  || (enemies.get(enemies.size() - 1).getY() + enemies.get(enemies.size() - 1).getHeight()) >= avatar.getY()) {
			avatar.setAlive(false);
			playing = false;
			pauseGame();
			MainWindow.SCREEN = 2;
			transitionScreen.setWon(false);
			transitionScreen.setCurrentScore(score + "");
			transitionScreen.setHighScore(scoreData.getHighScore() + "");

			// Update scoreboard
			scoreData.updateScoreboard(score);
			scoreData.saveJSON();
		}

		gc.drawImage(background, 0, 0);

		avatar.paint();

		for (int i = 0; i < avatar.getBullets().size(); i++) {
			avatar.getBullets().get(i).paint();

			if (avatar.getBullets().get(i).getY() < 0) {
				avatar.getBullets().remove(i);
				i--;
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getTimer() == 0) {
				enemyBullets.add(new Bullet(canvas, enemies.get(i).getX(), enemies.get(i).getY(), 30, 30, enemies.get(i).getWidth()/2, true));
			}

			enemies.get(i).paint();
		}

		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).paint();

			if (enemyBullets.get(i).getY() > canvas.getHeight()) {
				enemyBullets.remove(i);
				i--;
			}
		}

		checkCollisions();

		checkBarriers();
	}

	public void checkCollisions() {
		// Calculate box distance
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < avatar.getBullets().size(); j++) {
				// Compare
				Enemy b = enemies.get(i);
				Bullet bullet = avatar.getBullets().get(j);

				// Calculate radius distance
				double xDistance = Math.abs(b.getCenterX() - bullet.getCenterX());
				double yDistance = Math.abs(b.getCenterY() - bullet.getCenterY());

				double totalDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

				// Collision happened (Takes into account a 10 pixel tolerance)
				if (totalDistance <= ((b.getHeight() / 2) + (bullet.getHeight() / 2)) - 10) {
					enemies.get(i).setAlive(false);
					enemies.remove(i);
					avatar.removeBullet(bullet);

					score += 100;
					Platform.runLater(() -> scoreLabel.setText(score + ""));

					return;
				}
			}
		}

		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet enemyBullet = enemyBullets.get(i);

			// Calculate radius distance
			double xDistance = Math.abs(avatar.getCenterX() - enemyBullet.getCenterX());
			double yDistance = Math.abs(avatar.getCenterY() - enemyBullet.getCenterY());

			double totalDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

			// Collision happened (Takes into account a 10 pixel tolerance)
			if (totalDistance <= ((avatar.getHeight() / 2) + (enemyBullet.getHeight() / 2)) - 10) {
				avatar.setAlive(false);
				return;
			}
		}
	}

	public void checkBarriers() {
		boolean touchedBarrier = false;

		// Detect if an enemy touched a barrier
		for (int i = 0; i < enemies.size(); i++) {
			double center = enemies.get(i).getCenterX();
			double radius = enemies.get(i).getCenterX() - enemies.get(i).getX();

			// Uses movingRight variable to determine the general direction of the enemies.
			// This helps with avoiding repeated collisions with the limits of the map
			if (movingRight && center + radius >= canvas.getWidth()) {
				touchedBarrier = true;
				movingRight = false;
				break;
			} else if (!movingRight && center - radius <= 0) {
				touchedBarrier = true;
				movingRight = true;
				break;
			}
		}

		// Changes direction of every enemy if a barrier was touched
		if (touchedBarrier) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).changeDirection();
			}
		}

		// Detect if the player touched a barrier
		double avatarRadius = avatar.getCenterX() - avatar.getX();
		double avatarCenter = avatar.getCenterX();

		// Limit the avatar's movements
		if (avatarCenter + avatarRadius >= canvas.getWidth()) {
			avatar.setLimitedRightMovement(true);
		} else if (avatarCenter - avatarRadius <= 0) {
			avatar.setLimitedLeftMovement(true);
		} else {
			avatar.setLimitedLeftMovement(false);
			avatar.setLimitedRightMovement(false);
		}
	}

	@Override
	public void onKey(KeyEvent e) {
		if (e.getCode().equals(KeyCode.A)){
			avatar.setMoveLeft(true);
		} else if (e.getCode().equals(KeyCode.D)){
			avatar.setMoveRight(true);
		} else if(e.getCode().equals(KeyCode.SPACE)) {
			// Limits the capacity of shooting
			if (bulletTimer == 0 && !shooting) {
				avatar.addBullet(new Bullet(canvas, avatar.getX(), avatar.getY(), 30, 30, avatar.getWidth()/2, false));
				bulletTimer++;
				shooting = true;
			}
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
		} else if (e.getCode().equals(KeyCode.D)){
			avatar.setMoveRight(false);
		} else if (e.getCode().equals(KeyCode.RIGHT)) {
			avatar.setMoveRight(false);
		} else if (e.getCode().equals(KeyCode.LEFT)) {
			avatar.setMoveLeft(false);
		} else if (e.getCode().equals(KeyCode.SPACE)) {
			shooting = false;
		}
	}

	public void pauseGame() {
		avatar.setMoveRight(false);
		avatar.setMoveLeft(false);
		avatar.setMoveDown(false);
		avatar.setMoveUp(false);

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).setAlive(false);
		}
	}

	public void resumeGame() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).setAlive(true);
			new Thread(enemies.get(i)).start();
		}
	}
}
