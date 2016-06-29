package to.marcus.classtab;

import android.app.Application;
import android.content.Context;

import to.marcus.classtab.injection.component.ApplicationComponent;
import to.marcus.classtab.injection.component.DaggerApplicationComponent;
import to.marcus.classtab.injection.module.ApplicationModule;

/**
 * Created by marcus on 6/29/2016
 */
public class ClassTabApplication extends Application {

    ApplicationComponent mApplicationComponent;

    public static ClassTabApplication get(Context context){
        return (ClassTabApplication)context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent(){
        if(mApplicationComponent == null){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
