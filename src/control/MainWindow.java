package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import screens.BaseScreen;
import screens.GameScreen;
import screens.PauseScreen;

public class MainWindow implements Initializable {
	public static int SCREEN = 0;

	@FXML
	private Canvas canvas;

	@FXML
	private Button pauseBTN;

	private ArrayList<BaseScreen> screens;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pauseBTN.setGraphic(new ImageView(new Image("images/pause.png",40, 40, true, true)));
		screens = new ArrayList<>();
		screens.add(new GameScreen(canvas));
		screens.add(new PauseScreen(canvas));
		canvas.setFocusTraversable(true);
		
		new Thread(() -> {
			while (true) {
				paint();
				pause();
			}
		}).start();

		initEvents();
	}
	
	private void paint() {
		screens.get(SCREEN).paint();
	}

	public void initEvents() {
		// lambda of key pressed
		canvas.setOnKeyPressed(e -> {
			screens.get(SCREEN).onKey(e);
		});

		canvas.setOnKeyReleased(e -> {
			screens.get(SCREEN).keyReleased(e);
		});
	}

	private void pause() {
		try {
			Thread.sleep(30);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void pause(ActionEvent event) {
		canvas.requestFocus();

		BaseScreen currentScreen = screens.get(SCREEN);

		if (currentScreen instanceof GameScreen) {
			GameScreen gameScreen = (GameScreen) currentScreen;
			gameScreen.pauseGame();
			pauseBTN.setGraphic(new ImageView(new Image("images/play.png",40, 40, true, true)));
		} else {
			GameScreen gameScreen = (GameScreen) screens.get((SCREEN + 1) % 2);
			gameScreen.resumeGame();
			pauseBTN.setGraphic(new ImageView(new Image("images/pause.png",40, 40, true, true)));
		}

		SCREEN = (SCREEN + 1) % 2;
	}
}
