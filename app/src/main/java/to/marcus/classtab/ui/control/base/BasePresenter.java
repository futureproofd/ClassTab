package to.marcus.classtab.ui.control.base;


import to.marcus.classtab.ui.control.Presenter;
import to.marcus.classtab.ui.control.base.BaseView;

/**
 * Created by marcus on 7/4/2016
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {

    private T mBaseView;

    @Override
    public void attachView(T baseView) {
        mBaseView = baseView;
    }

    @Override
    public void detachView() {
        mBaseView = null;
    }

    public T getView(){
        return mBaseView;
    }
}
