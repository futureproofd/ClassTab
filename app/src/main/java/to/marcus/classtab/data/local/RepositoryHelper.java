package to.marcus.classtab.data.local;

import java.util.concurrent.Callable;

import to.marcus.classtab.data.local.contract.SQLQueryStatement;
import to.marcus.classtab.data.local.contract.SQLUpdateStatement;

/**
 * Created by marcus on 6/29/2016
 */
public interface RepositoryHelper<T,P,V>{
    Callable<T> query(SQLQueryStatement sqlQueryStatement, String params);
    Callable<T> update(String field, SQLUpdateStatement sqlQueryStatement, V value, P params);
}
