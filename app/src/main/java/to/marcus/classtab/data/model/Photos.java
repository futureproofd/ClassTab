package to.marcus.classtab.data.model;

import java.util.List;

/**
 * Created by marcus on 7/12/2016
 * Map JSON google API image search keys to the object by GSON
 */
public class Photos {
    private List<Photo> items;
    private PhotoQuery queries;

    public List<Photo> getItems() {
        return items;
    }

    public PhotoQuery getQueries(){
        return queries;
    }
}
