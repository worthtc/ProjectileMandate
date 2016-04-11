package jollyrogergaming.projectilemandate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import jollyrogergaming.projectilemandate.GameView;
import jollyrogergaming.projectilemandate.R;

/**
 * Created by Trevor on 4/10/2016.
 */
public class GameFragment extends Fragment {

    private TextView mTestTextOne;
    private TextView mTestTextTwo;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;


    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";
    private static final String DIALOG_TOP_SCORE = "DialogTopScore";
    private static final String DIALOG_RESTART_GAME = "DialogRestartGame";
    private static final String TAG = "GameActivity";

    GameView mGameView;
    Timer mTimer;
    TimerTask mTimerTask;
    Handler mHandler = new Handler();

    public static GameFragment newInstance(boolean colorScheme, boolean isGameHard){
        Bundle args = new Bundle();
        args.putBoolean(KEY_COLOR_SCHEMA, colorScheme);
        args.putBoolean(KEY_IS_GAME_HARD, isGameHard);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public GameFragment(){
        // Required empty public constructor
    }

    /**
     * onCreateView method to set the on click listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
   // protected void onCreate(Bundle savedInstanceState) {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        View view = inflater.inflate(R.layout.activity_game, container, false);
        //setContentView(R.layout.activity_game);


        //Main screen
        FrameLayout mainView = (FrameLayout) view.findViewById(R.id.game_view);


        // Create the game view and add it to the game screen.
        mGameView = new GameView(getContext());
        mainView.addView(mGameView);

        return view;
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
                        if( mGameView.isGameOver()){
                            mTimerTask.cancel();
                            FragmentManager manager = getFragmentManager();
                            TopScoreFragment dialog = TopScoreFragment.newInstance(mGameView.getScore());
                            dialog.show(manager, DIALOG_TOP_SCORE);
                        }
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
