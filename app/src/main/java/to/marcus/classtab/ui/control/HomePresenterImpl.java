package to.marcus.classtab.ui.control;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.data.model.Artist;

/**
 * Created by marcus on 7/4/2016
 */
public class HomePresenterImpl extends BasePresenter<MainView> {
    private final DataManager mDataManager;

    @Inject public HomePresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void loadTabs(){
        mDataManager.getTabs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<HashMap<String, byte[]>>(){
                @Override
                public void call(HashMap<String, byte[]> tabs) {
                    getView().showTabs(tabs);
                }
            });
    }

    public void loadArtists(){
        mDataManager.getArtists()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<LinkedHashMap<String, String>>(){
                @Override
                public void call(LinkedHashMap<String, String> artists){
                    getView().showArtists(presentArtists(artists));
                }
            });
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

    private LinkedHashMap<Integer,Artist> presentArtists(LinkedHashMap<String,String> artists){
        LinkedHashMap<Integer,Artist> mArtists = new LinkedHashMap<>();
        Integer key = 0;
        for(Map.Entry<String,String> entry : artists.entrySet()){
            Artist artistObj = new Artist(entry.getKey(),entry.getValue());
            mArtists.put(key,artistObj);
            key++;
        }
        return mArtists;
    }
}
