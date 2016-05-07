package jollyrogergaming.projectilemandate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Simple activity class that creates a new LeaderBoardFragment
 */
public class LeaderBoardActivity extends SingleFragmentActivity {

    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";


    @Override
    protected Fragment createFragment(){
        return LeaderBoardFragment.newInstance(mColorScheme, mIsGameHard);
    }

    /**
     * Override onCreate so that we set the correct theme for the options that the user has chosen
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);
        if( mColorScheme ){
            setTheme(android.R.style.Theme_Holo_NoActionBar);
        }
        else{
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        }
        super.onCreate(savedInstanceState);

    }
}
