package jollyrogergaming.projectilemandate;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance(mColorScheme, mIsGameHard);
    }

    /**
     * Set the current theme depending on the options that the user has currently chosen
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        //setTheme(android.R.style.Theme_Black);
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);
        if( mColorScheme ) {
            setTheme(android.R.style.Theme_Holo_NoActionBar);
        }
        else{
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        }
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
    }

}
