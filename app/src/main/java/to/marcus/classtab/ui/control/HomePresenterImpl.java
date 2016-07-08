package to.marcus.classtab.ui.control;

import java.util.HashMap;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;

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
                .subscribe(new Action1<HashMap<String, String>>() {
                    @Override
                    public void call(HashMap<String, String> artists) {
                        getView().showArtists(artists);
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
}
