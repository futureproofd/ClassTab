package to.marcus.classtab.injection.component;

import dagger.Component;
import to.marcus.classtab.injection.module.ActivityModule;
import to.marcus.classtab.ui.HomeActivity;

/**
 * Created by marcus on 25/06/16
 * Inject dependencies to all activities specified
 */

@Component(dependencies = ApplicationComponenet.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity activity);

}
