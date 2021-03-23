package vb_3.trivial.player;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Set;

import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;

/**
 * Constructor for the main mechanics for the game. Creates an instance of the game whether it is single, or multiplayer.
 */
public class GameMechanics extends AppCompatActivity {

    //private String packURL = "http://coms-309-vb-3.misc.iastate.edu:8080/questions/Math/10"; //TODO, change to make dynamic based on category
    private String packURL = "http://coms-309-vb-3.misc.iastate.edu:8080/game/startGame/" + Global_Varable.Game_ID;
    private static String TAG = GameMechanics.class.getSimpleName();
    private ProgressDialog pDialog;
    private WebSocketClient wsc;

    //Buttons
    private Button answer1_button;
    private Button answer2_button;
    private Button answer3_button;
    private Button answer4_button;

    //TextViews
    private TextView question;
    private TextView scoreboard_text;
    private TextView timeResponse;

    //Global Variables
    private String[][] question_pack; //This will get passed into from the lobby
    public static long score = 0;
    private int round_tracker = 0;
    private long timeCount;
    private CountDownTimer t;
    private String scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gameplay);

        /* Initializations */
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //Eventually these will be combined from Communications
        question_pack = new String[10][5];

        //Connections
        question = findViewById(R.id.question_text);
        scoreboard_text = findViewById((R.id.scoreboard_text));
        answer1_button = findViewById(R.id.answer1_button);
        answer2_button = findViewById(R.id.answer2_button);
        answer3_button = findViewById(R.id.answer3_button);
        answer4_button = findViewById(R.id.answer4_button);
        timeResponse = findViewById((R.id.timeResponse));

        //Create Websocket
        /**
         * Websocket Mechanics
         */
        Draft[] drafts = {new Draft_6455()};
        String websocket_url = "ws://coms-309-vb-3.misc.iastate.edu:8080/ScoreDisplay/" + Global_Varable.username;
        try {
            Log.d("Socket:", "Trying Scoket");
            wsc = new WebSocketClient(new URI(websocket_url), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String s) {
                    Log.d("Returned Message", "run() returned: " + s);
                    scores = s;
                    scoreboard_text.setText(scores);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    //TODO Send a packet with usernames/scores to server
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        wsc.connect();

        makeJsonArrayRequest();
    }

    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "In the onStart() event");
        scoreboard_text.setText(Global_Varable.username + ": 0"); //TODO Will have to set the scoreboard with Username and score = 0
    }

    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "In the onResume() event");

        /* Create a timer to start at the beginning of a round */
        t = new CountDownTimer(11000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Log.d("Timer", Long.toString(millisUntilFinished)); //For live view
                Log.d("Which timer", ""+round_tracker);
                timeCount = millisUntilFinished / 1000;
                timeResponse.setText("Question" + (round_tracker + 1) + "   Timer: " + Long.toString(timeCount));

                if (millisUntilFinished < 500) {
                    t.onFinish();
                }
            }

            @Override
            public void onFinish() {
                round_tracker++; //Update which question should be displayed/attempted

                //TODO Need to separate this from the onFinish
                /**
                 * Set up the question and answers
                 */
                if(round_tracker != 10) {
                    setQuestion();
                    t.start();
                }
            }
        };

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                timeResponse.setText("START");
                setQuestion();//Update Question
                t.start();
            }
        }, 100);

        //Set listeners
        answer1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1_button.getText().toString() == question_pack[round_tracker][1]) {
                    updateScore();//Update Score
                    wsc.send(Long.toString(score)); //Send the updated score
                    round_tracker++; //Update current round
                    setQuestion();//Update Question
                } else {
                    round_tracker++;
                    setQuestion();//Update Question
                }
                //t.cancel(); //Cancel the current timer
                t.start();
            }
        });

        answer2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2_button.getText().toString() == question_pack[round_tracker][1]) {
                    updateScore();//Update Score
                    wsc.send(Long.toString(score)); //Send the updated score
                    round_tracker++; //Update current round
                    setQuestion();//Update Question
                } else {
                    round_tracker++;
                    setQuestion();//Update Question
                }
                //t.cancel(); //Cancel the current timer
                t.start();
            }
        });

        answer3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3_button.getText().toString() == question_pack[round_tracker][1]) {
                    updateScore();//Update Score
                    wsc.send(Long.toString(score)); //Send the updated score
                    round_tracker++; //Update current round
                    setQuestion(); //Update Question
                } else {
                    round_tracker++;
                    setQuestion();//Update Question
                }
                //t.cancel(); //Cancel the current timer
                t.start();
            }
        });

        answer4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4_button.getText().toString() == question_pack[round_tracker][1]) {
                    updateScore();//Update Score
                    wsc.send(Long.toString(score)); //Send the updated score
                    round_tracker++; //Update current round
                    setQuestion(); //Update Question
                } else {
                    round_tracker++;
                    setQuestion();//Update Question
                }
                //t.cancel(); //Cancel the current timer
                t.start();
            }
        });


        t.cancel();
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.d("Lifecycle", "In the onDestroy() event");
    }

    @Override
    public void onBackPressed(){

    }

    /**
     * Shows the active progress dialog.
     */
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Hides the active progress dialog.
     */
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /**
     * Sends a request to the server for a set of 10 questions to the targeted URL.
     */
    public void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(packURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < 10; i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String id = Question_Answers.getString("id");
                        String question = Question_Answers.getString("question");
                        String category = Question_Answers.getString("category");
                        String correct = Question_Answers.getString("correctAnswer");
                        String answer1 = Question_Answers.getString("wrongAnswer1");
                        String answer2 = Question_Answers.getString("wrongAnswer2");
                        String answer3 = Question_Answers.getString("wrongAnswer3");
                        //String category = Answers.getString("category");

                        question_pack[i][0] = question;
                        question_pack[i][1] = correct;
                        question_pack[i][2] = answer1;
                        question_pack[i][3] = answer2;
                        question_pack[i][4] = answer3;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error printed", e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     * Randomizes the given array for the use in the multiple choice answers.
     *
     * @param array The array of answers from the question pack.
     * @return Randomized version of the given array.
     */
    public static String[] RandomizeArray(String[] array) {
        Random rgen = new Random();  // Random number generator
        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            String temporary = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temporary;
        }
        return array;
    }

    /**
     * Sets up the next question for the game. Mainly used to eliminate repeat code.
     */
    public void setQuestion(){
        if(round_tracker <= 9) {
            question.setText(question_pack[round_tracker][0]);
            final String[] rand_arr = new String[4];
            rand_arr[0] = question_pack[round_tracker][1];
            rand_arr[1] = question_pack[round_tracker][2];
            rand_arr[2] = question_pack[round_tracker][3];
            rand_arr[3] = question_pack[round_tracker][4];
            RandomizeArray(rand_arr);
            answer1_button.setText(rand_arr[0]);
            answer2_button.setText(rand_arr[1]);
            answer3_button.setText(rand_arr[2]);
            answer4_button.setText(rand_arr[3]);
            t.cancel();
        }
        else
        {
            Intent game_done = new Intent(GameMechanics.this, Result.class);
            game_done.putExtra("Scores", scores);
            game_done.putExtra("userScore", score);

            wsc.close();
            //game_done.putExtra("",)
            startActivity(game_done);
        }
    }

    /**
     * Updates the score with the score modifier. Score modifier is a multiple of time and the lowest score.
     */
    public void updateScore() {
        long score_mod = Math.round(timeCount * 100); //Calculate points awarded
        score = score + score_mod; //Add points to score
    }

}
