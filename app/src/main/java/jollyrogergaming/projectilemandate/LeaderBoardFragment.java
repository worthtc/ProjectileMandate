package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreCursorWrapper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

//public class LeaderBoardFragment extends AppCompatActivity {

/**
 * Fragment that will display the players and their top scores for Projectile Mandate
 */
public class LeaderBoardFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private ArrayList<Score> mScores;
    private TextView mScoresEmptyList;
    private TableLayout mTable;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";
    public static final String TAG = "LeaderBoardFragment";


    public static LeaderBoardFragment newInstance(boolean colorScheme, boolean isGameHard){
        Bundle args = new Bundle();
        args.putBoolean(KEY_COLOR_SCHEMA, colorScheme);
        args.putBoolean(KEY_IS_GAME_HARD, isGameHard);

        LeaderBoardFragment fragment = new LeaderBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Class to inflate the view for the Fragment, then read in the data from the database, and finally update the user interface
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return The view that was created
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        mScores = new ArrayList<>();
        mDatabase = new ScoreBaseHelper(getActivity()).getReadableDatabase();
        setHasOptionsMenu(true);

        mColorScheme = getArguments().getBoolean(KEY_COLOR_SCHEMA);
        mIsGameHard = getArguments().getBoolean(KEY_IS_GAME_HARD);

        ScoreCursorWrapper cursor = queryScores(null, null);

        //Read all of the rows of the database table and add them to our array list
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                mScores.add(cursor.getScore());
                cursor.moveToNext();
            }
        } finally{
            cursor.close();
        }

        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        mScoresEmptyList = (TextView) view.findViewById(R.id.empty_list_view);
        mTable = (TableLayout) view.findViewById(R.id.table_view);

        View dividerOne = view.findViewById(R.id.divider_one);
        if( mColorScheme ){
            dividerOne.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            dividerOne.setBackgroundColor(0xFF000000);
        }

        View dividerTwo = view.findViewById(R.id.divider_two);
        if( mColorScheme ){
            dividerTwo.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            dividerTwo.setBackgroundColor(0xFF000000);
        }

        View dividerHorizontal = view.findViewById(R.id.divider_horiz);
        if( mColorScheme ){
            dividerHorizontal.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            dividerHorizontal.setBackgroundColor(0xFF000000);
        }

        /*
        mScoresRecyclerView = (RecyclerView) view.findViewById(R.id.score_recycler_view);
        mScoresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        */

        updateUI();

        return view;
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


    /**
     * Update the user interface, taking all of the scores that were stored in the database and creates a table row for each score. If there are no scores, we show a TextView to the user
     */
    public void updateUI(){
        //If we do not have any scores we set the table to be gone and show the TextView. If we do have scores we only show the table
        if( mScores.size() == 0){
            mScoresEmptyList.setVisibility(View.VISIBLE);
            mTable.setVisibility(View.GONE);
        } else{
            mScoresEmptyList.setVisibility(View.GONE);
            mTable.setVisibility(View.VISIBLE);
        }

        for( int i = 0; i < mScores.size(); i++){
            addTableRow(mScores.get(i), i);
        }

    }

    /**
     * Generate a new row for the table and add it to the table.
     * @param score - instance of the Score class that holds the data we store in the table
     * @param position - index of the score we are currently on. Starts at 0 and ends at 9. This is converted to a string for display
     */
    public void addTableRow( Score score, int position ){

        TableRow row = new TableRow(getContext());
        row.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView placeText = new TextView(getContext());
        TableRow.LayoutParams placeParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        placeParams.gravity = Gravity.CENTER;
        placeText.setLayoutParams(placeParams);
        placeText.setText(getPositionText(position));
        placeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        row.addView(placeText);

        View verticalBarOne = new View(getContext());
        verticalBarOne.setLayoutParams(new TableRow.LayoutParams(
                (int) getResources().getDimension(R.dimen.bar_width),
                TableRow.LayoutParams.MATCH_PARENT

        ));
        if( mColorScheme ){
            verticalBarOne.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            verticalBarOne.setBackgroundColor(0xFF000000);
        }
        row.addView(verticalBarOne);

        TextView nameText = new TextView(getContext());
        TableRow.LayoutParams nameParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        nameParams.weight = 10;
        nameText.setLayoutParams(nameParams);
        nameText.setText(score.getUser());
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        nameText.setGravity(Gravity.CENTER);
        row.addView(nameText);

        View verticalBarTwo = new View(getContext());
        verticalBarTwo.setLayoutParams(new TableRow.LayoutParams(
                (int) getResources().getDimension(R.dimen.bar_width),
                TableRow.LayoutParams.MATCH_PARENT

        ));
        if( mColorScheme ){
            verticalBarTwo.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            verticalBarTwo.setBackgroundColor(0xFF000000);
        }
        row.addView(verticalBarTwo);

        TextView scoreText = new TextView(getContext());
        scoreText.setText(((Integer) score.getScore()).toString());
        TableRow.LayoutParams scoreParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
        );
        //scoreParams.gravity = Gravity.RIGHT;
        scoreParams.weight = 2;
        scoreText.setLayoutParams(scoreParams);
        scoreText.setGravity(Gravity.CENTER);
        scoreText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        row.addView(scoreText);
        mTable.addView(row);

        View horizontalBar = new View(getContext());
        horizontalBar.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.bar_width)
        ));
        if( mColorScheme ){
            horizontalBar.setBackgroundColor(0xFFFFFFFF);
        }
        else{
            horizontalBar.setBackgroundColor(0xFF000000);
        }
        mTable.addView(horizontalBar);
        return;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mDatabase.close();
    }

    /**
     * Return the text for position in the list. Works up until position 9 since we only select 10 scores
     * @param position the position to turn into a string. Starts at zero so position zero corresponds to the first score in the list
     * @return a string corresponding to the position. Ex. 0 => First, 1 => Second
     */
    public String getPositionText(int position){

        switch( position ){
            case 0:
                return "First";
            case 1:
                return "Second";
            case 2:
                return "Third";
            case 3:
                return "Fourth";
            case 4:
                return "Fifth";
            case 5:
                return "Sixth";
            case 6:
                return "Seventh";
            case 7:
                return "Eighth";
            case 8:
                return "Ninth";
            case 9:
                return "Tenth";
            default:
                return "NO_POSITION_FOUND";
        }
    }
}
