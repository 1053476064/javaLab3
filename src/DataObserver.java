import java.util.List;

public interface DataObserver {
    //For get called with the new filtered data when the subject notifies me
    void onDataChanged(List<String[]> filteredData);
}
