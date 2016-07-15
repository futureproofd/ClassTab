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

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        if(sApplicationComponent == null){
            sApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        //// TODO: 7/8/2016 initiate bootstrap DB build process here - reuse version check code
        //WebParser parser = new WebParser(this);
       // parser.InitDB();
    }

    public static ClassTabApplication get(Context context){
        return (ClassTabApplication)context.getApplicationContext();
    }

    public static ApplicationComponent getApplicationComponent(){
        return sApplicationComponent;
    }
}
