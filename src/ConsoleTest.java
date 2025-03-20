import java.util.List;

public class ConsoleTest {
    public static void runTest(List<String[]> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for testing");
            return;
        }
        
        System.out.println("First data entry: " + String.join(", ", data.get(0)));
        if (data.size() >= 10) {
            System.out.println("Tenth data entry: " + String.join(", ", data.get(9)));
        } else {
            System.out.println("Less than 10 data entries available");
        }
        System.out.println("Total number of entries: " + data.size());
        System.out.println("Data source: Department of Government Efficiency, Good job Musk");
    }
}
