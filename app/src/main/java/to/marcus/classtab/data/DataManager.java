package to.marcus.classtab.data;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import javax.inject.Inject;

import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.data.local.TabRepositoryHelperImpl;
import to.marcus.classtab.data.local.contract.query.AllTabsQuery;


/**
 * Created by marcus on 25/06/16
 * Manages data sources (Local and Remote) asynchronously
 */
public class DataManager {
    private final Context mContext;
    private TabRepositoryHelperImpl tabRepositoryHelper;

    @Inject
    public DataManager(Application application, TabRepositoryHelperImpl tabRepositoryHelper){
        this.mContext = application;
        this.tabRepositoryHelper = tabRepositoryHelper;
    }

    public void doSomething(){
        //TabRepositoryHelperImpl tabRepositoryHelper = new TabRepositoryHelperImpl(mContext);
        HashMap<String,byte[]> tabs = new HashMap<>();
        tabs = tabRepositoryHelper.query(new AllTabsQuery());
        Log.i("DATAMANAGER", "Did something");
    }
}
