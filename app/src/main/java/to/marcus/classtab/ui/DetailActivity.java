package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;

import javax.inject.Inject;

import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.DetailPresenterImpl;
import to.marcus.classtab.ui.control.DetailView;

/**
 * Created by mplienegger on 7/19/2016
 */
public class DetailActivity extends AppCompatActivity implements DetailView {

    @Inject DetailPresenterImpl mDetailPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_detail);
        //ButterKnife.bind(this);
        mDetailPresenterImpl.attachView(this);
        mDetailPresenterImpl.loadTabs();
    }

    protected DetailPresenterImpl getPresenter(){
        if(mDetailPresenterImpl == null){
            DaggerPresenterComponent.builder()
                    .applicationComponent(ClassTabApplication.getApplicationComponent())
                    .presenterModule(new PresenterModule())
                    .build().inject(this);
            mDetailPresenterImpl.attachView(this);
        }
        return mDetailPresenterImpl;
    }

    @Override
    public void showTabs(JSONArray tabs) {

    }
}
