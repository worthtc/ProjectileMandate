package jollyrogergaming.projectilemandate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import jollyrogergaming.projectilemandate.database.ScoreBaseHelper;
import jollyrogergaming.projectilemandate.database.ScoreDbSchema;

public class OptionsActivity extends SingleFragmentActivity {

    private boolean mColorScheme; //False with a light color scheme, True with a dark color scheme
    private boolean mIsGameHard;
    public static final String EXTRA_COLOR_SCHEME = "jollyrogergaming.projectilemandate.color_scheme";
    public static final String EXTRA_IS_GAME_HARD = "jollyrogergaming.projectilemandate.is_game_easy";
    public static final String KEY_COLOR_SCHEMA = "color_scheme";
    public static final String KEY_IS_GAME_HARD = "is_game_easy";

    @Override
    protected Fragment createFragment(){
        return OptionsFragment.newInstance(mColorScheme, mIsGameHard);
    }


    /**
     * Sets the current theme depending on what options the user has currently chosen
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
