package to.marcus.classtab.injection.component;

import dagger.Component;
import to.marcus.classtab.ClassTabApplication;
import to.marcus.classtab.injection.ActivityScope;
import to.marcus.classtab.injection.module.PresenterModule;
import to.marcus.classtab.ui.ArtistHomeActivity;
import to.marcus.classtab.ui.DetailActivity;
import to.marcus.classtab.ui.SearchActivity;

/**
 * Created by marcus on 7/5/2016
 */
@Component(modules = {PresenterModule.class}, dependencies = ApplicationComponent.class)
@ActivityScope
public interface PresenterComponent {
    void inject(ArtistHomeActivity activity);
    void inject(DetailActivity activity);
    void inject(SearchActivity activity);
    void inject(ClassTabApplication application);
}
