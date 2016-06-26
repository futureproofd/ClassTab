package to.marcus.classtab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marcus on 6/24/2016
 */
public class SQLiteDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "classTab.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ARTIST = "artist";
    public static final String DATABASE_CREATE = "create table "
            +TABLE_ARTIST +"("
            +"Id text primary key, "
            +"Name text not null);";

    public SQLiteDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteDataHelper.class.getSimpleName(), "Upgrading DB from version "+ oldVersion +
            " to "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ARTIST);
        onCreate(db);
    }
}
