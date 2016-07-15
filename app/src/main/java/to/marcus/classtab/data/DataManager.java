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
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllArtistsByIndexAQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsQuery;
import to.marcus.classtab.data.model.Photo;
import to.marcus.classtab.data.model.Photos;
import to.marcus.classtab.data.remote.GoogleImageAPI;


/**
 * Created by marcus on 25/06/16
 * Manages data sources (Local and Remote) asynchronously
 */
public class DataManager {
    private TabRepositoryHelperImpl tabRepositoryHelper;
    private ArtistRepositoryHelperImpl artistRepositoryHelper;

    @Inject
    public DataManager(TabRepositoryHelperImpl tabRepositoryHelper
            ,ArtistRepositoryHelperImpl artistRepositoryHelper){
        this.tabRepositoryHelper = tabRepositoryHelper;
        this.artistRepositoryHelper = artistRepositoryHelper;
    }

    public Observable<HashMap<String,byte[]>> getTabs(){
        return makeObservable(tabRepositoryHelper.query(new AllTabsQuery()));
    }

    public Observable<JSONArray> getArtists(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsByIndexAQuery()));
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
            //// TODO: 7/14/2016 update Photos DB with artist Id and URL encoded name
        }
        return googleImageAPI.loadPhotos(artistName);
    }

    /*
    Receives result of Rx Subscription one result at a time
    Updates Photo table with result Id corresponding to Artist table
     */
    public void savePhotos(List<Photos> data){
        for(Photos p : data){
            List<Photo> photos = p.getItems();
            //// TODO: 7/14/2016 update url property of photos DB for last item in list (position(photos.size-1)) Where url encoded name = phototabe url encoded name
            //make observable from callable function
        }
    }

}
