package to.marcus.classtab.data.local;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Base64OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 6/30/2016
 */
public class TabRepositoryHelperImpl implements RepositoryHelper{
    private SQLiteDatabase database;
    private SQLiteDBHelper dbHelper;
    private Context mContext;

    @Inject
    public TabRepositoryHelperImpl(Application context){
        this.mContext = context;
        dbHelper = new SQLiteDBHelper(context);
    }

    /**
     * RepositoryHelper implementation to get a Database recordset
     * @param sqlStatement A raw, parameterized SQL query
     * @return Callable for an Observable
     */
    @Override
    public Callable<JSONArray> query(final SQLStatement sqlStatement) {
        final String SQLQuery = sqlStatement.sqlQuery();
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
                        String tabData = Base64.encodeToString(cursor.getBlob(1),Base64.DEFAULT);
                        rowObject.put(cursor.getColumnName(0),tabData);
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

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void populateTabs(HashMap<String,String> tabsMap){
        ContentValues values = new ContentValues();
        Iterator it = tabsMap.entrySet().iterator();
        open();
        while (it.hasNext()){
            database.beginTransaction();
            Map.Entry pair = (Map.Entry)it.next();
            values.put(ClassTabDB.TabTable.COLUMN_ID,(String)pair.getKey());
            values.put(ClassTabDB.TabTable.COLUMN_FILE,readAsset((String)pair.getValue()));
            try{
                database.insert(ClassTabDB.TabTable.TABLE_NAME, null, values);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }

    public void populateTabTitles(HashMap<String,String> songTitles){
        ContentValues values = new ContentValues();
        Iterator it = songTitles.entrySet().iterator();
        open();
        while (it.hasNext()){
            database.beginTransaction();
            Map.Entry pair = (Map.Entry)it.next();
            values.put(ClassTabDB.TabTable.COLUMN_NAME,(String)pair.getValue());
            try{
                database.update(ClassTabDB.TabTable.TABLE_NAME, values, "id='"+pair.getKey()+"'",null);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }

    private byte[] readAsset(String fileName){
        try{
            InputStream inputStream = mContext.getAssets().open("tabs/"+fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return buffer;
        }catch (IOException e){
            //file doesn't exist
            byte[] buffer = new byte[0];
            return buffer;
        }
    }
}
