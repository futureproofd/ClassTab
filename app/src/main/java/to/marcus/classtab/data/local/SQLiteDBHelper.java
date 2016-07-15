package to.marcus.classtab.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import to.marcus.classtab.data.local.contract.ClassTabDB;

/**
 * Created by marcus on 6/24/2016
 * First run - create databases if(!exist)
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context){
        super(context, ClassTabDB.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassTabDB.ArtistTable.TABLE_CREATE);
        //// TODO: 7/15/2016 reinstall and recreate phototable 
        db.execSQL(ClassTabDB.PhotoTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.SoundTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.VideoTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.TabTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteDBHelper.class.getSimpleName(), "Upgrading DB from version "+ oldVersion +
            " to "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.ArtistTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.PhotoTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.SoundTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.VideoTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.TabTable.TABLE_CREATE);
        onCreate(db);
    }
}
