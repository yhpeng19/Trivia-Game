package vb_3.trivial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Testing field for websocket temporarily.
 */
public class Help_Activity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private EditText e1;
    private EditText e2;
    private TextView t1;
    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_);

        button = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        e1 = (EditText) findViewById(R.id.et1);
        e2 = (EditText) findViewById(R.id.et2);
        t1 = (TextView) findViewById(R.id.tx1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Draft[] drafts = {new Draft_6455()};

                //String w = "ws://10.26.13.93:8080/websocket/"+e1.getText().toString();
                //String w = "http://coms-309-vb-3.misc.iastate.edu/websocket/" +e1.getText().toString();
                //String w = "127.0.0.1";
                //String w = "ws://127.0.0.1/websocket/"+e1.getText().toString();
                String w = "ws://coms-309-vb-3.misc.iastate.edu:8080/websocket/" +e1.getText().toString();
                //String w = "ws://echo.websocket.org"; //+ e1.getText();

                try{
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]){
                        @Override
                        public void onMessage(String message){
                            Log.d("", "run() returned: " + message);
                            String s=t1.getText().toString();
                            //t1.setText("hello world");
                            //Log.d("first", "run() returned: " + s);
                            //s=t1.getText().toString();
                            //Log.d("second", "run() returned: " + s);
                            t1.setText(s+"\n Server:"+message);

                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
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


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(e2.getText().toString());
                }
                catch (Exception e)
                {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });
    }
}
