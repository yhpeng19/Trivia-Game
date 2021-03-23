package vb_3.trivial.admin;

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

public class Question_Pack_item_Activity extends AppCompatActivity {
    private String url = "http://coms-309-vb-3.misc.iastate.edu:8080/questions/approve";
    private TextView tx;
    private Button sub;
    private EditText question;
    private EditText correct;
    private EditText wronganswer1;
    private EditText wronganswer2;
    private EditText wronganswer3;
    private EditText category;
    private View mProgressView;
    private View mFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__pack_item_);

        question = (EditText) findViewById(R.id.question_approve_question);
        question.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        correct = (EditText) findViewById(R.id.question_approve_correct);
        correct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer1 = (EditText) findViewById(R.id.question_approve_answer1);
        wronganswer1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer2 = (EditText) findViewById(R.id.question_approve_answer2);
        wronganswer2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        wronganswer3 = (EditText) findViewById(R.id.question_approve_answer3);
        wronganswer3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        category = (EditText) findViewById(R.id.question_approve_category);
        category.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return true;
            }
        });

        sub = findViewById(R.id.Question_approve_submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveQuestion();
            }
        });

        tx = findViewById(R.id.question_pack_display);
        String print = "DateProposed:\t" + Question_Approve.Package[Global_Varable.Approving_index][0]
                +"\n"+"Category\t" + Question_Approve.Package[Global_Varable.Approving_index][1]
                +"\n"+"Id\t"+ Question_Approve.Package[Global_Varable.Approving_index][2]
                +"\n"+"Question\t"+Question_Approve.Package[Global_Varable.Approving_index][3]
                +"\n"+"Correct answer:\t"+Question_Approve.Package[Global_Varable.Approving_index][4]
                +"\n"+"Wrong answer1:\t"+Question_Approve.Package[Global_Varable.Approving_index][5]
                +"\n"+"Wrong answer2:\t"+Question_Approve.Package[Global_Varable.Approving_index][6]
                +"\n"+"Wrong answer3:\t"+Question_Approve.Package[Global_Varable.Approving_index][7];
        tx.setText(print);

        mFormView = findViewById(R.id.question_approve_form);
        mProgressView = findViewById(R.id.question_approve_progress);
    }

    /**
     * Make a JSON object request, and send a JSON object to server to approve a question.
     * */
    private void approveQuestion(){

        String Question = question.getText().toString();
        String Correct = correct.getText().toString();
        String WrongAnswer1 = wronganswer1.getText().toString();
        String WrongAnswer2 = wronganswer2.getText().toString();
        String WrongAnswer3 = wronganswer3.getText().toString();
        String Category = category.getText().toString();

        if(Question.equals(""))
            Question=Question_Approve.Package[Global_Varable.Approving_index][3];
        if(Correct.equals(""))
            Correct=Question_Approve.Package[Global_Varable.Approving_index][4];
        if(WrongAnswer1.equals(""))
            WrongAnswer1=Question_Approve.Package[Global_Varable.Approving_index][5];
        if(WrongAnswer2.equals(""))
            WrongAnswer2=Question_Approve.Package[Global_Varable.Approving_index][6];
        if(WrongAnswer3.equals(""))
            WrongAnswer3=Question_Approve.Package[Global_Varable.Approving_index][7];
        if(Category.equals(""))
           Category=Question_Approve.Package[Global_Varable.Approving_index][1];

        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        params.put("id", Question_Approve.Package[Global_Varable.Approving_index][2]);
        params.put("question", Question);
        params.put("correct", Correct);
        params.put("wrong1", WrongAnswer1);
        params.put("wrong2", WrongAnswer2);
        params.put("wrong3", WrongAnswer3);
        params.put("category", Category);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(response.getString("status").equals("true")){
                        Intent intent = new Intent(Question_Pack_item_Activity.this, Admin_Activity.class);
                        startActivity(intent);
                    }else{
                        if(response.getString("error").equals("token")){
                            Toast errorToast = Toast.makeText(Question_Pack_item_Activity.this, " Token wasn’t found!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("date")){
                            Toast errorToast = Toast.makeText(Question_Pack_item_Activity.this, " Token expired!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }else if(response.getString("error").equals("role")){
                            Toast errorToast = Toast.makeText(Question_Pack_item_Activity.this, "User is not a player!", Toast.LENGTH_SHORT);
                            errorToast.show();
                        }
                    }
                }catch(JSONException e){
                    Toast errorToast = Toast.makeText(Question_Pack_item_Activity.this, "Propose failed", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Question_Pack_item_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
