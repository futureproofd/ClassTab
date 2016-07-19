package to.marcus.classtab.ui.control;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.data.model.Photos;

/**
 * Created by marcus on 7/4/2016
 */
public class HomePresenterImpl extends BasePresenter<MainView> {
    private final String TAG = HomePresenterImpl.class.getSimpleName();
    private final DataManager mDataManager;

    @Inject public HomePresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void loadArtists(){
        mDataManager.getArtists()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<JSONArray>(){
                @Override
                public void call(JSONArray artists){
                    getView().showArtists(presentArtists(artists));
                }
            });
    }

    public void loadArtistsWithPhotos(){
        mDataManager.getArtistsWithPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>() {
                    @Override
                    public void call(JSONArray artists) {
                        getView().showArtists(presentArtists(artists));
                    }
                });
    }

    public void loadPhotos(){
        mDataManager.getPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>() {
                    @Override
                    public void call(JSONArray photos) {
                        Log.i(TAG, "test");
                    }
                });
    }

    /**
     * Gets a JSON list of artists, creates an observable LinkedHashmap
     * uses map to iterate over artists, extract name, flatmap each individual name
     * to google API. Return combined resultSet to dataManager for saving
     */
    public void downloadPhotos(){
        mDataManager.getArtists()
            .subscribeOn(Schedulers.io())
            .subscribe(new Action1<JSONArray>() {
                @Override
                public void call(JSONArray jsonArray) {
                    LinkedHashMap<Integer,Artist> artistMap = presentArtists(jsonArray);
                    Collection<Artist> artists = artistMap.values();
                    Observable.from(artists)
                       .map(new Func1<Artist, HashMap<String,String>>() {
                           @Override
                           public HashMap<String,String> call(Artist artist) {
                               HashMap<String,String> artistRecord = new HashMap<>();
                               //artistRecord.put(artist.getId(),URLEncoder.encode(artist.getName()));
                               String artistName;
                               if(artist.getName().contains("(")){
                                   artistName = artist.getName().substring(0,artist.getName().indexOf("(")-2).replace(" ","+");
                               }else {
                                   artistName = artist.getName().replace(" ", "+");
                               }
                               artistRecord.put(artist.getId(),artistName+"+composer");
                               return artistRecord;
                           }
                       })
                        .flatMap(new Func1<HashMap<String,String>, Observable<Photos>>() {
                            @Override
                            public Observable<Photos> call(HashMap<String,String> artistRecord) {
                               return mDataManager.getPhotosObservable(artistRecord);
                            }
                        })
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<List<Photos>>() {
                            @Override
                            public void onCompleted() {}

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<Photos> objects) {
                                mDataManager.savePhotoURL(objects);
                            }
                        });
                }
            });
    }

    private LinkedHashMap<Integer,Artist> presentArtists(JSONArray artists){
        LinkedHashMap<Integer,Artist> mArtists = new LinkedHashMap<>();
        for(int i = 0; i < artists.length(); i++){
            try{
                mArtists.put(i,new Artist(artists.getJSONObject(i)));
            }catch (JSONException e){
                Log.e(TAG,"Oops: "+e);
            }
        }
        return mArtists;
    }

    @Override
    public void attachView(MainView mainView){
        super.attachView(mainView);
    }

    @Override
    public void detachView(){
        super.detachView();
        //do other stuff
    }

}
