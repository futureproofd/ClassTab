package to.marcus.classtab.data.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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
    private Long accessTime;
    private int columnSpan;

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
        if(!json.has("accessTime") || json.isNull("accessTime")){
            this.accessTime = 0l;
        }else{
            this.accessTime = json.getLong("accessTime");
        }
        this.columnSpan = 1;
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

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }
}
