package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;

public interface ArrayListAsyncResponse {
    public void process_finished(ArrayList<Question> questionArrayList);

}
