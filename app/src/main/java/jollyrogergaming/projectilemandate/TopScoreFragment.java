package jollyrogergaming.projectilemandate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

/**
 * Created by Trevor on 4/10/2016.
 */
public class TopScoreFragment extends DialogFragment {
    private static final String ARG_SCORE = "score";
    private String mText;
    private EditText mUserName;
    private int mScore;
    private SQLiteDatabase mDatabase;

    public static TopScoreFragment newInstance(int score){
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);

        TopScoreFragment fragment = new TopScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ContentValues getContentValues(String user, int score){
        ContentValues values = new ContentValues();
        values.put(ScoreDbSchema.ScoreTable.Cols.USER, user);
        values.put(ScoreDbSchema.ScoreTable.Cols.SCORE, score);

        return values;
    }

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
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = getContentValues(mUserName.getText().toString(), mScore);
                        mDatabase.insert(ScoreDbSchema.ScoreTable.NAME, null, values);
                        mDatabase.close();
                        //Submit to database
                    }
                })
                .setNegativeButton(R.string.score_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.close();
                    }
                })
                .create();
    }
}
