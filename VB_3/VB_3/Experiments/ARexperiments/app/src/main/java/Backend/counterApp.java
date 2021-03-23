package Backend;

import android.view.View;
import android.widget.TextView;

public class counterApp {

    private int counter = 0;

    private TextView showme;

    public void CounterApp(View view) {
        counter++;
        if(showme != null){
            showme.setText(Integer.toString(counter));
        }
    }
}
