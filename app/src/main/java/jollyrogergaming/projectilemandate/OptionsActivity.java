package jollyrogergaming.projectilemandate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

public class OptionsActivity extends AppCompatActivity {
    private Button mLightButton;
    private Button mDarkButton;
    private Button mEasyButton;
    private Button mHardButton;
    private Button mClearDatabaseButton;
    private RadioGroup mColorSchemeGroup;
    private RadioGroup mDifficultyGroup;
    private SQLiteDatabase mDatabase;
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        mDatabase = new ScoreBaseHelper(this).getWritableDatabase();
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);

        mColorSchemeGroup = (RadioGroup) findViewById(R.id.color_scheme_group);
        mDifficultyGroup = (RadioGroup) findViewById(R.id.difficulty_group);

        mLightButton = (Button) findViewById(R.id.light_button);
        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorScheme = false;
                storeResult();
            }
        });

        mDarkButton = (Button) findViewById(R.id.dark_button);
        mDarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorScheme = true;
                storeResult();
            }
        });

        mEasyButton = (Button) findViewById(R.id.easy_button);
        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsGameHard = false;
                storeResult();
            }
        });

        mHardButton = (Button) findViewById(R.id.hard_button);
        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsGameHard = true;
                storeResult();
            }
        });

        mClearDatabaseButton = (Button) findViewById(R.id.clear_database_button);
        mClearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.delete(ScoreDbSchema.ScoreTable.NAME, null, null);
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

        if( savedInstanceState != null){
            mColorScheme = savedInstanceState.getBoolean(KEY_COLOR_SCHEMA);
            mIsGameHard = savedInstanceState.getBoolean(KEY_IS_GAME_HARD);

            storeResult();
        }
    }

    public void storeResult(){
        Intent data = getIntent();
        data.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
        data.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
        setResult(RESULT_OK, data);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_COLOR_SCHEMA, mColorScheme);
        savedInstanceState.putBoolean(KEY_IS_GAME_HARD, mIsGameHard);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mDatabase.close();
    }
}
