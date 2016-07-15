package to.marcus.classtab.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.R;
import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.data.model.Photo;
import to.marcus.classtab.data.model.Photos;
import to.marcus.classtab.data.remote.GoogleImageAPI;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.HomePresenterImpl;
import to.marcus.classtab.ui.control.MainView;

public class HomeActivity extends BaseActivity implements MainView{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ArtistsAdapter mArtistsAdapter;
    private LinearLayoutManager mLayoutManager;

    @Inject HomePresenterImpl mHomePresenterImpl;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomePresenterImpl.attachView(this);
       // mHomePresenterImpl.loadTabs();
        mHomePresenterImpl.loadArtists();
        doRetroFitStuff();
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

    private void initRecyclerAdapter(){
        if(mArtistsAdapter == null){
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mArtistsAdapter = new ArtistsAdapter();
            mRecyclerView.setAdapter(mArtistsAdapter);
        }
    }

    private void doRetroFitStuff(){
        /*
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/customsearch/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GoogleImageAPI googleImageAPI = retrofit.create(GoogleImageAPI.class);

        Call<Photos> call = googleImageAPI.loadPhotos("sergio+assad");
        call.enqueue(this);
        */
        mHomePresenterImpl.getPhotos();
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
    public void showArtists(LinkedHashMap<Integer, Artist> artists){
        initRecyclerAdapter();
        mArtistsAdapter.setArtists(artists);
        mArtistsAdapter.notifyDataSetChanged();
    }

}
