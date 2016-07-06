package to.marcus.classtab.ui.control;

import android.provider.ContactsContract;
import android.util.Log;

import javax.inject.Inject;

import to.marcus.classtab.data.DataManager;

/**
 * Created by marcus on 7/4/2016
 */
public class HomePresenterImpl extends BasePresenter<MainView> {
    private final DataManager mDataManager;

    @Inject public HomePresenterImpl(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    public void doSomething(){
        Log.i("PRESENTER","Did something");
        mDataManager.doSomething();
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
