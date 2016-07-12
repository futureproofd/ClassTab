package to.marcus.classtab.ui.control;

import java.util.HashMap;
import java.util.LinkedHashMap;

import to.marcus.classtab.data.model.Artist;

/**
 * Created by marcus on 7/4/2016
 */
public interface MainView extends BaseView {
    void showTabs(HashMap<String,byte[]> tabs);
    void showArtists(LinkedHashMap<Integer,Artist> artists);
}
