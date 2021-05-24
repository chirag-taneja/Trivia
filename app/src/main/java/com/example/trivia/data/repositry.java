package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.example.trivia.controller.AppController;

import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class repositry {
    ArrayList<Question> questionArrayList =new ArrayList<>();
    String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    public List<Question> getQuestion(final ArrayListAsyncResponse callback)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        Question question=new Question(response.getJSONArray(i).getString(0),response.getJSONArray(i).getBoolean(1));
                        questionArrayList.add(question);
                       // Log.d("main", "onResponse: "+questionArrayList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (callback!=null) callback.process_finished(questionArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "error");
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

     return questionArrayList;
    }
}
