package jollyrogergaming.projectilemandate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends SingleFragmentActivity implements RestartGameFragment.Restartable{
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";

    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;



    @Override
    protected Fragment createFragment(){
        //Hide the action bar since this is the game window
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        return GameFragment.newInstance(mColorScheme, mIsGameHard);
    }

    /**
     * Restart the activity in case the user wants to replay the game
     */
    @Override
    public void restart(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;//= fm.findFragmentById(R.id.fragment_container);

        fragment = createFragment();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    /**
     * Create the activity, setting the appropriate theme depending on how the user set the options
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        mColorScheme = getIntent().getBooleanExtra(EXTRA_COLOR_SCHEME, false);
        mIsGameHard = getIntent().getBooleanExtra(EXTRA_IS_GAME_HARD, false);
        if( mColorScheme ){
            setTheme(android.R.style.Theme_Holo_NoActionBar_Fullscreen);
        }
        else{
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
        }
        super.onCreate(savedInstanceState);

    }
}
