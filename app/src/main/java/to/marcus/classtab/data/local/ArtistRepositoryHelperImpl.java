package to.marcus.classtab.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;
import to.marcus.classtab.data.model.Artist;

/**
 * Created by marcus on 6/24/2016
 */
public class ArtistRepositoryHelperImpl implements RepositoryHelper {
    private SQLiteDatabase database;
    private SQLiteDBHelper dbHelper;
    private String[] allColumns = {"Id, Name"};

    public ArtistRepositoryHelperImpl(Context context){
        dbHelper = new SQLiteDBHelper(context);
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
            values.put(ClassTabDB.ArtistTable.COLUMN_ID,(String)pair.getKey());
            values.put(ClassTabDB.ArtistTable.COLUMN_NAME,(String)pair.getValue());
            try{
                database.insert(ClassTabDB.ArtistTable.TABLE_NAME, null, values);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }

    @Override
    public HashMap<String,String> query(SQLStatement sqlStatement) {
        SQLStatement params = (SQLStatement) sqlStatement;
        open();
        HashMap<String, String> artists = new HashMap<>();
        try{
            Cursor cursor = database.rawQuery(params.sqlQuery(), new String[]{});
            for(int i = 0, size = cursor.getCount(); i < size; i++){
                cursor.moveToPosition(i);
                artists.put(cursor.getString(0),cursor.getString(1));
            }
            cursor.close();
            return artists;
        }finally {
            close();
        }
    }

    //Make Rx, and communicate with DataManager
    public ArrayList<Artist> getAllArtists(){
        ArrayList<Artist> artists = new ArrayList<Artist>();
        open();
        Cursor cursor = database.query(ClassTabDB.ArtistTable.TABLE_NAME, allColumns, null, null, null, null," name ", null);
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
