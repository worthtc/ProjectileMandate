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
 * Created by Trevor on 4/11/2016.
 */
public class RestartGameFragment extends DialogFragment {

    public interface Restartable{
        void restart();
    }
    /**
     * Return an instance of this Fragment with the correct arguments
     * @return An instance of this Fragment
     */
    public static RestartGameFragment newInstance(){

        RestartGameFragment fragment = new RestartGameFragment();
        return fragment;
    }

    /**
     * Create the actual dialog that will be shown to user. This dialog asks the player if the want to replay the game, if they do we restart the game.
     * @param savedInstanceState
     * @return The dialog to be show
     */
    public Dialog onCreateDialog( Bundle savedInstanceState ){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_restart_game, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.restart_game_title)
                .setPositiveButton(R.string.restart_game_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Restartable) getActivity()).restart();
                    }
                })
                .setNegativeButton(R.string.restart_game_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .create();
    }

}
