package to.marcus.classtab;

/**
 * Created by marcus on 6/24/2016
 * Model
 */
public class Artist {
    private String id;
    private String name;

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

    //test
    @Override
    public String toString(){
        return name;
    }

}