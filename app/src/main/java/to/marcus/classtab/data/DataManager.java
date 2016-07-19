package to.marcus.classtab.data;

import android.util.Log;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import to.marcus.classtab.data.local.ArtistRepositoryHelperImpl;
import to.marcus.classtab.data.local.PhotoRepositoryHelperImpl;
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllArtistsByIndexAQuery;
import to.marcus.classtab.data.local.contract.query.AllArtistsWithPhotosQuery;
import to.marcus.classtab.data.local.contract.query.AllPhotosQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsByIndexAQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsQuery;
import to.marcus.classtab.data.model.Photos;
import to.marcus.classtab.data.remote.GoogleImageAPI;


/**
 * Created by marcus on 25/06/16
 * Manages data sources (Local and Remote) asynchronously
 */
public class DataManager {
    private TabRepositoryHelperImpl tabRepositoryHelper;
    private ArtistRepositoryHelperImpl artistRepositoryHelper;
    private PhotoRepositoryHelperImpl photoRepositoryHelper;

    @Inject
    public DataManager(TabRepositoryHelperImpl tabRepositoryHelper
            ,ArtistRepositoryHelperImpl artistRepositoryHelper
            ,PhotoRepositoryHelperImpl photoRepositoryHelper){
        this.tabRepositoryHelper = tabRepositoryHelper;
        this.artistRepositoryHelper = artistRepositoryHelper;
        this.photoRepositoryHelper = photoRepositoryHelper;
    }

    public Observable<JSONArray> getTabs(){
        return makeObservable(tabRepositoryHelper.query(new AllTabsByIndexAQuery()));
    }

    public Observable<JSONArray> getArtists(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsByIndexAQuery()));
    }

    public Observable<JSONArray> getArtistsWithPhotos(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsWithPhotosQuery()));
    }

    public Observable<JSONArray> getPhotos(){
        return makeObservable(photoRepositoryHelper.query(new AllPhotosQuery()));
    }

    /**
     * Creates an observable from a RepositoryHelper SQL Query
     * @param func takes a SQL query callable from a RepositoryHelper implementation
     * @param <T> HashMap<K,V>
     * @return an Observable
     */
    private static <T> Observable<T> makeObservable(final Callable<T> func){
        return Observable.create(
            new Observable.OnSubscribe<T>() {
                @Override
                public void call(Subscriber<? super T> subscriber) {
                    try{
                       subscriber.onNext(func.call());
                    }catch (Exception e){
                        Log.e("TEST", "Error getting database records: ",e);
                    }
                }
            }
        );
    }

    /*
    Iterates and creates new API call per record, resulting in Photos object
     */
    public Observable<Photos> getPhotosObservable(HashMap<String,String> artistRecord){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.googleapis.com/customsearch/")
                .build();
        GoogleImageAPI googleImageAPI = retrofit.create(GoogleImageAPI.class);
        Iterator it = artistRecord.entrySet().iterator();
        String artistName = "";
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            artistName = (String)pair.getValue();
            // TODO: 7/18/2016   make async
            photoRepositoryHelper.populatePhotoNameAndId(artistRecord);

        }
        return googleImageAPI.loadPhotos(artistName);
    }

    /*
    Receives result of Rx Subscription, combined List
    Updates Photo table with result Ids corresponding to Artist table
     */
    public void savePhotoURL(List<Photos> data) {
        //// TODO: 7/18/2016 make async 
        photoRepositoryHelper.populatePhotoURL(data);
    }

}
