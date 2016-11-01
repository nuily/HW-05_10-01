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

import static android.util.Log.d;

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
    private int choice;
    private boolean isCorrect;


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
            d("My Choice", color);
            if (!simon.isEmpty()) {
                choice++;
                Log.d("Simon's choice", simon.get(choice - 1));

                if (color.equalsIgnoreCase(simon.get(choice - 1))) {
                    isCorrect = true;
                } else {
                    isCorrect = false;
                }
                // how will this wait for the next input to continue comparing from where last left off

                if (isCorrect && choice == roundCnt) {
                    turnOffButtons();
                    choice = 0;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runGame();
                        }
                    }, 1000);
                } else if (!isCorrect) {
                    Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_LONG).show();
                    turnOffButtons();
                    choice = 0;
                    start.setText("Restart");
                    start.setClickable(true);
                }
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
                    simon.clear();
                    start.setText("Start");
                }
                runGame();
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


    public void runGame() {

            /* first simon plays then player method is called
    * once clicked, compare that choice to the place in the simon array list
    * & loop that comparison depending on the array size
    * & once all comparisons OK then call simon's method again (which should call players method after) */

        start.setClickable(false);

        if (roundCnt == 0) {
            simon.clear();
//            turns = 0;
        }

        roundCnt++;
        round.setText("Round:" + roundCnt);

        Handler handler = new Handler();

        if (!simon.isEmpty()) {
            for (int i = 0; i <= simon.size(); i++) {
                final int finalI = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!simon.isEmpty() && finalI != simon.size()) {

                            switch (simon.get(finalI)) {
                                case "green":
                                    Fader.RunAlphaAnimation(green);
                                    break;
                                case "red":
                                    Fader.RunAlphaAnimation(red);
                                    break;
                                case "yellow":
                                    Fader.RunAlphaAnimation(yellow);
                                    break;
                                case "blue":
                                    Fader.RunAlphaAnimation(blue);
                                    break;
                            }

                        } else {
                            simonPlays();
                        }
                    }
                }, 0 + (finalI * 1000));
            }
        } else {
            simonPlays();
        }


    }

    public void simonPlays() {
        int index = random.nextInt(4);
        switch (index) {
            case 0:
                Fader.RunAlphaAnimation(green);
                simon.add("green");
                break;
            case 1:
                Fader.RunAlphaAnimation(red);
                simon.add("red");
                break;
            case 2:
                Fader.RunAlphaAnimation(yellow);
                simon.add("yellow");
                break;
            case 3:
                Fader.RunAlphaAnimation(blue);
                simon.add("blue");
                break;
        }

        d(TAG, String.valueOf(simon));

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

