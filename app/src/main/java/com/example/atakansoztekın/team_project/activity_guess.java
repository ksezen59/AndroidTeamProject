package com.example.atakansoztekin.team_project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_guess extends AppCompatActivity {
    DatabaseHandler2 db;
    String username ;

    TextView questionText;
    String questionWord;
    EditText answerText;
    String answerWord;
    Button guessButton,scoreboard,btnback;
    TextView guess1,score1, guess2,score2,guess3,score3,guess4,score4,guess5,score5;

    public static TextView totalScore;
    String[] questionList = {"dog","book","pencil","bring","start","plane","blackberry","try","chair","bike","psychology","physiology",
            "engineering","computer","mechanical","science","algorithm","chemistry","management","information"};
    ArrayList<TextView> scores = new ArrayList<>();
    ArrayList<TextView> guessList = new ArrayList<>();
    int r;
    int i=0;
    int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        db=new DatabaseHandler2(getApplicationContext());
        username = MainActivity.etName.getText().toString();


        questionText = (TextView) findViewById(R.id.l_from);
        answerText = (EditText) findViewById(R.id.l_to);
        guessButton = (Button) findViewById(R.id.btn_get);
        scoreboard = (Button) findViewById(R.id.scoreboard);
        btnback = (Button) findViewById(R.id.btn_back);
        guess1 = (TextView) findViewById(R.id.g1);
        score1 = (TextView) findViewById(R.id.l_s1);
        guess2 = (TextView) findViewById(R.id.g2);
        score2 = (TextView) findViewById(R.id.l_s2);
        guess3 = (TextView) findViewById(R.id.g3);
        score3 = (TextView) findViewById(R.id.l_s3);
        guess4 = (TextView) findViewById(R.id.g4);
        score4 = (TextView) findViewById(R.id.l_s4);
        guess5 = (TextView) findViewById(R.id.g5);
        score5 = (TextView) findViewById(R.id.l_s5);
        totalScore = (TextView) findViewById(R.id.final_score);
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        scores.add(score4);
        scores.add(score5);
        guessList.add(guess1);
        guessList.add(guess2);
        guessList.add(guess3);
        guessList.add(guess4);
        guessList.add(guess5);


        r = (int) (Math.random()*(20-0) +0);
        questionText.setText(questionList[r]);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionWord = questionText.getText().toString();
                answerWord = answerText.getText().toString();
                if (questionWord != null && answerWord != null) {

                    String myApiURL = "https://translation.googleapis.com/language/translate/v2?q=" + questionText.getText().toString() + "&target=tr&cid=en&key=AIzaSyBIICm6wG8uyG1AcYM5wXR8LIjlFa9eeD4";

                    final HttpTask newTask = new HttpTask(new ResponseHandler() {
                        @Override

                        public void onFailure() {
                            super.onFailure();
                            Toast toast = Toast.makeText(activity_guess.this, "Service Failure", Toast.LENGTH_LONG);
                            toast.show();
                        }

                        @Override
                        public <T> void onFinish(T result) {


                            if (result instanceof TranslationListModel) {

                                TranslateModel modelObj = ((TranslationListModel) result).data.get("translations").get(0);
                                if (modelObj.translatedText.toString().equals(answerWord.toString())) {
                                    guessList.get(i).setText(modelObj.translatedText.toString());
                                    scores.get(i).setText(String.valueOf(modelObj.translatedText.toString().length() * 100));
                                    k+=Integer.valueOf(scores.get(i).getText().toString());
                                    totalScore.setText(String.valueOf(k));
                                    r = (int) (Math.random() * (10-0) + 0);
                                    questionText.setText(questionList[r]);
                                    i++;
                                    answerText.setText("");
                                    if (i == 5){
                                        int k = Integer.parseInt(score1.getText().toString())+Integer.parseInt(score2.getText().toString())+Integer.parseInt(score3.getText().toString())+Integer.parseInt(score4.getText().toString())+Integer.parseInt(score5.getText().toString());
                                        totalScore.setText(String.valueOf(k));
                                        guessButton.setVisibility(View.GONE);
                                        questionText.setVisibility(View.GONE);
                                        answerText.setVisibility(View.GONE);
                                        scoreboard.setVisibility(View.VISIBLE);
                                        btnback.setVisibility(View.VISIBLE);

                                    }
                                }

                                else {
                                    guessList.get(i).setText(answerWord.toString());
                                    guessList.get(i).setTextColor(Color.RED);
                                    scores.get(i).setText("0");
                                    scores.get(i).setTextColor(Color.RED);
                                    r = (int) (Math.random() * (10-0) + 0);
                                    questionText.setText(questionList[r]);
                                    i++;
                                    answerText.setText("");
                                    if (i == 5){
                                        k = Integer.parseInt(score1.getText().toString())+Integer.parseInt(score2.getText().toString())+Integer.parseInt(score3.getText().toString())+Integer.parseInt(score4.getText().toString())+Integer.parseInt(score5.getText().toString());
                                        totalScore.setText(String.valueOf(k));
                                        guessButton.setVisibility(View.GONE);
                                        questionText.setVisibility(View.GONE);
                                        answerText.setVisibility(View.GONE);
                                        scoreboard.setVisibility(View.VISIBLE);
                                        btnback.setVisibility(View.VISIBLE);

                                    }

                                }


                            }
                        }

                    });

                    newTask.execute(myApiURL);


                }
                ///

            }
        });








    }

    public void insert(View view) {
                String name = username;
                int score = Integer.valueOf(totalScore.getText().toString());

                Leaderboard c = new Leaderboard(name, score);
                long r = db.addContact(c);

                if (r > 0) {
                    Intent intent = new Intent(view.getContext(), activity_leaderboard.class);
                    view.getContext().startActivity(intent);
                } else {
                    showMessage("Same User!");
                }
    }

    private void showMessage(String value){
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
    }
    public void back(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }
}