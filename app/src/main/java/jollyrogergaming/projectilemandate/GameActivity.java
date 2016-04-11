package jollyrogergaming.projectilemandate;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    /**
     * onCreate method to set the on click listeners
     *
     * Creates onClick listeners for all buttons in the view. Also starts the TicTacToe game according to the options in the intent
     * @param savedInstanceState
     */
    private TextView mTestTextOne;
    private TextView mTestTextTwo;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";
    private static final String DIALOG_TOP_SCORE = "DialogTopScore";
    private static final String TAG = "GameActivity";

    GameView mGameView;
    Timer mTimer;
    TimerTask mTimerTask;
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);

        //Hide the action bar since this is the game window
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Main screen
        FrameLayout mainView = (FrameLayout) findViewById(R.id.game_view);
        FragmentManager manager = getSupportFragmentManager();
        //TopScoreFragment dialog = TopScoreFragment.newInstance(5);
       // dialog.show(manager, DIALOG_TOP_SCORE);

        // Create the game view and add it to the game screen.

        mGameView = new jollyrogergaming.projectilemandate.GameView(this);
        mainView.addView(mGameView);

//        mTestTextOne = (TextView) findViewById(R.id.text_one);
//        mTestTextOne.setText("ColorScheme: " + mColorScheme);
//
//        mTestTextTwo = (TextView) findViewById(R.id.text_two);
//        mTestTextTwo.setText("IsGameHard: " + mIsGameHard);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onResume() {
        // Create the timer and its task.
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

                // **CALL CALCULATIONS FOR GAME OBJECTS HERE**

                // The handler will tell the background thread to redraw the view.
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGameView.invalidate();
                    }
                });
            }
        };

        // Assign the task to the timer.
        mTimer.schedule(mTimerTask,
                10,     // delay (ms) before task is to be executed
                10);    // time (ms) between successive task executions

        super.onResume();
    }


}
