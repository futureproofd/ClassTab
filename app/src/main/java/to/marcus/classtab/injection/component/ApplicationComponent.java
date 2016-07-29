package to.marcus.classtab.injection.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import to.marcus.classtab.injection.module.ApplicationModule;

/**
 * Created by marcus on 25/06/16
 */

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    Application application();
}
