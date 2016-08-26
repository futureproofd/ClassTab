package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;

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
public class SearchActivity extends AppCompatActivity implements MainView {
    private static final String TAG = SearchActivity.class.getSimpleName();

    @Inject ArtistPresenterImpl mArtistPresenterImpl;
    @BindView(R.id.search_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.search_view) SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initSearchView();
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
        mSearchView.setQueryHint(getString(R.string.search_view_hint));
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                mArtistPresenterImpl.doSomething();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //first clear results
                return true;
            }
        });
    }

    private void clearResults(){
        //clear adapter
        //clear subscription
        //clear results view
        //clear progress view
        //clear no results view
    }

    @Override
    public void showArtists(LinkedHashMap<Integer, Artist> artists) {

    }
}
