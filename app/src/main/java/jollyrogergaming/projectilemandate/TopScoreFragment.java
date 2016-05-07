package jollyrogergaming.projectilemandate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Dialog to ask the user if they want to save their score to the database or not
 */
public class TopScoreFragment extends DialogFragment implements DialogInterface.OnCancelListener {
    private static final String ARG_SCORE = "score";
    private static final String DIALOG_RESTART_GAME = "DialogRestartGame";
    private static final int MAX_NAME_LENGTH = 25;
    private String mText;
    private EditText mUserName;
    private int mScore;
    private SQLiteDatabase mDatabase;
    private static final String TAG = "TopScoreFragment";
    private AlertDialog mDialog;

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
        mDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.score_dialog_title)
                .setPositiveButton(R.string.score_dialog_positive, null) //Declare positive button but do not give it a listener since we do not want to always dismiss the dialog
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
        /**
         * Set positive button to Insert the player's name and score into the database.
         */
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button pos_button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                pos_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( !TextUtils.isEmpty(mUserName.getText()) && mUserName.getText().toString().length() < MAX_NAME_LENGTH ){
                            ContentValues values = getContentValues(mUserName.getText().toString(), mScore);
                            mDatabase.insert(ScoreDbSchema.ScoreTable.NAME, null, values);
                            mDatabase.close();
                            FragmentManager manager = getFragmentManager();
                            RestartGameFragment restartDialog = RestartGameFragment.newInstance();
                            restartDialog.setCancelable(false);
                            restartDialog.show(manager, DIALOG_RESTART_GAME);
                            mDialog.dismiss();
                        }
                        else if( mUserName.getText().toString().length() > MAX_NAME_LENGTH ){
                            Toast.makeText(getContext(), "Sorry, your name was too long. Please enter a name of less than " + MAX_NAME_LENGTH + " Characters", Toast.LENGTH_LONG).show();
                        }
                        else{
                           Toast.makeText(getContext(), "Please enter your name before submitting your score.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        return mDialog;
    }
}
