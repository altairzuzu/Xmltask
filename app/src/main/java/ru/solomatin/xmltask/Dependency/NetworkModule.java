package ru.solomatin.xmltask.Dependency;

import android.support.v4.util.LruCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.solomatin.xmltask.Network.NetworkApi;
import rx.Observable;

/**
 * Модуль предоставления сетевых зависимостей
 */
@Module
public class NetworkModule {

    String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

//    @Provides
//    @Singleton
//    Cache provideOkHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        return new Cache(application.getCacheDir(), cacheSize);
//    }

//    @Provides
//    @Singleton
//    OkHttpClient provideOkHttpClient(Cache cache) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .cache(cache)
//                .build();
//        return client;
//    }

    @Provides
    @Singleton
    //Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    NetworkApi provideNetworkService(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }

    @Provides
    @Singleton
    LruCache<Class<?>, Observable<?>> provideLruCache() {
        int lruCacheSize = 1024 * 1024;
        return new LruCache<>(lruCacheSize);
    }

}