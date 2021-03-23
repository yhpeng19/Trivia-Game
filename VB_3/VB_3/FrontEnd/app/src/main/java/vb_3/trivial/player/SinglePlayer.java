package vb_3.trivial.player;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.R;

import android.os.CountDownTimer;


public class SinglePlayer extends AppCompatActivity {
    private String url = "http://coms-309-vb-3.misc.iastate.edu/data.json";
    private static String TAG = SinglePlayer.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView txtResponse;
    private TextView timer;

    private int current_question = 0;
    private boolean start_pressed = false;
    /*Buttons*/
    private Button answer1_button;
    private Button answer2_button;
    private Button answer3_button;
    private Button answer4_button;
    private Button start_button;

    //EDIT
    private String [][] question_pack;

    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        question_pack = new String[10][5];
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        makeJsonArrayRequest();

        //Connect buttons and textViews
        txtResponse = (TextView) findViewById(R.id.question_text);
        answer1_button = findViewById(R.id.pull_button);
        answer2_button = findViewById(R.id.pull_button2);
        answer3_button = findViewById(R.id.pull_button3);
        answer4_button = findViewById(R.id.pull_button4);
        start_button = findViewById(R.id.Single_start_button);
        timer = findViewById(R.id.timer_text);



        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_button.setVisibility(View.GONE);
                start_pressed = true;
                txtResponse.setText(question_pack[current_question][0]);
                answer1_button.setText(question_pack[current_question][1]);
                answer2_button.setText(question_pack[current_question][2]);
                answer3_button.setText(question_pack[current_question][3]);
                answer4_button.setText(question_pack[current_question][4]);


                new CountDownTimer(50000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }
                };
            }
        });

        //Request a question pack
        //Communications comm = new Communications();
        //quest_pack = comm.get_Question_Pack(1); //TODO: Change category id to random
        makeJsonArrayRequest();


            //Set Answers beyond initial setting
            answer1_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!start_pressed)
                    {
                        start_button.setVisibility(View.GONE);
                        txtResponse.setText(question_pack[0][0]);
                        answer1_button.setText(question_pack[0][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[0][2]);
                        answer3_button.setText(question_pack[0][3]);
                        answer4_button.setText(question_pack[0][4]);
                    }
                    else {
                        current_question++; //Iterate Question
                        txtResponse.setText(question_pack[current_question][0]);
                        answer1_button.setText(question_pack[current_question][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[current_question][2]);
                        answer3_button.setText(question_pack[current_question][3]);
                        answer4_button.setText(question_pack[current_question][4]);
                    }
                }
            });

            answer2_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!start_pressed)
                    {
                        start_button.setVisibility(View.GONE);
                        txtResponse.setText(question_pack[0][0]);
                        answer1_button.setText(question_pack[0][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[0][2]);
                        answer3_button.setText(question_pack[0][3]);
                        answer4_button.setText(question_pack[0][4]);
                    }
                    else {
                        current_question++; //Iterate Question
                        txtResponse.setText(question_pack[current_question][0]);
                        answer1_button.setText(question_pack[current_question][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[current_question][2]);
                        answer3_button.setText(question_pack[current_question][3]);
                        answer4_button.setText(question_pack[current_question][4]);
                    }
                }
            });

            answer3_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!start_pressed)
                    {
                        start_button.setVisibility(View.GONE);
                        txtResponse.setText(question_pack[0][0]);
                        answer1_button.setText(question_pack[0][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[0][2]);
                        answer3_button.setText(question_pack[0][3]);
                        answer4_button.setText(question_pack[0][4]);
                    }
                    else {
                        current_question++; //Iterate Question
                        txtResponse.setText(question_pack[current_question][0]);
                        answer1_button.setText(question_pack[current_question][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[current_question][2]);
                        answer3_button.setText(question_pack[current_question][3]);
                        answer4_button.setText(question_pack[current_question][4]);
                    }
                }
            });

            answer4_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!start_pressed)
                    {
                        start_button.setVisibility(View.GONE);
                        txtResponse.setText(question_pack[0][0]);
                        answer1_button.setText(question_pack[0][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[0][2]);
                        answer3_button.setText(question_pack[0][3]);
                        answer4_button.setText(question_pack[0][4]);
                    }
                    else {
                        current_question++; //Iterate Question
                        txtResponse.setText(question_pack[current_question][0]);
                        answer1_button.setText(question_pack[current_question][1]); //REPETITIVE CODE, SUMMARIZE ELSEWHERE
                        answer2_button.setText(question_pack[current_question][2]);
                        answer3_button.setText(question_pack[current_question][3]);
                        answer4_button.setText(question_pack[current_question][4]);
                    }
                }
            });
        //}
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //Might need outside of this class, might have to change to public.
    private int get_current_question()
    {
        return current_question;
    }


    //This can be moved to a communications class
    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) { Log.d(TAG, response.toString());
/*
                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response.get(i);

                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");

                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n\n";

                            }

                            txtResponse.setText(jsonResponse);
*/

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject Question_Answers = (JSONObject) response.get(i);

                                String question = Question_Answers.getString("question");
                                String correct = Question_Answers.getString("correct");

                                //JSONObject Answers = Question_Answers.getJSONObject("answers");
                                String answer1 = Question_Answers.getString("answer1");
                                String answer2 = Question_Answers.getString("answer2");
                                String answer3 = Question_Answers.getString("answer3");
                                //String category = Answers.getString("category");
                                String id = Question_Answers.getString("id");

                                question_pack[i][0]= question;
                                question_pack[i][1]= correct;
                                question_pack[i][2]= answer1;
                                question_pack[i][3]= answer2;
                                question_pack[i][4]= answer3;

                            }
                            txtResponse.setText(jsonResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
