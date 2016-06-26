package to.marcus.classtab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by marcus on 6/24/2016
 */
public class ArtistDAO {
    private SQLiteDatabase database;
    private SQLiteDataHelper dbHelper;
    private String[] allColumns = {"Id, Name"};

    public ArtistDAO(Context context){
        dbHelper = new SQLiteDataHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //bootstrap
    public void populateArtists(HashMap<String,String> artistMap){
        ContentValues values = new ContentValues();
        Iterator it = artistMap.entrySet().iterator();
        open();
        while (it.hasNext()){
            database.beginTransaction();
            Map.Entry pair = (Map.Entry)it.next();
            values.put("Name",(String)pair.getValue());
            values.put("Id",(String)pair.getKey());
            try{
                database.insert(SQLiteDataHelper.TABLE_ARTIST, null, values);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        database.close();
    }

    public ArrayList<Artist> getAllArtists(){
        ArrayList<Artist> artists = new ArrayList<Artist>();
        open();
        Cursor cursor = database.query(SQLiteDataHelper.TABLE_ARTIST, allColumns, null, null, null, null," name ", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Artist artist = new Artist(cursor.getString(0),cursor.getString(1));
            artists.add(artist);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return artists;
    }
}
