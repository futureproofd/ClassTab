package to.marcus.classtab.data.model;

/**
 * Created by marcus on 7/12/2016
 * Map JSON google API image search keys to the object by GSON
 */
public class Photo {
    String title;
    String link;
    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return(title);
    }
}
