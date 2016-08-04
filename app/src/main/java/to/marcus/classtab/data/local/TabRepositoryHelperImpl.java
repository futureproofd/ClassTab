package to.marcus.classtab.data.local;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Base64;
import android.util.Log;

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
import to.marcus.classtab.data.local.contract.SQLQueryStatement;
import to.marcus.classtab.data.local.contract.SQLUpdateStatement;
import to.marcus.classtab.util.StringUtils;

/**
 * Created by marcus on 6/30/2016
 */
public class TabRepositoryHelperImpl implements RepositoryHelper{
    private SQLiteDatabase database;
    private ClassTabDBHelper dbHelper;
    private Context mContext;

    @Inject
    public TabRepositoryHelperImpl(Application context){
        this.mContext = context;
        dbHelper = ClassTabDBHelper.getInstance(context);
    }

    /**
     * RepositoryHelper implementation to get a Database recordset
     * @param sqlQueryStatement A raw, parameterized SQL query
     * @return Callable for an Observable
     */
    @Override
    public Callable<JSONArray> query(final SQLQueryStatement sqlQueryStatement, String params) {
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
                        String tabData = Base64.encodeToString(cursor.getBlob(1),Base64.DEFAULT);
                        rowObject.put(cursor.getColumnName(0),cursor.getString(0));
                        rowObject.put(cursor.getColumnName(1),tabData);
                        rowObject.put(cursor.getColumnName(2),cursor.getString(2));
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

    @Override
    public Callable update(String field, SQLUpdateStatement sqlQueryStatement, Object value, Object params) {
        return null;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    /*
    Bootstrap methods
     */
    public Callable<Boolean> populateTabs(HashMap<String,String> tabsMap){
        final HashMap<String,String> tmpTabMap = tabsMap;
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean isSuccess = false;
                ContentValues values = new ContentValues();
                Iterator it = tmpTabMap.entrySet().iterator();
                open();
                while (it.hasNext()){
                    database.beginTransaction();
                    Map.Entry pair = (Map.Entry)it.next();
                    values.put(ClassTabDB.TabTable.COLUMN_ID,(String)pair.getKey());
                    values.put(ClassTabDB.TabTable.COLUMN_FILE,readFromAsset((String)pair.getValue()));
                    try{
                        database.insert(ClassTabDB.TabTable.TABLE_NAME, null, values);
                        database.setTransactionSuccessful();
                        isSuccess = true;
                    }finally {
                        database.endTransaction();
                    }
                }
                close();
                return isSuccess;
            }
        };
    }

    public Callable<Boolean> populateTabTitles(HashMap<String,String> tabTitles){
        final HashMap<String,String> tmpTabTitles = tabTitles;
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean isSuccess = false;
                ContentValues values = new ContentValues();
                Iterator it = tmpTabTitles.entrySet().iterator();
                open();
                while (it.hasNext()){
                    database.beginTransaction();
                    Map.Entry pair = (Map.Entry)it.next();
                    String formattedTitle = StringUtils.escapeSpecialChars((String)pair.getValue(),true);
                    //String formattedTitle = pair.getValue().toString().replaceAll("\"","");
                    // formattedTitle.replaceAll("\'","");
                    values.put(ClassTabDB.TabTable.COLUMN_NAME,formattedTitle);
                    try {
                        database.update(ClassTabDB.TabTable.TABLE_NAME, values, "id='"+pair.getKey()+"'",null);
                        database.setTransactionSuccessful();
                        isSuccess = true;
                    }catch (SQLiteException e){
                        Log.e("TABDBHELPER: ","exception:"+e);
                    }finally {
                        database.endTransaction();
                    }
                }
                close();
                return isSuccess;
            }
        };

    }

    private byte[] readFromAsset(String fileName){
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
