package to.marcus.classtab.ui;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.HomePresenterImpl;
import to.marcus.classtab.ui.control.MainView;

public class HomeActivity extends BaseActivity implements MainView {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Inject
    HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_home);
        mHomePresenterImpl.attachView(this);
        mHomePresenterImpl.loadTabs();
        mHomePresenterImpl.loadArtists();
    }

    protected HomePresenterImpl getPresenter(){
        if(mHomePresenterImpl == null){
            DaggerPresenterComponent.builder()
                    .applicationComponent(ClassTabApplication.getApplicationComponent())
                    .presenterModule(new PresenterModule())
                    .build().inject(this);
            mHomePresenterImpl.attachView(this);
        }
        return mHomePresenterImpl;
    }

    /**
     * MVP View Implementation(s)
     */
    @Override
    public void showTabs(HashMap<String,byte[]> tabs){
        //// TODO: 7/8/2016 recycler adapter receives tabs
         Log.i(TAG, "size:"+tabs.size());
    }

    @Override
    public void showArtists(HashMap<String, String> artists){
        //// TODO: 7/8/2016 recycler adapter receives artists
        Log.i(TAG, "artists:"+artists.size());
    }
}
