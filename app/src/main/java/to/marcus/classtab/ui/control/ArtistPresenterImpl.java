package to.marcus.classtab.ui.control;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.LinkedHashMap;
import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.ui.control.base.BasePresenter;

/**
 * Created by marcus on 7/4/2016
 */
public class ArtistPresenterImpl extends BasePresenter<MainView> {
    private final String TAG = ArtistPresenterImpl.class.getSimpleName();
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject public ArtistPresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void loadArtistsWithPhotos(){
        mSubscription = mDataManager.getTopArtistsWithPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>() {
                    @Override
                    public void call(JSONArray artists) {
                        if(isViewAttached()){
                            getView().showArtists(presentArtists(artists));
                            unsubscribe();
                        }
                    }
                });
    }

    public <T> void updateArtist(String field, T value, String id){
        mSubscription = mDataManager.updateArtistRecord(field,value,id)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean succeeded) {
                        if(succeeded){
                            Log.i("APPLICATION", "success updating access time!");
                            unsubscribe();
                        }else{
                            Log.i("APPLICATION", "failure updating access time!");
                            unsubscribe();
                        }
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
    
    public void doSomething(){
        Log.d(TAG, "doSomething: worked");
    }

    @Override
    public void detachView(){
        super.detachView();
        unsubscribe();
    }

    private void unsubscribe(){
        if(mSubscription != null && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
        mSubscription = null;
    }

}
