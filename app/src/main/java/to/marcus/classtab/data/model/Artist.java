package to.marcus.classtab.data.model;

/**
 * Created by marcus on 6/24/2016
 * Artist Model
 */
public class Artist{
    private String id;
    private String name;
    private String date;

    public Artist(String id, String name){
        this.name = name;
        this.id = id;
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
}
