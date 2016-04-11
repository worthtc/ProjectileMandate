package jollyrogergaming.projectilemandate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Trevor on 4/10/2016.
 */
public class ScoreBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "scoreBase.db";

    public ScoreBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ScoreDbSchema.ScoreTable.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    ScoreDbSchema.ScoreTable.Cols.USER + ", " +
                    ScoreDbSchema.ScoreTable.Cols.SCORE +
                    ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Add in code to update old db version
    }
}
