package ru.solomatin.xmltask;

import android.app.Application;

import ru.solomatin.xmltask.Dependency.AppModule;
import ru.solomatin.xmltask.Dependency.DaggerNetworkComponent;
import ru.solomatin.xmltask.Dependency.NetworkComponent;
import ru.solomatin.xmltask.Dependency.NetworkModule;

/**
 * Application. Инициализируем и храним здесь DaggerNetworkComponent
 */
public class RxApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("http://192.168.1.33:3000"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}

