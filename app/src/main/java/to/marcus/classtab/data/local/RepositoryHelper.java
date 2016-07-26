package to.marcus.classtab.data.local;

import java.util.concurrent.Callable;

import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 6/29/2016
 */
public interface RepositoryHelper<T>{
    Callable<T> query(SQLStatement sqlStatement, String params);
    //// TODO: 6/30/2016 add/remove/delete etc... 
}
