package to.marcus.classtab.ui.control;

import org.json.JSONArray;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import to.marcus.classtab.data.DataManager;

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

    public void loadTabs(){
        mDataManager.getTabs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONArray>(){
                    @Override
                    public void call(JSONArray tabs) {
                        getView().showTabs(tabs);
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

}
