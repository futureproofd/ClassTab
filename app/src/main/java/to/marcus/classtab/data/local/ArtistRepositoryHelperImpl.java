package to.marcus.classtab.data.local;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

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
    private Context mContext;

    @Inject
    public ArtistRepositoryHelperImpl(Application context){
        this.mContext = context;
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

    public void populateArtistsDates(HashMap<String,String> artistDateMap){
        ContentValues values = new ContentValues();
        Iterator it = artistDateMap.entrySet().iterator();
        open();
        while (it.hasNext()){
            database.beginTransaction();
            Map.Entry pair = (Map.Entry)it.next();
            values.put(ClassTabDB.ArtistTable.COLUMN_DATE,(String)pair.getValue());
            try{
                database.update(ClassTabDB.ArtistTable.TABLE_NAME, values, "id='"+pair.getKey()+"'",null);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }

    /**
     * RepositoryHelper implementation to get a Database recordset
     * @param sqlStatement A raw, parameterized SQL query
     * @return Callable for an Observable
     */
    @Override
    public Callable<LinkedHashMap<String,String>> query(SQLStatement sqlStatement) {
        final String SQLQuery = sqlStatement.sqlQuery();
        return new Callable<LinkedHashMap<String, String>>() {
            @Override
            public LinkedHashMap<String, String> call() throws Exception {
                open();
                LinkedHashMap<String, String> artists = new LinkedHashMap<>();
                try{
                    Cursor cursor = database.rawQuery(SQLQuery, new String[]{});
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
        };
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
