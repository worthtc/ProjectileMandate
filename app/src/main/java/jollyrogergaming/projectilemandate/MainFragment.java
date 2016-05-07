package jollyrogergaming.projectilemandate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Trevor on 4/10/2016.
 */
public class MainFragment extends Fragment {
    /**
     * varibles used through out the Mainfragment
     */
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
    public static final String TAG = "MainFragment";

    private static final int REQUEST_CODE_OPTIONS = 0;

    public static MainFragment newInstance(boolean colorScheme, boolean isGameHard){
        Bundle args = new Bundle();
        args.putBoolean(KEY_COLOR_SCHEMA, colorScheme);
        args.putBoolean(KEY_IS_GAME_HARD, isGameHard);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create onClickListeners for all of the activities in this layout.
     * @param savedInstanceState
     */
    @Override
    //protected void onCreate(Bundle savedInstanceState) {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
        mColorScheme = getArguments().getBoolean(KEY_COLOR_SCHEMA);
        mIsGameHard = getArguments().getBoolean(KEY_IS_GAME_HARD);

        View view = inflater.inflate(R.layout.activity_main, container, false);

        mPlayButton = (Button) view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GameActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivity(i);
            }
        });

        mOptionsButton = (Button) view.findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OptionsActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivityForResult(i, REQUEST_CODE_OPTIONS);
            }
        });

        mLeaderBoardButton = (Button) view.findViewById(R.id.leader_board_button);
        mLeaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LeaderBoardActivity.class);
                i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
                i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
                startActivity(i);
            }
        });

        mQuitButton = (Button) view.findViewById(R.id.quit_button);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        if( savedInstanceState != null){
            mColorScheme = savedInstanceState.getBoolean(KEY_COLOR_SCHEMA);
            mIsGameHard = savedInstanceState.getBoolean(KEY_IS_GAME_HARD);
        }

        return view;
    }

    /**
     * Save the current instance state in case the user rotates the screen
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_COLOR_SCHEMA, mColorScheme);
        savedInstanceState.putBoolean(KEY_IS_GAME_HARD, mIsGameHard);
    }

    /**
     * Once we return from an activity, we figure out what activity we returned from, and if it is the options activity, we
     * save the options that the user chose.
     * @param requestCode The code corresponding to which activity we returned from
     * @param resultCode Whether we got a correct result or not
     * @param data The data that was returned
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if( resultCode != Activity.RESULT_OK){
            return;
        }
        if( requestCode == REQUEST_CODE_OPTIONS){
            mColorScheme = data.getBooleanExtra(EXTRA_COLOR_SCHEME, false);
            mIsGameHard = data.getBooleanExtra(EXTRA_IS_GAME_HARD, false);
            Intent i = getActivity().getIntent();
            i.putExtra(EXTRA_COLOR_SCHEME, mColorScheme);
            i.putExtra(EXTRA_IS_GAME_HARD, mIsGameHard);
            //Don't think that this is considered good code, but oh well
            getActivity().finish();
            startActivity(i);
        }

    }
}
