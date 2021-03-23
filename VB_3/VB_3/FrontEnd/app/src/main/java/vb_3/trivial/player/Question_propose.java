package vb_3.trivial.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;

/**
 *   Class Question_propose of player, which is an activity for player to propose the new questions to the server.
 * */
public class Question_propose extends AppCompatActivity {

    private EditText question;
    private EditText correct;
    private EditText wronganswer1;
    private EditText wronganswer2;
    private EditText wronganswer3;
    private EditText category;
    private Button submit;
    private View mProgressView;
    private View mFormView;
    private final String URL = "http://coms-309-vb-3.misc.iastate.edu:8080/questions/propose";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetion_propose);

        question = (EditText) findViewById(R.id.question_propose_question);
        question.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        correct = (EditText) findViewById(R.id.question_propose_correct);
        correct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer1 = (EditText) findViewById(R.id.question_propose_answer1);
        wronganswer1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer2 = (EditText) findViewById(R.id.question_propose_answer2);
        wronganswer2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer3 = (EditText) findViewById(R.id.question_propose_answer3);
        wronganswer3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        category = (EditText) findViewById(R.id.question_propose_category);
        category.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        Button submit = (Button) findViewById(R.id.question_propose_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitQuestion();
            }
        });

        mFormView = findViewById(R.id.question_propose_form);
        mProgressView = findViewById(R.id.question_propose_progress);
    }

    /**
     *  Make a Json Request which send the proposed question_pack to server.
     * */
    private void submitQuestion(){
        final String Question = question.getText().toString();
        final String Correct = correct.getText().toString();
        final String WrongAnswer1 = wronganswer1.getText().toString();
        final String WrongAnswer2 = wronganswer2.getText().toString();
        final String WrongAnswer3 = wronganswer3.getText().toString();
        final String Category = category.getText().toString();
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("question", Question);
        params.put("correct", Correct);
        params.put("wrong1", WrongAnswer1);
        params.put("wrong2", WrongAnswer2);
        params.put("wrong3", WrongAnswer3);
        params.put("category", Category);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                   if(response.getString("status").equals("true")){
                       Intent intent = new Intent(Question_propose.this, Main_Activity.class);
                       startActivity(intent);
                   }else{
                       if(response.getString("error").equals("token")){
                           Toast errorToast = Toast.makeText(Question_propose.this, " Token wasn’t found!", Toast.LENGTH_SHORT);
                           errorToast.show();
                       }else if(response.getString("error").equals("date")){
                           Toast errorToast = Toast.makeText(Question_propose.this, " Token expired!", Toast.LENGTH_SHORT);
                           errorToast.show();
                       }else if(response.getString("error").equals("role")){
                           Toast errorToast = Toast.makeText(Question_propose.this, "User is not a player!", Toast.LENGTH_SHORT);
                           errorToast.show();
                       }
                   }
                }catch(JSONException e){
                    Toast errorToast = Toast.makeText(Question_propose.this, "Propose failed", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Question_propose.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}

