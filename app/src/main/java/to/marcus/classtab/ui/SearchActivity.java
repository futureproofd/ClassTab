package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.ArtistPresenterImpl;
import to.marcus.classtab.ui.control.MainView;

/**
 * Created by mplienegger on 8/18/2016
 */
public class SearchActivity extends BaseActivity implements MainView {

    @Inject
    ArtistPresenterImpl mArtistPresenterImpl;
    @BindView(R.id.search_recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mArtistPresenterImpl.doSomething();
    }

    protected ArtistPresenterImpl getPresenter(){
        if(mArtistPresenterImpl == null){
            DaggerPresenterComponent.builder()
                    .applicationComponent(ClassTabApplication.getApplicationComponent())
                    .presenterModule(new PresenterModule())
                    .build().inject(this);
            mArtistPresenterImpl.attachView(this);
        }
        return mArtistPresenterImpl;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initSearchView(){

    }

    @Override
    public void showArtists(LinkedHashMap<Integer, Artist> artists) {

    }
}
