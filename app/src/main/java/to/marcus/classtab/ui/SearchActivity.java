package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.data.model.Tab;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.DetailPresenterImpl;
import to.marcus.classtab.ui.control.DetailView;

/**
 * Created by mplienegger on 8/18/2016
 */
public class SearchActivity extends AppCompatActivity implements DetailView, RecyclerViewTabClickListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private TabAdapter mTabAdapter;
    private LinearLayoutManager mLayoutManager;

    @Inject DetailPresenterImpl mDetailPresenterImpl;
    @BindView(R.id.search_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.search_view) SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initSearchView();
        initRecyclerAdapter();
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
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initSearchView(){
        mSearchView.setQueryHint(getString(R.string.search_view_hint));
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //first clear results
                return true;
            }
        });
    }

    private void initRecyclerAdapter(){
        if(mTabAdapter == null){
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mTabAdapter = new TabAdapter(this);
            mRecyclerView.setAdapter(mTabAdapter);
        }
    }

    private void clearResults(){
        //clear adapter
        //clear subscription
        //clear results view
        //clear progress view
        //clear no results view
    }

    private void search(String query){
        //clearResults
        //set progress visibility
        mSearchView.clearFocus();
        mDetailPresenterImpl.loadSearchResults(query);
    }

    @Override
    public void onObjectClick(View v, byte[] tab) {
        Log.d(TAG, "onObjectClick: clicked");
    }

    @Override
    public void showTabs(LinkedHashMap<Integer, Tab> tabs) {
        Log.d(TAG, "showTabs: tabs");
        if(tabs.size() > 0){
            mTabAdapter.setTabs(tabs);
            mTabAdapter.notifyDataSetChanged();
        }
    }
}
