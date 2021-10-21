package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerStringTreeMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> mapEntry = customerStringTreeMap.firstEntry();
        return new AbstractMap.SimpleEntry<>(new Customer(mapEntry.getKey()), mapEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> mapEntry = customerStringTreeMap.higherEntry(customer);
        if (mapEntry == null) return null;
        return new AbstractMap.SimpleEntry<>(new Customer(mapEntry.getKey()), mapEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customerStringTreeMap.put(customer, data);
    }
}
