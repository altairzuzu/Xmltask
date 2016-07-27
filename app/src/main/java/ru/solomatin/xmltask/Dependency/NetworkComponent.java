package ru.solomatin.xmltask.Dependency;

import javax.inject.Singleton;

import dagger.Component;
import ru.solomatin.xmltask.Presenter;

/**
 * Created by altair on 15.07.2016.
 */
@Singleton
@Component (modules = NetworkModule.class)
public interface NetworkComponent {
    //void inject (MainActivity activity);
    void inject(Presenter presenter);
}
