package jollyrogergaming.projectilemandate.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import jollyrogergaming.projectilemandate.Score;

/**
 * Created by Trevor on 4/10/2016.
 */
public class ScoreCursorWrapper extends CursorWrapper {
    public ScoreCursorWrapper( Cursor cursor ){
        super(cursor);
    }

    public Score getScore(){
        int score = getInt(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.SCORE));
        String user = getString(getColumnIndex(ScoreDbSchema.ScoreTable.Cols.USER));

        return new Score(score, user);
    }
}
