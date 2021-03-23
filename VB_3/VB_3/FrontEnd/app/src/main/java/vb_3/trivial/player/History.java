package vb_3.trivial.player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class History extends AppCompatActivity {
/*
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textview = findViewById(R.id.)
        public void addFriend() {
            Map<String, String> params = new HashMap();
            params.put("token", Global_Varable.token);
            params.put("username", Global_Varable.username_add);
            JSONObject parameters = new JSONObject(params);
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, add_friend_url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    response.toString("numPlayers");
                    response.toString("currentUser");
                    response.toString("currentUserScore");
                    response.toString("player1"); //- username of the player the game was played against (could be null)
                    response.toString("score1"); //- score of the first enemy
                    response.toString("player2");
                    response.toString("score2");
                    response.toString("player3");
                    response.toString("score3");
                }catch(JSONException e){
                        Toast errorToast = Toast.makeText(Friend_Activity.this, "Sorry, there is an error happened!", Toast.LENGTH_SHORT);
                        errorToast.show();
                        // Recovery
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast errorToast = Toast.makeText(Friend_Activity.this, "Check your internet connection!", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            });
            Volley.newRequestQueue(this).add(jsonRequest);
        }
    }
 */

}
