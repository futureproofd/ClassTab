package to.marcus.classtab.injection.component;

import dagger.Component;
import to.marcus.classtab.injection.ActivityScope;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.HomeActivity;

/**
 * Created by marcus on 7/5/2016
 */
@Component(modules = {PresenterModule.class}, dependencies = ApplicationComponent.class)
@ActivityScope
public interface PresenterComponent {

    void inject(HomeActivity activity);
}
