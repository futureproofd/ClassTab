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

    public abstract static class VideoTable{
        public static final String TABLE_NAME = "video";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "link";

        public static final String TABLE_CREATE = "create table "
                +TABLE_NAME +"("
                +COLUMN_ID+" text primary key, "
                +COLUMN_NAME+" text not null);";
    }

    public abstract static class TabTable{
        public static final String TABLE_NAME = "tab";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FILE = "file";

        public static final String TABLE_CREATE = "create table "
                +TABLE_NAME +"("
                +COLUMN_ID+" text primary key, "
                +COLUMN_FILE+" BLOB not null);";
    }

    public abstract static class SoundTable{
        public static final String TABLE_NAME = "sound";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "file";

        public static final String TABLE_CREATE = "create table "
                +TABLE_NAME +"("
                +COLUMN_ID+" text primary key, "
                +COLUMN_NAME+" text not null);";
    }

    public abstract static class SongTable{
        public static final String TABLE_NAME = "song";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final String TABLE_CREATE = "create table "
                +TABLE_NAME +"("
                +COLUMN_ID+" text primary key, "
                +COLUMN_NAME+" text not null);";
    }
}
