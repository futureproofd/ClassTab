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
public class HomePresenterImpl extends BasePresenter<MainView> {
    private final String TAG = HomePresenterImpl.class.getSimpleName();
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject public HomePresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void loadArtistsWithPhotos(){
        mSubscription = mDataManager.getArtistsWithPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>() {
                    @Override
                    public void call(JSONArray artists) {
                        getView().showArtists(presentArtists(artists));
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
        if(mSubscription != null) mSubscription.unsubscribe();
    }

}
