package vb_3.trivial;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import vb_3.trivial.ControllerClasses.app.AppController;

public class Communications {


    private String url = "https://api.androidhive.info/volley/person_array.json";
    private static String TAG = SinglePlayer.class.getSimpleName();
    private ProgressDialog pDialog;
    private String jsonResponse;


    /**
     * This method returns a question pack in the form of a 2D array in the format (Question, Correct Answer, Answer 1, Answer 2, Answer 3) after
     * checking to make sure each question has not already been called.
     * @param category_id
     */
    public void get_Question_Pack(int category_id){
        int num_questions = 10;

        //
        while(num_questions > 0)
        {
            //Randomly generate a question #
            Random random = new Random();
            int question_num = random.nextInt();

            //Check to make sure it hasn't already been called
            //if(not_Called())
            //{
                //makeJsonArrayRequest(category_id, 0);
            //}


        }

    }





    /**
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest(int id, int question_number) {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) { Log.d(TAG, response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String question = Question_Answers.getString("question");
                        String correct = Question_Answers.getString("correct");

                        JSONObject Answers = Question_Answers.getJSONObject("answers");
                        String answer1 = Answers.getString("answer1");
                        String answer2 = Answers.getString("answer2");
                        String answer3 = Answers.getString("answer3");
                        String category = Answers.getString("category");
                        String id = Answers.getString("id");

                        //TODO: Put these into either an array or store in public variables, maybe just return a string that can be parsed

                        //jsonResponse += "Question: " + name + "\n\n";
                        //jsonResponse += "Correct Answer: " + email + "\n\n";
                        //jsonResponse += "Answer 1 " + home + "\n\n";
                        //jsonResponse += "Answer 2 " + mobile + "\n\n\n";

                    }
                    //txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
