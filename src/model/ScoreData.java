package model;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreData {
    private ArrayList<Integer> scores;

    public ScoreData() {
        scores = new ArrayList<>();
    }

    public void updateScoreboard(int newScore) {
        scores.add(newScore);
        Collections.sort(scores);
        saveJSON();
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
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
                scores = data.scores;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
