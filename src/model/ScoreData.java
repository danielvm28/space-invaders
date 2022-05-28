package model;

import com.google.gson.Gson;
import java.io.*;

public class ScoreData {
    private int highScore;

    public void updateScoreboard(int newScore) {
        if (newScore > highScore) {
            highScore = newScore;
        }
        saveJSON();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void saveJSON() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(this);

            File file = new File("data/scoreData.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadJSON() {
        try {
            FileInputStream fis = new FileInputStream(new File("data/scoreData.json"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String json = "";
            String line;

            while((line = reader.readLine()) != null) {
                json += line;
            }

            Gson gson = new Gson();
            ScoreData data = gson.fromJson(json, ScoreData.class);

            if (data != null) {
                highScore = data.highScore;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
