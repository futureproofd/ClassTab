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
import to.marcus.classtab.data.local.contract.SQLQueryStatement;
import to.marcus.classtab.data.local.contract.SQLUpdateStatement;

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

    //Set Display Date and default accessTime
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
                    values.put(ClassTabDB.ArtistTable.COLUMN_ACCESSTIME, 0);
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
     * @param sqlQueryStatement A raw, parameterized SQL query
     * @return Callable for an Observable
     */
    @Override
    public Callable<JSONArray> query(SQLQueryStatement sqlQueryStatement, String params) {
        final String SQLQuery = sqlQueryStatement.sqlQuery(params);
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

    /**
     * RepositoryHelper implementation to update a Database record
     * @param field the database field to perform update on
     * @param value the update value
     * @param params the where clause (id equals param)
     * @return boolean on success or failure
     */
    @Override
    public Callable<Boolean> update(String field, Object value, Object params) {
        final String updateField = field;
        final Object updateValue = value;
        final Object updateId = params;
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                open();
                boolean success = false;
                database.beginTransaction();
                //// TODO: 8/4/2016 cast as correct type based on field name (new helper static method?)
                try{
                    database.update(ClassTabDB.ArtistTable.TABLE_NAME, castToContentValues(updateField,updateValue), "id='"+updateId+"'",null);
                    database.setTransactionSuccessful();
                    success = true;
                }finally {
                    database.endTransaction();
                }
                close();
                return success;
            }
        };
    }

    /*
    To ensure the update field is cast to the correct type before updating the DB
     */
    private <T> ContentValues castToContentValues(String updateField, T value){
        ContentValues values = new ContentValues();
        switch (updateField){
            case ClassTabDB.ArtistTable.COLUMN_ACCESSTIME:
                values.put(updateField,(Long)value);
                break;
            case ClassTabDB.ArtistTable.COLUMN_DATE:
                values.put(updateField,(String)value);
                break;
            case ClassTabDB.ArtistTable.COLUMN_NAME:
                values.put(updateField,(String)value);
                break;
        }
        return values;
    }

}
