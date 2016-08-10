package to.marcus.classtab.data.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mplienegger on 8/5/2016
 */
public class ArtistSorter {

    public static class ArtistComparator{

       public static Map<Integer, Artist> sortByAccessTime(Map<Integer, Artist> unsortMap){
           List<Map.Entry<Integer, Artist>> list = new LinkedList<>(unsortMap.entrySet());
           // Sorting the list based on Access Time
           Collections.sort(list, new Comparator<Map.Entry<Integer, Artist>>(){
               public int compare(Map.Entry<Integer, Artist> o1, Map.Entry<Integer, Artist> o2){
                   return o2.getValue().getAccessTime().compareTo(o1.getValue().getAccessTime());
               }
           });

           //Redefine dataset for Adapter with new key corresponding to new order
           LinkedHashMap<Integer, Artist> sortedMap = new LinkedHashMap<>();
           int newKey = 0;
           for (Map.Entry<Integer, Artist> entry : list)
           {
               sortedMap.put(newKey, entry.getValue());
               newKey++;
           }
           return sortedMap;
       }
    }


}
