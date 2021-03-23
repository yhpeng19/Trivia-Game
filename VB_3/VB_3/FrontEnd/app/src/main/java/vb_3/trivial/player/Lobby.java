package vb_3.trivial.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import vb_3.trivial.Global_Varable;
import vb_3.trivial.R;

public class Lobby extends AppCompatActivity {

    private WebSocketClient wsc;

    private TextView lobby_name;
    private TextView player1_text;
    private TextView player2_text;
    private TextView player3_text;
    private TextView player4_text;
    private TextView game_id_text;

    private Button ready_button;

    private String player1;
    private String p1stat = "Not Ready";
    private String player2;
    private String p2stat = "Not Ready";
    private String player3;
    private String p3stat = "Not Ready";
    private String player4;
    private String p4stat = "Not Ready";

    private WebSocketClient cc;
    //private String URL = "ws://coms-309-vb-3.misc.iastate.edu:8080/game_lobby/" + Global_Varable.username;
    private String URL = "ws://coms-309-vb-3.misc.iastate.edu:8080/game_lobby/" + Global_Varable.username + "/" + Global_Varable.Game_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        lobby_name = findViewById(R.id.lobbyName_tv);
        player1_text = findViewById(R.id.all_players_tv);
        ready_button = findViewById(R.id.ready_button);

        Draft[] drafts = {new Draft_6455()};

        try{
            Log.d("Socket:", "Trying lobby socket");
            cc = new WebSocketClient(new URI(URL), (Draft) drafts[0]){
                @Override
                public void onMessage(String message){
                    Log.d("", "run() returned: " + message);
                    if(message.equals("ALL READY"))
                    {
                        Intent intent  = new Intent(Lobby.this, GameMechanics.class);
                        startActivity(intent);
                    }
                    else{
                        player1_text.setText(message);
                        if(message.equals("ALL READY")) {
                            Intent intent = new Intent(Lobby.this, GameMechanics.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    //Take in all existing players

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }


            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();

        ready_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc.send("ready"); //Note: unready
            }
        });


        lobby_name.setText(Global_Varable.Game_ID);
    }


    @Override
    protected void onResume(){
        super.onResume();

    }


}
