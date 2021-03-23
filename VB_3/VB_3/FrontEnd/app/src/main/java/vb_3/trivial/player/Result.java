package vb_3.trivial.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;

public class Result extends AppCompatActivity {


    private TextView winOrLose_text;
    private TextView scores_text;

    private Button rematch;
    private Button exit;

    private long user_score;

    //private int scores[];
    private ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Connections
        scores_text = findViewById(R.id.users_scores_text);
        winOrLose_text = findViewById(R.id.win_lose_text);
        rematch = findViewById(R.id.rematch_button);
        exit = findViewById(R.id.exit_button);

        //Gather user's username to initialize
        String winning_player = "";
        String winning_score = "0";

        //Gather user's final score from the game
        long player = getIntent().getLongExtra("userScore",0);

        //Initialize arrayList
        users = new ArrayList<String>();

        //Set the display of all scores
        String scores = getIntent().getStringExtra("Scores");
        scores_text.setText(scores);

        //Parse to get highest score to compare with user score
        String temp = scores;
        int l_pos = 0;
        //Stores all the scores in user score user score user score format
        while(!temp.isEmpty() && l_pos+1 != temp.length())
        {
            int c_pos = temp.indexOf(':'); //Use to cut off the user from the score
            Log.d("Cut position of score", ""+c_pos);
            l_pos = temp.indexOf('\n'); //Used to cut off the user/score
            Log.d("Cut position of EOL",""+l_pos);
            Log.d("Length of temp",""+temp.length());
            String count = temp.substring(c_pos+2, l_pos); //Pulls the score for THAT player from beginning of its position, to the end of the line
            Log.d("Count" , count);
                String name = temp.substring(0, c_pos); //Pulls the username for THAT player from the beginning to the score
            Log.d("Name", name);
                users.add(name); //Add username first
                users.add(count); //Add score second
            if(l_pos+1 != temp.length()){
            temp = temp.substring(l_pos); //Set temp to not include the last parsed user/score duo
            }
        }

        //Initialize separation of players with their score
        int num_players = users.size(); //Returns the number of players + their score (separate), so 2 pieces of info for each user
        int s[] = new int[num_players]; //Just an array of scores in int format
        for(int i = 0; i<num_players/2;i++)
        {
            int t = Integer.parseInt(users.get(i+i+1));
            s[i]= t;
        }

        //Manage determining highest score
        int highest_score = 0;
        int winner_num = 0;
        for(int i = 0; i < num_players/2; i++){
            if(s[i] > highest_score)
            {
                winner_num = i*2; //Set the winner to the correct number
                highest_score = s[i]; //Reset highest score
            }
        }

        Log.d("ArrayList", users.get(winner_num));

        if(users.get(winner_num) == Global_Varable.username)
        {
            winOrLose_text.setText("You Win!");
        }
        else
        {
            winOrLose_text.setText(users.get(winner_num) + " Wins!");
        }

        //winOrLose_text.setText("You Win!"); //TODO CHANGE based on that

        rematch = findViewById(R.id.rematch_button);
        rematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Lobbies_List.class);
                startActivity(intent);
            }
        });

        exit = findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, Lobbies_List.class);
                startActivity(intent);
            }
        });
    }


    protected void onStart()
    {
        super.onStart();
        Log.d("Moved to onStart", "");
    }
}
