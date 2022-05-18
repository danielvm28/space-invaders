package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import screens.BaseScreen;
import screens.ScreenA;
import screens.ScreenB;

public class MainWindow implements Initializable {
	public static int FRAMES = 0;
	public static int SCREEN = 0;

	@FXML
	private Canvas canvas;
	private GraphicsContext gc;
	private ScreenA screenA;
	private ScreenB screenB;
	private ArrayList<BaseScreen> screens;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		screens = new ArrayList<>();

		screens.add(new ScreenA(canvas));
		screens.add(new ScreenB(canvas));

		gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);
		
		new Thread(() -> {
			while (true) {
				paint();
				pause(5);
				FRAMES++;
			}
		}).start();

		initEvents();
	}
	
	private void paint() {
		screens.get(SCREEN).paint();
		
	}

	public void initEvents() {
		//lambda 1
		canvas.setOnMouseClicked(e -> {
			screens.get(SCREEN).onClick(e);
		});

		//lambda 2
		canvas.setOnKeyPressed(e -> {
			screens.get(SCREEN).onKey(e);
//			if (e.getCode().equals(KeyCode.SPACE)){
//				System.out.println("Space");
//				SCREEN = (SCREEN+1)%2;
//			}
			if (e.getCode().equals(KeyCode.UP)){
				System.out.println("UP");
				SCREEN = (SCREEN+1)%2;
			}
			if (e.getCode().equals(KeyCode.DOWN)){
				System.out.println("DOWN");
				SCREEN = (SCREEN+1)%2;
			}
			if (e.getCode().equals(KeyCode.LEFT)){
				System.out.println("LEFT");
				SCREEN = (SCREEN+1)%2;
			}
			if (e.getCode().equals(KeyCode.RIGHT)){
				System.out.println("RIGTH");
				SCREEN = (SCREEN+1)%2;
			}
		});
	}

	private void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
}
