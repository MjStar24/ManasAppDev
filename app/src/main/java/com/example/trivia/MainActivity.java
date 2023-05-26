package com.example.trivia;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{



    String url;
    RequestQueue queue;
    JsonObjectRequest jsonObjectRequest;


    TextView totalQues,Score,question;
    Button ansA,ansB,ansC,ansD,submitBtn;
    TextView Category,Type,Difficulty;

    ArrayList<QuizModal> quizModelArrayList;
    Random random;
    int currentPos,currentScore=0,questionsAttempted=0;
    JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Score = findViewById(R.id.Score);
        question = findViewById(R.id.Questions);
        ansA = findViewById(R.id.FirstAns);
        ansB = findViewById(R.id.SecondAns);
        ansC = findViewById(R.id.ThirdAns);
        ansD = findViewById(R.id.FourthAns);
        submitBtn = findViewById(R.id.submitBtn);
        Category = findViewById(R.id.Category);
        Type = findViewById(R.id.Type);
        Difficulty = findViewById(R.id.Difficulty);

        quizModelArrayList = new ArrayList<>();
        random = new Random();

        url="https://www.coursehubiitg.in/api/codingweek/contributions";
        queue = Volley.newRequestQueue(this);

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    result=response.getJSONArray("results");

                    Log.e("api",response.toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                question.setText(error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        getQuizQuestion(quizModelArrayList,result);
        currentPos = random.nextInt(quizModelArrayList.size());
        setDataToView(currentPos);


        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModelArrayList.get(currentPos).getOption4()==quizModelArrayList.get(currentPos).getAnswer()){
                        currentScore++;
                }
                questionsAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModelArrayList.get(currentPos).getOption1()==quizModelArrayList.get(currentPos).getAnswer()){
                    currentScore++;
                }
                questionsAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModelArrayList.get(currentPos).getOption2()==quizModelArrayList.get(currentPos).getAnswer()){
                    currentScore++;
                }
                questionsAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });

        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizModelArrayList.get(currentPos).getOption3()==quizModelArrayList.get(currentPos).getAnswer()){
                    currentScore++;
                }
                questionsAttempted++;
                currentPos = random.nextInt(quizModelArrayList.size());
                setDataToView(currentPos);
            }
        });




    }

    private void setDataToView(int currentPos){
            question.setText(quizModelArrayList.get(currentPos).getQuestion());
            ansA.setText(quizModelArrayList.get(currentPos).getOption4());
            ansB.setText(quizModelArrayList.get(currentPos).getOption1());
            ansC.setText(quizModelArrayList.get(currentPos).getOption2());
            ansD.setText(quizModelArrayList.get(currentPos).getOption3());
            Category.setText(quizModelArrayList.get(currentPos).getCategory());
            Type.setText(quizModelArrayList.get(currentPos).getType());
            Difficulty.setText(quizModelArrayList.get(currentPos).getDifficulty());

    };


    private void getQuizQuestion(ArrayList<QuizModal> quiz,JSONArray result){
        try{

            for(int i=0;i< result.length();i++){
                JSONObject obj=result.getJSONObject(i);
                String Cate= obj.getString("category");

                String type= obj.getString("type");

                String diffi=obj.getString("difficulty");

                String Ques=obj.getString("question");
                String correct_ans=obj.getString("correct_answer");

                JSONArray incorrectAns=obj.getJSONArray("incorrect_answers");
                String opt1=incorrectAns.getString(0);
                String opt2=incorrectAns.getString(1);
                String opt3=incorrectAns.getString(2);

                quiz.add(new QuizModal(Ques,opt1,opt2,opt3,correct_ans,correct_ans,diffi,type,Cate));

            };

        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}