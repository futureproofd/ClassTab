package to.marcus.classtab.injection.component;

import android.app.Application;

import dagger.Component;
import to.marcus.classtab.SQLiteDataHelper;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.injection.module.ApplicationModule;

/**
 * Created by marcus on 25/06/16
 */

@Component(modules = ApplicationModule.class)
public interface ApplicationComponenet {

    Application application();
    DataManager dataManager();
    SQLiteDataHelper databaseHelper();

}
