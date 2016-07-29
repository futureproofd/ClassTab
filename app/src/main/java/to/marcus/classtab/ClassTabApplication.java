package to.marcus.classtab;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import to.marcus.classtab.injection.component.ApplicationComponent;
import to.marcus.classtab.injection.component.DaggerApplicationComponent;
import to.marcus.classtab.injection.component.DaggerPresenterComponent;
import to.marcus.classtab.injection.module.ApplicationModule;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.control.ApplicationPresenterImpl;

/**
 * Created by marcus on 6/29/2016
 */
public class ClassTabApplication extends Application {
    private static ApplicationComponent sApplicationComponent;

    @Inject
    ApplicationPresenterImpl mApplicationPresenterImpl;

    @Override
    public void onCreate(){
        super.onCreate();
        if(sApplicationComponent == null){
            sApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        //// TODO: 7/8/2016 initiate bootstrap DB build process here - reuse version check code
        getPresenter();
        Stetho.initializeWithDefaults(this);
        //// TODO: 7/27/2016 make sure these run synchronously
     // mApplicationPresenterImpl.populateArtistTable();
     // mApplicationPresenterImpl.populateArtistDates();
      //mApplicationPresenterImpl.populateTabTable();
        //// TODO: 7/27/2016 issue :  java.lang.IllegalStateException: attempt to re-open an already-closed object: SQLiteDatabase: /data/user/0/to.marcus.classtab/databases/classTab.db
    //  mApplicationPresenterImpl.populateTabTitles();
        //// TODO: 7/28/2016  downloads photos but doesn't finish on time for recycler adapter
        //mApplicationPresenterImpl.downloadPhotos();

    }

    public static ClassTabApplication get(Context context){
        return (ClassTabApplication)context.getApplicationContext();
    }

    public static ApplicationComponent getApplicationComponent(){
        return sApplicationComponent;
    }

    protected ApplicationPresenterImpl getPresenter(){
        if(mApplicationPresenterImpl == null){
            DaggerPresenterComponent.builder()
                    .applicationComponent(ClassTabApplication.getApplicationComponent())
                    .presenterModule(new PresenterModule())
                    .build().inject(this);
        }
        return mApplicationPresenterImpl;
    }


}
