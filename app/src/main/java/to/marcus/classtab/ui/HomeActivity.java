package to.marcus.classtab.ui;

import android.os.Bundle;

import javax.inject.Inject;

import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.HomePresenterImpl;
import to.marcus.classtab.ui.control.MainView;

public class HomeActivity extends BaseActivity implements MainView {

    @Inject
    HomePresenterImpl mHomePresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_home);
        mHomePresenterImpl.attachView(this);
        mHomePresenterImpl.doSomething();
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

    /*
    MVP View implementation
    */
    @Override
    public void getArtists() {

    }
}
