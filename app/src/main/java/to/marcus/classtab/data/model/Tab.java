package to.marcus.classtab.data.model;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mplienegger on 7/20/2016.
 */
public class Tab {
    private String artistId;
    private byte[] file;
    private String name;

    public Tab(JSONObject json) throws JSONException {
        if(!json.has("name") || json.isNull("name")){
            this.name = "No Name";
        }else{
            this.name = json.getString("name");
        }
        this.artistId = json.getString("id");
        this.file = Base64.decode(json.getString("file"),Base64.DEFAULT);
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
