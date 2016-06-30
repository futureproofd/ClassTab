package to.marcus.classtab.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 6/30/2016
 */
public class TabRepositoryHelperImpl implements RepositoryHelper{
    private SQLiteDatabase database;
    private SQLiteDBHelper dbHelper;
    private String[] allColumns = {"Id, Name"};

    public TabRepositoryHelperImpl(Context context){
        dbHelper = new SQLiteDBHelper(context);
    }
    
    @Override
    public HashMap query(SQLStatement sqlStatement) {
        return null;
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
            values.put(ClassTabDB.TabTable.COLUMN_NAME,(String)pair.getValue());
            //// TODO: 6/30/2016 do something with txt BLOB data
            try{
                database.insert(ClassTabDB.ArtistTable.TABLE_NAME, null, values);
                database.setTransactionSuccessful();
            }finally {
                database.endTransaction();
            }
        }
        close();
    }
}
