package ru.solomatin.xmltask;

import android.support.v4.util.LruCache;

import javax.inject.Inject;

import ru.solomatin.xmltask.Model.ApiResponse;
import ru.solomatin.xmltask.Network.NetworkApi;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter
 */
@SuppressWarnings("unchecked")
public class Presenter {

    private MainActivity view;
    private Subscription subscription;
    @Inject NetworkApi networkService;
    @Inject
    LruCache<String, Observable<?>> cacheObservables;

    public Presenter(MainActivity view){
        this.view = view;
        ((RxApplication) view.getApplication()).getNetworkComponent().inject(this);
    }

    public void loadRxData(String url, boolean useCache){
        view.showRxInProcess();
        // Подготавливаем Observable к подписке либо извлекаем из кэша
        Observable<ApiResponse> apiResponseObservable = (Observable<ApiResponse>)
                getPreparedObservable(networkService.getPersonsObservable(url), url, useCache);
        subscription = apiResponseObservable.subscribe(new Observer<ApiResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(ApiResponse response) {
                // Передаем полученный xml-ответ в MainActivity
                view.showRxResults(response);
            }
        });
    }

    /**
     * Отписывает от событий Observable
     */
    public void rxUnSubscribe(){
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    /**
     * Возвращает кэшированный Observable или подготавливает новый
     *
     * @param unPreparedObservable Неподготовленный Observable
     * @param url Используется, как ключ в LruCache
     * @return Observable, подготовленная для подписки
     */
    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable,
                                               String url,
                                               boolean useCache) {
        Observable<?> preparedObservable = null;
        if (useCache) {
            // Ищем уже готовый Observable в кэше
            preparedObservable = cacheObservables.get(url);
        }

        if (preparedObservable != null) {
            return preparedObservable;
        }
        // Если в кэше нет, значит, подготавливаем новый
        preparedObservable = unPreparedObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
        cacheObservables.put(url, preparedObservable);
        return preparedObservable;
    }

}


