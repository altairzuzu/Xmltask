package ru.solomatin.xmltask.Network;

import retrofit2.http.GET;
import retrofit2.http.Url;
import ru.solomatin.xmltask.Model.ApiResponse;
import rx.Observable;

/**
 * Описывает интерфейс API
 */
public interface NetworkApi {
//    @GET("/1.xml")
//    Observable<ApiResponse> getPersonsObservable();
    @GET
    Observable<ApiResponse> getPersonsObservable(@Url String url);

}
