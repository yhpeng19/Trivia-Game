package vb_3.trivial.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;
import vb_3.trivial.newJsonArrayRequest;

/**
 *   Class Question_Approve of admin, which is an activity for admin to approve the pending questions proposed by the player.
 * */
public class Question_Approve extends AppCompatActivity {
    private ListView lst;
    private String URL = "http://coms-309-vb-3.misc.iastate.edu:8080/questions/pending";
    private int question_number;
    public static String[][] Package;
    private ArrayList<item> itemList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_approve);
        LoadQuetionPack();
        int p = 0;
        long delay= 500;
        while(p<1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < question_number; i++) {
                        item temp = new item(Package[i][0],Package[i][1],Package[i][2]);
                        itemList.add(temp);
                    }
                    Adapter adapter = new Adapter(Question_Approve.this, R.layout.user_item,itemList);
                    lst = (ListView) findViewById(R.id.question_approve_listView);
                    lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            },delay);
            p++;
        }
    }


    public class Adapter extends ArrayAdapter<item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public Adapter(Context context, int textViewResourceId, ArrayList<item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView date_ = (TextView) view.findViewById(R.id.question_approve_date);
            TextView category_ = (TextView) view.findViewById(R.id.question_approve_category);
            TextView ID_ = (TextView) view.findViewById(R.id.question_ID);
            Button check = (Button) view.findViewById(R.id.question_approve_check);
            date_.setText(temp.getDate()+"");
            category_.setText(temp.getCategory()+"");
            ID_.setText(temp.getID()+"");
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.Approving_index=position;
                    Intent intent = new Intent(Question_Approve.this,Question_Pack_item_Activity.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    /**
     *   void method LoadQuetionPack full filled the 2D array by receiving the data from server.
     * */
    public void LoadQuetionPack() {
        Map<String, String> params = new HashMap();
        params.put("token", Global_Varable.token);
        JSONObject parameters = new JSONObject(params);
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.POST,URL, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    question_number=response.length();
                    Package = new String[question_number][8];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Question_Answers = (JSONObject) response.get(i);
                        String id = Question_Answers.getString("id");
                        String dateProposed = Question_Answers.getString("dateProposed");
                        String question = Question_Answers.getString("question");
                        String category = Question_Answers.getString("category");
                        String correct = Question_Answers.getString("correct_answer");
                        String answer1 = Question_Answers.getString("wrong_answer1");
                        String answer2 = Question_Answers.getString("wrong_answer2");
                        String answer3 = Question_Answers.getString("wrong_answer3");
                        Package[i][0] = dateProposed;
                        Package[i][1] = category;
                        Package[i][2] = id;
                        Package[i][3] = question;
                        Package[i][4] = correct;
                        Package[i][5] = answer1;
                        Package[i][6] = answer2;
                        Package[i][7] = answer3;
                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Question_Approve.this, "0", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Question_Approve.this, "1", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}

