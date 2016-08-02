package to.marcus.classtab.ui.control.base;


import java.lang.ref.WeakReference;
import to.marcus.classtab.ui.control.Presenter;

/**
 * Created by marcus on 7/4/2016
 */
public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private WeakReference<V> mBaseView;

    @Override
    public void attachView(V baseView) {
        if(!isViewAttached()){
            mBaseView = new WeakReference<>(baseView);
        }
    }

    @Override
    public void detachView() {
        mBaseView.clear();
        mBaseView = null;
    }

    @Override
    public boolean isViewAttached(){
        return mBaseView != null && mBaseView.get() != null;
    }

    public V getView(){
        return mBaseView == null ? null : mBaseView.get();
    }
}
