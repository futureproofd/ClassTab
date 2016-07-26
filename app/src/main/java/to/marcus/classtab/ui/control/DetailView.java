package to.marcus.classtab.ui.control;

import org.json.JSONArray;

import java.util.LinkedHashMap;

import to.marcus.classtab.data.model.Tab;

/**
 * Created by mplienegger on 7/19/2016.
 */
public interface DetailView extends BaseView {
    void showTabs(LinkedHashMap<Integer,Tab> tabs);
}
