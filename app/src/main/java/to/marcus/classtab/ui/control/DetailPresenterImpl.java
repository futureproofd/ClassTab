package to.marcus.classtab.ui.control;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedHashMap;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.data.model.Tab;

/**
 * Created by mplienegger on 7/19/2016.
 */
public class DetailPresenterImpl extends BasePresenter<DetailView> {
    private final String TAG = DetailPresenterImpl.class.getSimpleName();
    private final DataManager mDataManager;

    @Inject
    public DetailPresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void loadTabsByArtist(String artistId){
        mDataManager.getTabsByArtist(artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>(){
                    @Override
                    public void call(JSONArray tabs) {
                        getView().showTabs(presentTabs(tabs));
                    }
                });
    }

    @Override
    public void attachView(DetailView detailView){
        super.attachView(detailView);
    }

    @Override
    public void detachView(){
        super.detachView();
    }

    private LinkedHashMap<Integer,Tab> presentTabs(JSONArray tabs){
        LinkedHashMap<Integer,Tab> mTabs = new LinkedHashMap<>();
        for(int i = 0; i < tabs.length(); i++){
            try{
                mTabs.put(i,new Tab(tabs.getJSONObject(i)));
            }catch (JSONException e){
                Log.e(TAG,"Oops: "+e);
            }
        }
        return mTabs;
    }

}
