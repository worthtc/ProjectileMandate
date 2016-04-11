package jollyrogergaming.projectilemandate;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends SingleFragmentActivity {
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
    protected Fragment createFragment(){
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);
        return MainFragment.newInstance(false, false);
    }


}
