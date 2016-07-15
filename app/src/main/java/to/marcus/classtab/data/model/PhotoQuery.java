package to.marcus.classtab.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 7/15/2016
 */
public class PhotoQuery {

    private List<Request> request = new ArrayList<Request>();

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }
}
