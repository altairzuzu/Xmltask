package ru.solomatin.xmltask;

import android.app.Application;

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
                .networkModule(new NetworkModule("http://127.0.0.1"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}

