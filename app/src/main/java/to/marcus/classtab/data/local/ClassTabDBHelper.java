package to.marcus.classtab.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

import to.marcus.classtab.data.local.contract.ClassTabDB;

/**
 * Created by marcus on 6/24/2016
 * First run - create databases if(!exist) using Singleton pattern
 */
public class ClassTabDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static ClassTabDBHelper sInstance;
    private SQLiteDatabase mDatabase;
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private ClassTabDBHelper(Context context){
        super(context, ClassTabDB.DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Singleton - app context
    public static synchronized ClassTabDBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new ClassTabDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /*
    Activate 'open-state' by incrementing +1 - to avoid concurrent DB access issues
     */
    public synchronized SQLiteDatabase getWritableDB(){
        if(mOpenCounter.incrementAndGet() == 1){
            mDatabase = sInstance.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized SQLiteDatabase getReadableDB(){
        if(mOpenCounter.incrementAndGet() == 1){
            mDatabase = sInstance.getReadableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDB(){
        if(mOpenCounter.decrementAndGet() == 0){
            mDatabase.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassTabDB.ArtistTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.PhotoTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.SoundTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.VideoTable.TABLE_CREATE);
        db.execSQL(ClassTabDB.TabTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ClassTabDBHelper.class.getSimpleName(), "Upgrading DB from version "+ oldVersion +
            " to "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.ArtistTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.PhotoTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.SoundTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.VideoTable.TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS "+ ClassTabDB.TabTable.TABLE_CREATE);
        onCreate(db);
    }
}
