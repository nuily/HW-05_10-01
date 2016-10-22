package nyc.c4q.huilin.simon_game_hw_05;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Simon";
    private String color;
    private Button green;
    private Button red;
    private Button yellow;
    private Button blue;
    private Button start;
    private Button round;
    private int roundCnt;
    private int turns;


    ArrayList<String> simon = new ArrayList<String>();
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();

    }

    private class HandleClick implements View.OnClickListener {
        public void onClick(View view) {
            Fader.RunAlphaAnimation(view);
            color = getResources().getResourceEntryName(view.getId());
            Log.d("My Choice:", color);
            turns++;
            Log.d("Turns:", String.valueOf(turns));

            if (turns % 2 == 0) {
                String simonsChoice = simon.get(simon.size()-1);
                if (color.equalsIgnoreCase(simonsChoice)) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            simonThenPlayer();
                        }
                    },1000);
                } else {
                    Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_LONG).show();
                    turnOffButtons();
                    start.setText("Restart");
                    start.setClickable(true);
                }
            } else if (roundCnt == 0) {
                Toast.makeText(MainActivity.this, "Click the Start button!", Toast.LENGTH_SHORT).show();
                turnOffButtons();
            }
        }
    }

    public void initButtons() {
        green = (Button) findViewById(R.id.green);
        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        blue = (Button) findViewById(R.id.blue);
        start = (Button) findViewById(R.id.start);
        round = (Button) findViewById(R.id.round);

        green.setOnClickListener(new HandleClick());
        red.setOnClickListener(new HandleClick());
        yellow.setOnClickListener(new HandleClick());
        blue.setOnClickListener(new HandleClick());
        start.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (roundCnt != 0) {
                                             roundCnt = 0;
                                         }  simonThenPlayer();
                                     }
                                 });


        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start.getText().equals("Start")) {
                    Toast.makeText(MainActivity.this, "Trying to restart? No no no, not yet!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "You got up to round " + roundCnt, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void simonThenPlayer() {

            /* first simon plays then player method is called
    * once clicked, compare that choice to the place in the simon array list
    * & loop that comparison depending on the array size
    * & once all comparisons OK then call simon's method again (which should call players method after) */

        start.setClickable(false);

        if (roundCnt == 0) {
            simon.clear();
            turns = 0;
        }

        roundCnt++;
        round.setText("Round:" + roundCnt);

        turnOffButtons();

        int index = random.nextInt(4);
        switch (index) {
            case 0:
                green.callOnClick();
                simon.add("green");
                break;
            case 1:
                red.callOnClick();
                simon.add("red");
                break;
            case 2:
                yellow.callOnClick();
                simon.add("yellow");
                break;
            case 3:
                blue.callOnClick();
                simon.add("blue");
                break;
        }
        Log.d(TAG, String.valueOf(simon));

        turnOnButtons();


    }

    public void turnOffButtons() {
        green.setClickable(false);
        red.setClickable(false);
        yellow.setClickable(false);
        blue.setClickable(false);
    }

    public void turnOnButtons() {
        green.setClickable(true);
        red.setClickable(true);
        yellow.setClickable(true);
        blue.setClickable(true);
    }


}

