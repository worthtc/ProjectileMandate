package jollyrogergaming.projectilemandate.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import jollyrogergaming.projectilemandate.Score;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Class to make querying of the SQLite database less cumbersome
 */
public class ScoreCursorWrapper extends CursorWrapper {
    public ScoreCursorWrapper( Cursor cursor ){
        super(cursor);
    }

    /**
     * Method to grab the score and username of a row of the database and return it as a Score
     * @return the Score with the corresponding score and username from the database
     */
    public Score getScore(){
        int score = getInt(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.SCORE));
        String user = getString(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.USER));

        return new Score(score, user);
    }
}
