package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private RecyclerView mScoresRecyclerView;
    private ScoreAdapter mAdapter;
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

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leader_board);
        mScores = new ArrayList<>();
        mDatabase = new ScoreBaseHelper(this).getWritableDatabase();

        ScoreCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                mScores.add(cursor.getScore());
                cursor.moveToNext();
            }
        } finally{
            cursor.close();
        }

        mScoresEmptyList = (TextView) findViewById(R.id.empty_list_view);
        mScoresRecyclerView = (RecyclerView) findViewById(R.id.score_recycler_view);

        if( mScores.size() == 0){
            mScoresEmptyList.setVisibility(View.VISIBLE);
            mScoresRecyclerView.setVisibility(View.GONE);
        } else{
            mScoresEmptyList.setVisibility(View.GONE);
            mScoresRecyclerView.setVisibility(View.VISIBLE);
        }

    }*/

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

        Log.d(TAG, ((Boolean) mColorScheme).toString());
        Log.d(TAG, ((Boolean) mIsGameHard).toString());

        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        mScoresEmptyList = (TextView) view.findViewById(R.id.empty_list_view);
        mScoresRecyclerView = (RecyclerView) view.findViewById(R.id.score_recycler_view);
        mScoresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    /**
     * Function to run a query on the Scores table
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
                ScoreDbSchema.ScoreTable.Cols.SCORE + " DESC" // orderBy
        );

        return new ScoreCursorWrapper(cursor);
    }

    /**
     * Adapter for displaying Scores in the Recycler view
     */
    private class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder>{
        private ArrayList<Score> mScores;

        public ScoreAdapter(ArrayList<Score> scores){
            mScores = scores;
        }

        @Override
        public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            View view = layoutInflater.inflate(R.layout.list_item_score, parent, false);
            return new ScoreHolder(view);
        }

        @Override
        public void onBindViewHolder(ScoreHolder holder, int position){
            Score score = mScores.get(position);
            holder.bindScore(score);
        }

        @Override
        public int getItemCount(){
            return mScores.size();
        }
    }

    /**
     * Class to hold each individual score in the RecyclerView
     */
    private class ScoreHolder extends RecyclerView.ViewHolder{
        private TextView mTextUser;
        private TextView mTextScore;
        private Score mScore;

        public ScoreHolder( View itemView ){
            super(itemView);

            mTextUser = (TextView) itemView.findViewById(R.id.text_user);
            mTextScore = (TextView) itemView.findViewById(R.id.text_score);
        }

        public void bindScore(Score score){
            mScore = score;
            mTextUser.setText(mScore.getUser());
            mTextScore.setText(((Integer)mScore.getScore()).toString());
        }
    }

    /**
     * Update the user interface, creating the adapter if it needs to be created. If there are no scores to be displayed, we display a message telling the user this
     * Otherwise, we show all of the scores in the database.
     */
    public void updateUI(){

        if( mAdapter == null) {
            mAdapter = new ScoreAdapter(mScores);
            mScoresRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }

        if( mScores.size() == 0){
            mScoresEmptyList.setVisibility(View.VISIBLE);
            mScoresRecyclerView.setVisibility(View.GONE);
        } else{
            mScoresEmptyList.setVisibility(View.GONE);
            mScoresRecyclerView.setVisibility(View.VISIBLE);
        }

        //updateSubtitle();

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mDatabase.close();
    }
}
