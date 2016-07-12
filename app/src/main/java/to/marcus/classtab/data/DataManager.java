package to.marcus.classtab.data;

import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import to.marcus.classtab.data.local.ArtistRepositoryHelperImpl;
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllArtistsQuery;
import to.marcus.classtab.data.local.contract.query.AllTabsQuery;


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

    public Observable<LinkedHashMap<String,String>> getArtists(){
        return makeObservable(artistRepositoryHelper.query(new AllArtistsQuery()));
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
}
