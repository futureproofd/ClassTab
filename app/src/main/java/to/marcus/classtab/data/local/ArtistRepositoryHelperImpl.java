package to.marcus.classtab.data.local;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 6/24/2016
 */
public class ArtistRepositoryHelperImpl implements RepositoryHelper {
    private SQLiteDatabase database;
    private ClassTabDBHelper dbHelper;
    private Context mContext;

    @Inject
    public ArtistRepositoryHelperImpl(Application context){
        this.mContext = context;
        dbHelper = ClassTabDBHelper.getInstance(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    /**
     * Bootstrap processes
     */
    public Callable<Boolean> populateArtists(HashMap<String,String> artistMap){
        final HashMap<String,String> tmpartistMap = artistMap;
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                open();
                ContentValues values = new ContentValues();
                Iterator it = tmpartistMap.entrySet().iterator();
                boolean success = false;
                while (it.hasNext()){
                    database.beginTransaction();
                    Map.Entry pair = (Map.Entry)it.next();
                    values.put(ClassTabDB.ArtistTable.COLUMN_ID,(String)pair.getKey());
                    values.put(ClassTabDB.ArtistTable.COLUMN_NAME,(String)pair.getValue());
                    try{
                        database.insert(ClassTabDB.ArtistTable.TABLE_NAME, null, values);
                        database.setTransactionSuccessful();
                        success = true;
                    }finally {
                        database.endTransaction();
                    }
                }
                close();
                return success;
            }
        };

    }

    public Callable<Boolean> populateArtistsDates(HashMap<String,String> artistDateMap){
        final HashMap<String,String> tmpArtistDateMap = artistDateMap;
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                open();
                boolean success = false;
                ContentValues values = new ContentValues();
                Iterator it = tmpArtistDateMap.entrySet().iterator();
                while (it.hasNext()){
                    database.beginTransaction();
                    Map.Entry pair = (Map.Entry)it.next();
                    values.put(ClassTabDB.ArtistTable.COLUMN_DATE,(String)pair.getValue());
                    try{
                        database.update(ClassTabDB.ArtistTable.TABLE_NAME, values, "id='"+pair.getKey()+"'",null);
                        database.setTransactionSuccessful();
                        success = true;
                    }finally {
                        database.endTransaction();
                    }
                }
                close();
                return success;
            }
        };

    }

    /**
     * RepositoryHelper implementation to get a Database recordset
     * @param sqlStatement A raw, parameterized SQL query
     * @return Callable for an Observable
     */
    @Override
    public Callable<JSONArray> query(SQLStatement sqlStatement, String params) {
        final String SQLQuery = sqlStatement.sqlQuery(params);
        return new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                open();
                try{
                    Cursor cursor = database.rawQuery(SQLQuery, new String[]{});
                    JSONArray resultSet = new JSONArray();
                    for(int i = 0, size = cursor.getCount(); i < size; i++){
                        cursor.moveToPosition(i);
                        JSONObject rowObject = new JSONObject();
                        for(int p = 0; p< cursor.getColumnCount(); p++){
                            rowObject.put(cursor.getColumnName(p),cursor.getString(p));
                        }
                        resultSet.put(rowObject);
                    }
                    cursor.close();
                    return resultSet;
                }finally {
                    close();
                }
            }
        };
    }



}
