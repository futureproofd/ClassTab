package to.marcus.classtab.ui.control;

import java.util.LinkedHashMap;

import to.marcus.classtab.data.model.Tab;
import to.marcus.classtab.ui.control.base.BaseView;

/**
 * Created by mplienegger on 7/19/2016.
 */
public interface DetailView extends BaseView {
    void showTabs(LinkedHashMap<Integer,Tab> tabs);
}
