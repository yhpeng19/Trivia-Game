package vb_3.trivial.player;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import vb_3.trivial.ControllerClasses.app.AppController;
import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;
import vb_3.trivial.admin.Account_Manage;
import vb_3.trivial.admin.Question_Approve;
import vb_3.trivial.admin.Question_Pack_item_Activity;
import vb_3.trivial.admin.item;
import vb_3.trivial.newJsonArrayRequest;

public class Lobbies_List extends AppCompatActivity {

    private String listURL = "http://coms-309-vb-3.misc.iastate.edu:8080/selectLobby";
    private String TAG = "";
    private String[] gameidsave;
    private ArrayList<Lobby_list_item> games = new ArrayList<Lobby_list_item>();
    //private ArrayAdapter<Lobby_list_item> adapter;

    private int game_num;

    private Button Create_lobby_button;
    private ListView lobby_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobbies__list);

        Create_lobby_button=findViewById(R.id.create_lobby_button);
        lobby_list = findViewById(R.id.lobby_listView);


        requestLobbies();

        int p = 0;
        long delay= 2000;
        while(p<1){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < game_num; i++) {
                    Lobby_list_item temp = new Lobby_list_item(gameidsave[i]);
                    games.add(temp);
                }
                Adapter adapter = new Adapter(Lobbies_List.this, R.layout.lobby_list_item, games);
                lobby_list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
        //requestLobbies();
        //Show Lobbies List;

        //Set Listeners and Reactions
                Create_lobby_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Lobbies_List.this, Create_Lobby.class);
                        startActivity(intent);
                    }
                });
            }
        },delay);
            p++;
        }
    }
/*
    public void addItem(String s)
    {
        games.add(s);
        adapter.notifyDataSetChanged();
    }
*/
    private void requestLobbies(){
        newJsonArrayRequest req = new newJsonArrayRequest(Request.Method.GET,listURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    game_num=response.length();

                    gameidsave=new String[game_num];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject game_id = (JSONObject) response.get(i);
                        //String id = game_id.optString("id", "N/A");
                        //String id = game_id.toString();
                        String id = game_id.getString("id");
                        //games.add(id);
                        gameidsave[i]=id;
                        Log.d("GAME ID",id);

                    }
                } catch (JSONException e) {
                    Toast errorToast = Toast.makeText(Lobbies_List.this, "0", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast errorToast = Toast.makeText(Lobbies_List.this, "1", Toast.LENGTH_SHORT);
                errorToast.show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    public class Adapter extends ArrayAdapter<Lobby_list_item> {
        private int Id;

        /**
         *  Constructor for adapter.
         * */
        public Adapter(Context context, int textViewResourceId, ArrayList< Lobby_list_item> object) {
            super(context,textViewResourceId,object);
            Id=textViewResourceId;
        }

        /**
         * @return view which is fixed single item for ViewList.
         * */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Lobby_list_item temp = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            TextView gameId = (TextView) view.findViewById(R.id.lobby_game_id);
            Button check = (Button) view.findViewById(R.id.lobby_join);
            gameId.setText(temp.getgameId()+"");
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global_Varable.lobby_index=position;
                    Global_Varable.Game_ID = gameidsave[position];
                    Intent intent = new Intent(Lobbies_List.this, Lobby.class);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

}