package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observer {

    protected List<Observable> observables = new ArrayList<>();

    public abstract void notifyObservables();

}
