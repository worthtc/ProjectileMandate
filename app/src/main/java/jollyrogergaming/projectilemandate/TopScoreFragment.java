package jollyrogergaming.projectilemandate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Dialog to ask the user if they want to save their score to the database or not
 */
public class TopScoreFragment extends DialogFragment {
    private static final String ARG_SCORE = "score";
    private static final String DIALOG_RESTART_GAME = "DialogRestartGame";
    private String mText;
    private EditText mUserName;
    private int mScore;
    private SQLiteDatabase mDatabase;

    /**
     * Return an instance of this Fragment with the correct arguments
     * @param score The final score that the user had for the game
     * @return An instance of this Fragment
     */
    public static TopScoreFragment newInstance(int score){
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);

        TopScoreFragment fragment = new TopScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create a value that can be pushed to our database
     * @param user the name of the player
     * @param score the score of the player
     * @return an instance of the ContentValues class that can be easily inserted into the database
     */
    public static ContentValues getContentValues(String user, int score){
        ContentValues values = new ContentValues();
        values.put(ScoreDbSchema.ScoreTable.Cols.USER, user);
        values.put(ScoreDbSchema.ScoreTable.Cols.SCORE, score);

        return values;
    }

    /**
     * Create the actual dialog that will be shown to user. This dialog asks the player for their name, and pushes their score to
     * the database if they choose to.
     * @param savedInstanceState
     * @return The dialog to be show
     */
    public Dialog onCreateDialog( Bundle savedInstanceState ){
        mScore = getArguments().getInt(ARG_SCORE);
        mDatabase = new ScoreBaseHelper(getContext()).getWritableDatabase();

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_score, null);

        mText = getString(R.string.score_dialog, mScore);
        TextView textView = (TextView) v.findViewById(R.id.score_text);
        textView.setText(mText);
        mUserName = (EditText) v.findViewById(R.id.player_name);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.score_dialog_title)
                .setPositiveButton(R.string.score_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * Insert the player's name and score into the dabase
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        //Submit to database
                        ContentValues values = getContentValues(mUserName.getText().toString(), mScore);
                        mDatabase.insert(ScoreDbSchema.ScoreTable.NAME, null, values);
                        mDatabase.close();
                        FragmentManager manager = getFragmentManager();
                        RestartGameFragment restartDialog = RestartGameFragment.newInstance();
                        restartDialog.show(manager, DIALOG_RESTART_GAME);
                    }
                })
                .setNegativeButton(R.string.score_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    /**
                     * Simply close the database
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.close();
                        FragmentManager manager = getFragmentManager();
                        RestartGameFragment restartDialog = RestartGameFragment.newInstance();
                        restartDialog.show(manager, DIALOG_RESTART_GAME);
                    }
                })
                .create();
    }
}
