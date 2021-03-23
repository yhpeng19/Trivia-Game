package vb_3.trivial.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vb_3.trivial.R;

public class Category extends AppCompatActivity {

    /**
     * ID for classifying which category the given player wants to play.
     */
    private int category_ID;
    /**
     * Status of the game, whether the game should be played/started as a single or multi-player game.
     */
    public static int mode = 0; // 0 means single-player, 1 means multiple-player

    /**
     * Tool for retrieving the category ID for use elsewhere.
     * @return categroy_ID
     */
    public int get_category_ID(){
        return category_ID;
    }

    /**
     * Tool for changing the category ID based on which category is selected.
     * @param category_ID
     */
    public void set_category_ID(int category_ID){
        this.category_ID = category_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Button category1;
        category1 = findViewById(R.id.category_1_button);
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_ID = 1;
                Intent intent = new Intent(Category.this, GameMechanics.class);
                startActivity(intent);

            }
        });

        Button category2;
        category2 = findViewById(R.id.category_0_button);
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_ID = 2;
                Intent intent = new Intent(Category.this, GameMechanics.class);
                startActivity(intent);

            }
        });

        Button category3;
        category3 = findViewById(R.id.category_2_button);
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_ID = 3;
                Intent intent = new Intent(Category.this, GameMechanics.class);
                startActivity(intent);
            }
        });
    }

}
