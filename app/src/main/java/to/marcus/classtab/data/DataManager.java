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
import to.marcus.classtab.WebParser;
import to.marcus.classtab.data.local.ArtistRepositoryHelperImpl;
import to.marcus.classtab.data.local.PhotoRepositoryHelperImpl;
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllArtistsByLetterIndexQuery;
import to.marcus.classtab.data.local.contract.query.AllArtistsWithPhotosQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsByArtistIdQuery;
import to.marcus.classtab.data.local.contract.update.ArtistRecordUpdate;
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
    private WebParser mWebParser;

    @Inject
    public DataManager(TabRepositoryHelperImpl tabRepositoryHelper
            ,ArtistRepositoryHelperImpl artistRepositoryHelper
            ,PhotoRepositoryHelperImpl photoRepositoryHelper
            ,WebParser webParser){
        this.tabRepositoryHelper = tabRepositoryHelper;
        this.artistRepositoryHelper = artistRepositoryHelper;
        this.photoRepositoryHelper = photoRepositoryHelper;
        this.mWebParser = webParser;
    }

    public Observable<JSONArray> getTabsByArtist(String artistId){
        return makeObservable(tabRepositoryHelper.query(new AllTabsByArtistIdQuery(),artistId));
    }

    public Observable<JSONArray> getArtists(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsByLetterIndexQuery(),null));
    }

    public Observable<JSONArray> getArtistsWithPhotos(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsWithPhotosQuery(),null));
    }

    public Observable<Boolean> populateArtistsTable(){
        return makeObservable(artistRepositoryHelper.populateArtists(mWebParser.getArtists()));
    }

    public Observable<Boolean> populateArtistDates(){
        return makeObservable(artistRepositoryHelper.populateArtistsDates(mWebParser.getArtistDates()));
    }

    public Observable<Boolean> populateTabTable(){
        return makeObservable(tabRepositoryHelper.populateTabs(mWebParser.getTabs()));
    }

    public Observable<Boolean> populateTabTitles(){
        return makeObservable(tabRepositoryHelper.populateTabTitles(mWebParser.getTabTitles()));
    }

    public <T> Observable<Boolean> updateArtistRecord(String field, T value, String id){
        Log.i("DATAMANAGER", "updating access time");
        return makeObservable(artistRepositoryHelper.update(field,new ArtistRecordUpdate(),value,id));
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
