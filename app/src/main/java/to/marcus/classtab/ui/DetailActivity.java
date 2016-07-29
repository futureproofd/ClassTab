package to.marcus.classtab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
 * Created by mplienegger on 7/19/2016
 */
public class DetailActivity extends AppCompatActivity implements DetailView, RecyclerViewTabClickListener {

    @Inject DetailPresenterImpl mDetailPresenterImpl;
    @BindView(R.id.recycler_view_tabs) RecyclerView mRecyclerView;
    private TabAdapter mTabAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDetailPresenterImpl.attachView(this);
        initRecyclerAdapter();
        mDetailPresenterImpl.loadTabsByArtist(getIntent().getStringExtra("ARTISTID"));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mDetailPresenterImpl.detachView();
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

    private void initRecyclerAdapter(){
        if(mTabAdapter == null){
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mTabAdapter = new TabAdapter(this);
            mRecyclerView.setAdapter(mTabAdapter);
        }
    }

    @Override
    public void showTabs(LinkedHashMap<Integer,Tab> tabs) {
        mTabAdapter.setTabs(tabs);
        mTabAdapter.notifyDataSetChanged();
    }

    @Override
    public void onObjectClick(View v, byte[] tab) {
        Intent intent = new Intent(this,TabActivity.class);
        intent.putExtra("TABEXTRA",tab);
        this.startActivity(intent);
    }
}
