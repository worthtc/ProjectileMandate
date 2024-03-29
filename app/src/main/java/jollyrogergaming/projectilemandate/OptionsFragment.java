package jollyrogergaming.projectilemandate;

import android.app.TaskStackBuilder;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

/**
 * Created by Trevor on 4/10/2016.
 *
 */
public class OptionsFragment extends Fragment{
    /**
     * options used through out the option fragments class
     */
    private Button mLightButton;
    private Button mDarkButton;
    private Button mEasyButton;
    private Button mHardButton;
    private Button mClearDatabaseButton;
    private Button mConfirmButton;
    private RadioGroup mColorSchemeGroup;
    private RadioGroup mDifficultyGroup;
    private SQLiteDatabase mDatabase;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";

    public static OptionsFragment newInstance(boolean colorScheme, boolean isGameHard){
        Bundle args = new Bundle();
        args.putBoolean(KEY_COLOR_SCHEMA, colorScheme);
        args.putBoolean(KEY_IS_GAME_HARD, isGameHard);

        OptionsFragment fragment = new OptionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create action listeners for all of the buttons in this activities layout
     * @param savedInstanceState
     */
    @Override
   // protected void onCreate(Bundle savedInstanceState) {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){

        View view = inflater.inflate(R.layout.activity_options, container, false);
        mDatabase = new ScoreBaseHelper(getContext()).getWritableDatabase();

        mColorScheme = getArguments().getBoolean(KEY_COLOR_SCHEMA);
        mIsGameHard = getArguments().getBoolean(KEY_IS_GAME_HARD);

        mColorSchemeGroup = (RadioGroup) view.findViewById(R.id.color_scheme_group);
        mDifficultyGroup = (RadioGroup) view.findViewById(R.id.difficulty_group);

        mLightButton = (Button) view.findViewById(R.id.light_button);
        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorScheme = false;
                storeResult();
            }
        });

        mDarkButton = (Button) view.findViewById(R.id.dark_button);
        mDarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorScheme = true;
                storeResult();
            }
        });

        mEasyButton = (Button) view.findViewById(R.id.easy_button);
        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsGameHard = false;
                storeResult();
            }
        });

        mHardButton = (Button) view.findViewById(R.id.hard_button);
        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsGameHard = true;
                storeResult();
            }
        });

        mClearDatabaseButton = (Button) view.findViewById(R.id.clear_database_button);
        mClearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.delete(ScoreDbSchema.ScoreTable.NAME, null, null);
            }
        });

        mConfirmButton = (Button) view.findViewById(R.id.confirm_button);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeResult();
                getActivity().finish();
            }
        });

        if( mColorScheme ){
            mColorSchemeGroup.check(R.id.dark_button);
        }
        else {
            mColorSchemeGroup.check(R.id.light_button);
        }

        if( mIsGameHard ){
            mDifficultyGroup.check(R.id.hard_button);
        }
        else {
            mDifficultyGroup.check(R.id.easy_button);
        }
        //If we have a saved instance state, then we want to restore the saved data. Then we store it as a result in case the user then hits the back or up button
        if( savedInstanceState != null){
            mColorScheme = savedInstanceState.getBoolean(KEY_COLOR_SCHEMA);
            mIsGameHard = savedInstanceState.getBoolean(KEY_IS_GAME_HARD);

            storeResult();
        }

        return view;
    }

    /**
     * Store the current state of the options set by the user into the activity's result
     */
    public void storeResult(){
        Intent data = getActivity().getIntent();
        data.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
        data.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
        getActivity().setResult(getActivity().RESULT_OK, data);
    }

    /**
     * Store the current state of the fragment if the user rotates the screen
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_COLOR_SCHEMA, mColorScheme);
        savedInstanceState.putBoolean(KEY_IS_GAME_HARD, mIsGameHard);
    }

    /**
     * Closes the database when the gragment is destroyed
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        mDatabase.close();
    }

}
