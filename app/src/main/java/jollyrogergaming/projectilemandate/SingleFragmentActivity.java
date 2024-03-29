package jollyrogergaming.projectilemandate;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tworth on 4/10/2016.
 */

/**
 * Abstract class for storeing a single fragment
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    /**
     *
     * @return
     */
    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);


        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
