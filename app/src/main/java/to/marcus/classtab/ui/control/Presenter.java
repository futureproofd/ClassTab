package to.marcus.classtab.ui.control;

import to.marcus.classtab.ui.control.base.BaseView;

/**
 * Created by mplienegger on 7/4/2016.
 */
public interface Presenter<V extends BaseView> {
    void attachView(V BaseView);
    void detachView();
    boolean isViewAttached();
}
