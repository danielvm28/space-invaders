package screens;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TransitionScreen extends BaseScreen{
    private String label;

    private GameScreen gameScreen;

    private boolean won;

    private String currentScore;

    private String highScore;

    public TransitionScreen(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void paint() {
        if (won) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(50));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.fillText(
                    label,
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2)
            );

            gc.setFont(new Font(30));

            gc.fillText("Press the button to continue to level " + (gameScreen.LEVEL + 1),
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2) + 50);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(50));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.fillText(
                    label,
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2)
            );

            gc.setFont(new Font(30));

            gc.fillText(
                    "High Score: " + highScore,
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2) + 50
            );

            gc.fillText(
                    "Achieved Score: " + currentScore,
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2) + 100
            );

            gc.fillText("Press the button to start again",
                    Math.round(canvas.getWidth()  / 2),
                    Math.round(canvas.getHeight() / 2) + 150);
        }
    }

    @Override
    public void onKey(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(String currentScore) {
        this.currentScore = currentScore;
    }

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        if (won) {
            label = "Level Complete!";
        } else {
            label = "Game Over";
        }

        this.won = won;
    }
}
