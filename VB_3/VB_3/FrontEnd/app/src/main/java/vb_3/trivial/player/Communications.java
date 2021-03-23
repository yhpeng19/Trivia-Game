package vb_3.trivial.player;

import android.app.ProgressDialog;
import android.util.Log;

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


    private String url = "http://coms-309-vb-3.misc.iastate.edu/data.json";
    private String nurl = "http://coms-309-vb-3.misc.iastate.edu/test"; //THIS IS FOR ALL QUESTIONS
    private String burl = "http://coms-309-vb-3.misc.iastate.edu/Math/10";
    private static String TAG = SinglePlayer.class.getSimpleName();
    private ProgressDialog pDialog;
    private String jsonResponse;
    private int questions = 0;

    /**
     * This method returns a question pack in the form of a 2D array in the format (Question, Correct Answer, Answer 1, Answer 2, Answer 3) after
     * checking to make sure each question has not already been called.
     * @param category_id The ID that relates to the categories that can be chosen.
     */
    public String[][] get_Question_Pack(int category_id){
        int num_questions = 0; //Current number of questions that have been called, also used to determine which row to add the next question_pack into
        int[] questions_called = new int[10]; //Array for storing all questions called based on ID
        String[][] question_pack = new String[10][5]; //2D array for storing all questions and their answers to return.

        Random random = new Random();

        //While you don't have 10 questions
        while(num_questions < 10)
        {
            //Randomly generate a question #
            int question_num = random.nextInt(20);

            //Check to see if it has already been called
            if(!check_Numbers(questions_called, num_questions, question_num)) //If it has NOT been called
            {
                makeJsonArrayRequest(question_num); //Request the question pack and store it into 'quetions_pack' array
                questions_called[num_questions] = question_num; //Adds the question_num to the questions_called array to make sure it is not called again
                num_questions++; //Increment to account for the newly added question
                //questions_called[question_num] = question_num;
            }
        }
    return question_pack;
    }

    /**
     * Requests a specific question from the server. Could be used in multiple applications, like predetermined matches or trivia training?
     * @param category_id The given identifier for the question's category.
     * @param question_num The given identifier for the question's direct ID.
     * @return A single array of size 5, that contains a question and its 4 potential answers in QCAAA format.
     */
    public String[] get_Specific_Question(int category_id, int question_num){
        return makeJsonArrayRequest(question_num);
    }

    /**
     * Requestions a question at COMPLETE random, from any category.
     * @return A single array of size 5, that contains a question and its 4 potential answers in QCAAA format.
     */
    public String[] get_Random_Question()
    {
        Random r = new Random();
        int category_id = r.nextInt(3); //TODO: Change based on amount of categories implemented
        int question_num = r.nextInt(20); //TODO: Change based on the amount of questions per category
        return makeJsonArrayRequest(question_num);
    }

    /**
     * Requests a single question within a given category.
     * @return A single array of size 5, containing a question and its 4 potential answers in QCAA format.
     */
    public String[] get_Single_Question(int category_id)
    {
        Random r = new Random();
        int question_num = r.nextInt(20); //TODO: Change based on the amount of questions per category
        return makeJsonArrayRequest(question_num);
    }


    /**
     * Checks the given array to see if the new question number has already been called.
     * @param called_questions An array of numbers relating to question ID's that have been called.
     * @param num_questions_called
     * @param new_question An integer representing a potential new question ID.
     * @return True, if the question has already been called; false otherwise.
     */
    private boolean check_Numbers(int[] called_questions, int num_questions_called, int new_question)
    {
        for(int i = 0; i< num_questions_called; i++)
        {
            if(called_questions[i] == new_question)
            {
                return true;
            }
        }
        //If it doesn't find the new_question within called_questions array, return false.
        return false;
    }

    private String[] packet = new String[5];

    /**
     * Method to make json array request where response starts with [
     * */
    private String[] makeJsonArrayRequest(final int question_number) {

        //final int i = (100 & id) + question_number;
        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) { Log.d(TAG, response.toString());
                try {
                    // Parsing json array response
                    // loop through each json object
                    //for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(question_number);
                        String question = Question_Answers.getString("question");
                        String correct = Question_Answers.getString("correct");
                        String answer1 = Question_Answers.getString("answer1");
                        String answer2 = Question_Answers.getString("answer2");
                        String answer3 = Question_Answers.getString("answer3");
                        String id = Question_Answers.getString("id"); //Probably can delete

                        //TODO: Put these into either an array
                        //String[] packet = new String[5];
                        packet[0]= question;
                        packet[1]= correct;
                        packet[2]= answer1;
                        packet[3]= answer2;
                        packet[4]= answer3;
                    //}
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
    return packet;
    }

    public String url_generator(int question_id)
    {
        String url = "http://coms-309-vb-3.misc.iastate.edu/game/";
        url = url + question_id;
        return url;
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
