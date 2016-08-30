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
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;
import to.marcus.classtab.data.model.PhotoQuery;
import to.marcus.classtab.data.model.Photos;

/**
 * Created by mplienegger on 7/18/2016
 */
public class PhotoRepositoryHelperImpl implements RepositoryHelper {

    private SQLiteDatabase database;
    private ClassTabDBHelper dbHelper;
    private Context mContext;

    @Inject
    public PhotoRepositoryHelperImpl(Application context){
        this.mContext = context;
        dbHelper = ClassTabDBHelper.getInstance(context);
    }

    public void openForWrite() throws SQLException{
        database = ClassTabDBHelper.getInstance(mContext).getWritableDatabase();
    }

    public void openForRead() throws SQLException{
        database = ClassTabDBHelper.getInstance(mContext).getReadableDatabase();
    }

    public void close(){
        dbHelper.close();
    }


    @Override
    public Callable<JSONArray> query(SQLQueryStatement sqlQueryStatement, String params) {
        final String SQLQuery = sqlQueryStatement.sqlQuery(params);
        return new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                openForRead();
                try{
                    Cursor cursor = database.rawQuery(SQLQuery, new String[]{});
                    JSONArray resultSet = new JSONArray();
                    for(int i = 0, size = cursor.getCount(); i<size; i++){
                        cursor.moveToPosition(i);
                        JSONObject rowObject = new JSONObject();
                        rowObject.put(cursor.getColumnName(0),cursor.getString(0));
                        rowObject.put(cursor.getColumnName(1),cursor.getString(1));
                        rowObject.put(cursor.getColumnName(2),cursor.getString(2));
                        resultSet.put(rowObject);
                    }
                    cursor.close();
                    return resultSet;
                }catch (SQLException e){
                    throw new SQLException("Could not query: "+e);
                }finally {
                    close();
                }

            }
        };
    }

    @Override
    public Callable update(String field, Object value, Object params) {
        return null;
    }

    public void populatePhotoNameAndId(HashMap<String,String> artistMap){
        ContentValues values = new ContentValues();
        Iterator it = artistMap.entrySet().iterator();
        openForWrite();
        while (it.hasNext()){
            database.beginTransaction();
            Map.Entry pair = (Map.Entry)it.next();
            values.put(ClassTabDB.PhotoTable.COLUMN_ID,(String)pair.getKey());
            values.put(ClassTabDB.PhotoTable.COLUMN_NAME,(String)pair.getValue());
            try{
                database.insert(ClassTabDB.PhotoTable.TABLE_NAME, null, values);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }

    public void populatePhotoURL(List<Photos> urlList){
        ContentValues values = new ContentValues();
        openForWrite();
        for(Photos p : urlList){
            database.beginTransaction();
            for(int i= 0;i < 1; i++){
                values.put(ClassTabDB.PhotoTable.COLUMN_URL,p.getItems().get(0).getLink());
                PhotoQuery searchTerm = p.getQueries();
                try{
                    String searchString = searchTerm.getRequest().get(0).getSearchTerms();
                    database.update(ClassTabDB.PhotoTable.TABLE_NAME,values,"encodedName='"+searchString+"'",null);
                    database.setTransactionSuccessful();
                }finally {
                    database.endTransaction();
                }
            }
        }
        close();
    }
}