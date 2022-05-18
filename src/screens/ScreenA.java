package screens;

import control.MainWindow;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Avatar;
import model.HitBox;
import model.Bullet;

import java.util.ArrayList;

public class ScreenA extends BaseScreen{

	// Objects in the scenario
	private Avatar avatar;
	private ArrayList<Bullet> bullets;
	private ArrayList<HitBox> hitBoxes;
	private int spdAvatar = 5;

	public ScreenA(Canvas canvas) {
		super(canvas);
		avatar = new Avatar(canvas, 100, 100);
		bullets = new ArrayList<>();
		hitBoxes = new ArrayList<>();

		hitBoxes.add(new HitBox(canvas, 500, 200, 30, 30));
	}

	@Override
	public void paint() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());

		avatar.paint();

		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).paint();

			if (bullets.get(i).getY() < 0) {
				bullets.remove(i);
				i--;
			}
		}

		for (int i = 0; i < hitBoxes.size(); i++) {
			hitBoxes.get(i).paint();
		}

		// Calculate box distance
		for (int i = 0; i < hitBoxes.size(); i++) {
			for (int j = 0; j < bullets.size(); j++) {
				// Compare
				HitBox b = hitBoxes.get(i);
				Bullet bullet = bullets.get(j);

				// Calculate radius distance
				double xDistance = Math.abs(b.getCenterX() - bullet.getCenterX());
				double yDistance = Math.abs(b.getCenterY() - bullet.getCenterY());

				double totalDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

				if (totalDistance <= (b.getHeight() / 2) + (bullet.getHeight() / 2)) {
					hitBoxes.remove(i);
					bullets.remove(j);
					return;
				}
			}
		}
	}

	@Override
	public void onClick(MouseEvent e) {
		MainWindow.SCREEN = (MainWindow.SCREEN + 1) % 2;
	}

	@Override
	public void onKey(KeyEvent e) {
		if (e.getCode().equals(KeyCode.A)){
			avatar.moveX(-spdAvatar);
			//spdAvatar++;
		} else if (e.getCode().equals(KeyCode.W)){
			avatar.moveY(-spdAvatar);
			//spdAvatar++;
		} else if (e.getCode().equals(KeyCode.S)){
			avatar.moveY(spdAvatar);
			//spdAvatar++;
		} else if (e.getCode().equals(KeyCode.D)){
			avatar.moveX(spdAvatar);
			//spdAvatar++;
		} else if(e.getCode().equals(KeyCode.SPACE)) {
			bullets.add(new Bullet(canvas, avatar.getX(), avatar.getY(), 30, 30, avatar.getWidth()/2));
		}
	}

}
