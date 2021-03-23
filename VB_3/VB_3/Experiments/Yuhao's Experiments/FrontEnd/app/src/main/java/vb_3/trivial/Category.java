package vb_3.trivial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Category extends AppCompatActivity {

    private int category_ID;

    public int get_category_ID(){
        return category_ID;
    }

    public void set_category_ID(int category_ID){
        this.category_ID = category_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }
}
