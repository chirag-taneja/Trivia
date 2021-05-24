package com.example.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.trivia.model.score;

public class prefs {
    private SharedPreferences sharedPreferences;
    score sobj;

    public prefs(Activity context) {
        sharedPreferences=context.getPreferences(Context.MODE_PRIVATE);
        sobj=new score();
    }
    public void saveHighScore(int score)
    {
        int currentScore=sharedPreferences.getInt("High",0);
        if (score>currentScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("High",score);
            editor.apply();

        }
        else {
        }
    }
    public int getHighscore()
    {
        int high=sharedPreferences.getInt("High", 0);
        return high;
    }

}
