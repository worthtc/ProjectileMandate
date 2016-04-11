package jollyrogergaming.projectilemandate;


import android.support.v4.app.Fragment;

/**
 * Created by Trevor on 4/10/2016.
 */

/**
 * Simple activity class that creates a new LeaderBoardFragment
 */
public class LeaderBoardActivity extends SingleFragmentActivity {
    /*@Override
    protected int getLayoutResId(){
        return R.layout.fragment_leader_board;
    }*/
    @Override
    protected Fragment createFragment(){
        return new LeaderBoardFragment();
    }
}
