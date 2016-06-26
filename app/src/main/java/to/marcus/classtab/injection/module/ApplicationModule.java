package to.marcus.classtab.injection.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marcus on 25/06/16
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application){
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    Context provideContext(){
        return mApplication;
    }

}
