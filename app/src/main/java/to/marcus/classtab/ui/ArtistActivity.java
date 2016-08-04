package to.marcus.classtab.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import java.util.LinkedHashMap;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.model.Artist;

import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.ArtistPresenterImpl;
import to.marcus.classtab.ui.control.MainView;

public class ArtistActivity extends BaseActivity implements MainView, RecyclerViewArtistClickListener{
    private static final String TAG = ArtistActivity.class.getSimpleName();
    private ArtistsAdapter mArtistsAdapter;
    private LinearLayoutManager mLayoutManager;

    @Inject
    ArtistPresenterImpl mArtistPresenterImpl;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mArtistPresenterImpl.attachView(this);
        initRecyclerAdapter();
        mArtistPresenterImpl.loadArtistsWithPhotos();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mArtistPresenterImpl.detachView();
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

    private void initRecyclerAdapter(){
        if(mArtistsAdapter == null){
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mArtistsAdapter = new ArtistsAdapter(this);
            mRecyclerView.setAdapter(mArtistsAdapter);
        }
    }

    /**
     * MVP View Implementation(s)
     */
    @Override
    public void showArtists(LinkedHashMap<Integer, Artist> artists){
        mArtistsAdapter.setArtists(artists);
        mArtistsAdapter.notifyDataSetChanged();
    }

    //ArtistsAdapter clickListener
    @Override
    public void onObjectClick(View v, String artistId) {
        mArtistPresenterImpl.updateArtist(ClassTabDB.ArtistTable.COLUMN_ACCESSTIME,System.currentTimeMillis()/1000L,artistId);
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("ARTISTID",artistId);
        this.startActivity(intent);
    }
}
