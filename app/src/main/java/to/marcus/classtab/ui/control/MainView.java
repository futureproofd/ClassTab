package to.marcus.classtab.ui.control;

import java.util.LinkedHashMap;

import to.marcus.classtab.data.model.Artist;
import to.marcus.classtab.ui.control.base.BaseView;

/**
 * Created by marcus on 7/4/2016
 */
public interface MainView extends BaseView {
    void showArtists(LinkedHashMap<Integer,Artist> artists);
}
