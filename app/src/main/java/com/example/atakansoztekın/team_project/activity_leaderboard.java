package com.example.atakansoztekin.team_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_leaderboard extends AppCompatActivity {

    DatabaseHandler2 db;
    TextView guess1,score1, guess2,score2,guess3,score3,guess4,score4,guess5,score5,ur,un,usc,score;
    List<Leaderboard> scorelist = new ArrayList<>();
    ArrayList<TextView> scores = new ArrayList<>();
    ArrayList<TextView> guessList = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        score = (TextView) findViewById(R.id.l_from);
        guess1 = (TextView) findViewById(R.id.l_sc3);
        score1 = (TextView) findViewById(R.id.l_sc4);
        guess2 = (TextView) findViewById(R.id.l_sc6);
        score2 = (TextView) findViewById(R.id.l_sc7);
        guess3 = (TextView) findViewById(R.id.l_sc9);
        score3 = (TextView) findViewById(R.id.l_sc10);
        guess4 = (TextView) findViewById(R.id.l_sc12);
        score4 = (TextView) findViewById(R.id.l_sc13);
        guess5 = (TextView) findViewById(R.id.l_sc15);
        score5 = (TextView) findViewById(R.id.l_sc16);
        ur = (TextView) findViewById(R.id.l_usr);
        un = (TextView) findViewById(R.id.l_usn);
        usc = (TextView) findViewById(R.id.l_ussc);
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

        db= new DatabaseHandler2(getApplicationContext());
        scorelist = db.getAllContacts();
        do{
            guessList.get(i).setText(scorelist.get(i).getName());
            scores.get(i).setText(String.valueOf(scorelist.get(i).getScore()));
            i++;
        }while(i<5);
        for(int j=0;j<scorelist.size();j++){
            if(MainActivity.etName.getText().toString().equals(scorelist.get(j).getName())&&activity_guess.totalScore.getText().toString().equals(String.valueOf(scorelist.get(j).getScore()))){
                if(j>=5){
                    ur.setVisibility(View.VISIBLE);
                    un.setVisibility(View.VISIBLE);
                    usc.setVisibility(View.VISIBLE);
                    ur.setText(String.valueOf(j+1));
                    un.setText(MainActivity.etName.getText().toString());
                    usc.setText(String.valueOf(scorelist.get(j).getScore()));
                    j = scorelist.size()-1;

                }
            }
        }
    }
    private void showMessage(String value){
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
    }
    public void back(View view){
        ur.setVisibility(View.INVISIBLE);
        un.setVisibility(View.INVISIBLE);
        usc.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(view.getContext(), activity_guess.class);
        view.getContext().startActivity(intent);
    }
}
