package to.marcus.classtab.data.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcus on 6/24/2016
 * Artist Model
 */
public class Artist{
    private String id;
    private String name;
    private String date;

    public Artist(JSONObject json) throws JSONException{
        this.name = json.getString("name");
        this.id = json.getString("id");
        this.date = json.getString("date");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
