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
    private String url;
    private String encodedName;

    public Artist(JSONObject json) throws JSONException{
        this.name = json.getString("name");
        this.id = json.getString("id");
        this.date = json.getString("date");
        if(!json.has("url") || json.isNull("url")){
            this.url = "www.placeholder.com";
        }else{
            this.url = json.getString("url");
        }
        if(!json.has("encodedName") || json.isNull("encodedName")){
            this.encodedName = "";
        }else{
            this.encodedName = json.getString("encodedName");
        }
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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncodedName() {
        return encodedName;
    }

    public void setEncodedName(String encodedName) {
        this.encodedName = encodedName;
    }
}
