package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.trivia.data.ArrayListAsyncResponse;
import com.example.trivia.data.repositry;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.model.score;
import com.example.trivia.util.prefs;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int scoreCounter=0;
    private score sobj;
    private prefs preference;
    private int array_lenght;
    private  int current_index=0;
    private boolean ans;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sobj=new score();
        preference=new prefs(this);
        binding.scoreTv.setText("Score: "+sobj.getScoreOfGame());

        Log.d("highScore", "highScore"+preference.getHighscore());
        question_update();
        binding.highScoreTv.setText("High Score :"+preference.getHighscore());

        binding.trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAns(true);
                question_update();
            }
        });
        binding.falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAns(false);
                question_update();
            }
        });
        binding.nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current_index += 1;
                question_update();
            }
        });
    }
    public void checkAns(boolean ansSend){
        if (ansSend==ans)
        {
            increaseScore();
            FadedAnimation();
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            deductScore();
            shakeAnimation();
            Toast.makeText(this,"Incorrect",Toast.LENGTH_SHORT).show();
        }
    }
public void question_update()
        {
            new repositry().getQuestion(new ArrayListAsyncResponse() {
                @Override
                public void process_finished(ArrayList<Question> questionArrayList) {
                    binding.questionTv.setText(questionArrayList.get(current_index).getQuestion());
                    array_lenght=questionArrayList.size();
                    binding.tvQuestionOutOf.setText("Question: "+current_index+"/"+array_lenght);
                    ans=questionArrayList.get(current_index).getAns();

                }
                
            });
        }
        private void FadedAnimation(){
            AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
            alphaAnimation.setDuration(150);
            alphaAnimation.setRepeatCount(1);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            binding.cv.setAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    binding.questionTv.setTextColor(Color.GREEN);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.questionTv.setTextColor(Color.WHITE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        private void shakeAnimation(){
            Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
            binding.cv.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    binding.questionTv.setTextColor(Color.RED);

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.questionTv.setTextColor(Color.WHITE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        @SuppressLint("SetTextI18n")
        public void increaseScore()
        {
            scoreCounter+=3;
            sobj.setScoreOfGame(scoreCounter);

            binding.scoreTv.setText("Score: "+sobj.getScoreOfGame());
        }
        @SuppressLint("SetTextI18n")
        public void deductScore()
        {
            scoreCounter-=1;
            if (scoreCounter>0)
            {

                sobj.setScoreOfGame(scoreCounter);

                binding.scoreTv.setText("Score: "+sobj.getScoreOfGame());
            }
            else
            {
                scoreCounter=0;
                sobj.setScoreOfGame(0);

                binding.scoreTv.setText("Score: "+sobj.getScoreOfGame());
            }
        }

    @Override
    protected void onPause() {
        preference.saveHighScore(scoreCounter);
        Log.d("pause", "onPause: ");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        Log.d("resume", "onPostResume: ");
        binding.highScoreTv.setText("High Score :"+preference.getHighscore());
        super.onPostResume();
    }
}