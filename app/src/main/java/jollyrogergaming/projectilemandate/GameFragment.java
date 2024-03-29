package jollyrogergaming.projectilemandate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import jollyrogergaming.projectilemandate.GameView;
import jollyrogergaming.projectilemandate.R;
import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreCursorWrapper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

/**
 * Created by Trevor on 4/10/2016.
 */
public class GameFragment extends Fragment {
    /**
     * varibles used by the Game fragment class
     */
    private TextView mTestTextOne;
    private TextView mTestTextTwo;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    private boolean mFlag; //Set when we are showing the dialogs so they are not shown twice
    private SQLiteDatabase mDatabase;
    private int mScore;
    private TextView mScoreView;


    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";
    private static final String DIALOG_TOP_SCORE = "DialogTopScore";
    private static final String DIALOG_RESTART_GAME = "DialogRestartGame";
    private static final String TAG = "GameActivity";
    /**
     * objects used by the Game fragment class
     */
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

    /**
     * onCreateView method to set the on click listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        View view = inflater.inflate(R.layout.activity_game, container, false);
        mDatabase = new ScoreBaseHelper(getContext()).getReadableDatabase();
        mFlag = false;
        mColorScheme = getArguments().getBoolean(KEY_COLOR_SCHEMA);
        mIsGameHard = getArguments().getBoolean(KEY_IS_GAME_HARD);

        mScore = 0;

        //Main screen
        FrameLayout mainView = (FrameLayout) view.findViewById(R.id.game_view);

        //Create score text view to display the current score to the user
        mScoreView = new TextView(getActivity());
        mScoreView.setGravity(Gravity.CENTER_HORIZONTAL);
        mScoreView.setTextSize(22);
        mScoreView.setText("Score : " + mScore);

        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(100, 100, 100, 100);



        // Create the game view and add it to the game screen.
        mGameView = new GameView(getContext(), null, mIsGameHard, mColorScheme);
        mainView.addView(mGameView);
        mainView.addView(mScoreView);

        return view;
    }

    /**
     * When the activity is resumed, we start a timer task that runs the Projectile Mandate game to completion.
     */
    @Override
    public void onResume() {
        // Create the timer and its task.
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {


                // The handler will tell the background thread to redraw the view.
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if( mGameView.isGameOver() && !mFlag){
                            mTimerTask.cancel();
                            mGameView.release();
                            boolean isScoreTopTen = false;
                            int databaseCount = 0;
                            ScoreCursorWrapper cursor = queryScores(null, null);

                            //Read all of the rows of the database table and see if the player has one of the top ten scores
                            try{
                                cursor.moveToFirst();
                                while(!cursor.isAfterLast()){
                                    databaseCount++;
                                    if( mGameView.getScore() > cursor.getScore().getScore()){
                                        isScoreTopTen = true;
                                        break;
                                    }
                                    cursor.moveToNext();
                                }
                            } finally{
                                cursor.close();
                            }
                            FragmentManager manager = getFragmentManager();
                            if( isScoreTopTen || databaseCount < 10) {
                                TopScoreFragment dialog = TopScoreFragment.newInstance(mGameView.getScore());
                                dialog.setCancelable(false);
                                dialog.show(manager, DIALOG_TOP_SCORE);
                            }
                            else{
                                RestartGameFragment dialog = RestartGameFragment.newInstance();
                                dialog.setCancelable(false);
                                dialog.show(manager, DIALOG_RESTART_GAME);
                            }
                            mFlag = true;
                        }
                        else{

                            mScore = mGameView.getScore();
                            mScoreView.setText("Score: " + mScore);
                            mGameView.invalidate();
                        }
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

    /**
     * Function to run a query on the Scores table and make the retrieval of the results easier
     * @param whereClause The where selection clause
     * @param whereArgs the arguments for the where clause
     * @return A wrapper for the cursor that makes it easy to retrieve the information from the cursor
     */
    private ScoreCursorWrapper queryScores( String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ScoreDbSchema.ScoreTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                ScoreDbSchema.ScoreTable.Cols.SCORE + " DESC", "10" // orderBy
        );

        return new ScoreCursorWrapper(cursor);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mGameView.release();
    }

}
