import java.util.ArrayList;
import java.util.List;

public class DataFilterSubject {
    private final List<DataObserver> observers = new ArrayList<>();

    public void addObserver(DataObserver o) {
        observers.add(o);
    }

    public void notifyObservers(List<String[]> newData) {
        for (DataObserver o : observers) {
            o.onDataChanged(newData);
        }
    }
}
