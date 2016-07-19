package to.marcus.classtab.injection.module;

import dagger.Module;
import dagger.Provides;
import to.marcus.classtab.data.DataManager;
import to.marcus.classtab.ui.control.BasePresenter;
import to.marcus.classtab.ui.control.DetailPresenterImpl;
import to.marcus.classtab.ui.control.HomePresenterImpl;

/**
 * Created by marcus on 7/5/2016
 */
@Module
public class PresenterModule {

    @Provides
    BasePresenter providesHomeActivityPresenter(DataManager dataManager){
        return new HomePresenterImpl(dataManager);
    }

    @Provides
    BasePresenter provideDetailActivityPresenter(DataManager dataManager){
        return new DetailPresenterImpl(dataManager);
    }
}
