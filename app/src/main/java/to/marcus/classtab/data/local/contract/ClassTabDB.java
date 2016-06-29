package to.marcus.classtab.data.local.contract;

/**
 * Created by marcus on 6/29/2016
 */
public class ClassTabDB {
    public static final String DATABASE_NAME = "classTab.db";

    public ClassTabDB(){}

    public abstract static class ArtistTable{
        public static final String TABLE_NAME = "artist";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final String TABLE_CREATE = "create table "
                +TABLE_NAME +"("
                +COLUMN_ID+" text primary key, "
                +COLUMN_NAME+" text not null);";
    }
}
