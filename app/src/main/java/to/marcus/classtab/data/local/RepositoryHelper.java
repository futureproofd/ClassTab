package to.marcus.classtab.data.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import to.marcus.classtab.data.local.contract.SQLStatement;
import to.marcus.classtab.data.model.Artist;

/**
 * Created by marcus on 6/29/2016
 *
 * make the arraylist type abstract
 */
public interface RepositoryHelper<K,V> {
    HashMap<K,V> query(SQLStatement sqlStatement);
    //// TODO: 6/30/2016 add/remove/delete etc... 
}
