package screens;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PauseScreen extends BaseScreen{
    public PauseScreen(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void paint() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                "Game Paused",
                Math.round(canvas.getWidth()  / 2),
                Math.round(canvas.getHeight() / 2)
        );

        gc.setFont(new Font(30));

    }

    @Override
    public void onKey(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
