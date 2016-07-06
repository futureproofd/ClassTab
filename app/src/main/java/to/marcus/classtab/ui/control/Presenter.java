package to.marcus.classtab.ui.control;

/**
 * Created by mplienegger on 7/4/2016.
 */
public interface Presenter<V extends BaseView> {
    void attachView(V BaseView);
    void detachView();
}
