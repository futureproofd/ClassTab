package to.marcus.classtab.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import to.marcus.classtab.data.model.Photos;

/**
 * Created by marcus on 7/12/2016
 * Define endpoints
 */
public interface GoogleImageAPI {
    @GET("v1?key=AIzaSyCKHoqK_ofLeaoRjsT7lNYeXOVjGKubpjE&cx=008509658288434337533:wweu7ml6wic&searchType=image&fileType=jpg&imgSize=large&imgType=face&imgColorType=black&imgDominantColor=black&alt=json")
    Observable<Photos> loadPhotos(@Query("q") String artistQuery);
}
