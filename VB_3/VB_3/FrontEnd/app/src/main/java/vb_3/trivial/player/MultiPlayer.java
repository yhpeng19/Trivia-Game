package vb_3.trivial.player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
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

public class MultiPlayer extends AppCompatActivity {
    private String url = "http://coms-309-vb-3.misc.iastate.edu/data.json";
    private static String TAG = SinglePlayer.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView txtResponse;
    private Button pull_button;
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        txtResponse = (TextView) findViewById(R.id.question_text);
        pull_button = (Button) findViewById(R.id.pull_button);
        pull_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

                        jsonResponse += "This is multiple-player mode!\n" + "Question: " + question + "\n\n";
                        jsonResponse += "Correct: " + correct + "\n\n";
                        jsonResponse += "Answer1: " + answer1 + "\n\n";
                        jsonResponse += "Answer2: " + answer2 + "\n\n";
                        jsonResponse += "Answer3: " + answer3 + "\n\n";
                        jsonResponse += "ID: " + id + "\n\n\n";

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
