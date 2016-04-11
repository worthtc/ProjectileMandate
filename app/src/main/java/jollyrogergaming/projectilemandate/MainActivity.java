package jollyrogergaming.projectilemandate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    private Button mPlayButton;
    private Button mOptionsButton;
    private Button mLeaderBoardButton;
    private Button mQuitButton;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";
    private static final int REQUEST_CODE_LEADERBOARD = 2;
    private static final int REQUEST_CODE_GAME = 1;
    private static final int REQUEST_CODE_OPTIONS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivityForResult(i, REQUEST_CODE_GAME);
            }
        });

        mOptionsButton = (Button) findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OptionsActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivityForResult(i, REQUEST_CODE_OPTIONS);
            }
        });

        mLeaderBoardButton = (Button) findViewById(R.id.leader_board_button);
        mLeaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaderBoardActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivityForResult(i, REQUEST_CODE_LEADERBOARD);
            }
        });

        mQuitButton = (Button) findViewById(R.id.quit_button);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if( savedInstanceState != null){
            mColorScheme = savedInstanceState.getBoolean(KEY_COLOR_SCHEMA);
            mIsGameHard = savedInstanceState.getBoolean(KEY_IS_GAME_HARD);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_COLOR_SCHEMA, mColorScheme);
        savedInstanceState.putBoolean(KEY_IS_GAME_HARD, mIsGameHard);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if( resultCode != Activity.RESULT_OK){
            return;
        }
        if( requestCode == REQUEST_CODE_OPTIONS){
            mColorScheme = data.getBooleanExtra(EXTRA_COLOR_SCHEME, false);
            mIsGameHard = data.getBooleanExtra(EXTRA_IS_GAME_HARD, false);
        }
        else if( requestCode == REQUEST_CODE_LEADERBOARD){

        }
        else if( requestCode == REQUEST_CODE_GAME){

        }

    }
}
