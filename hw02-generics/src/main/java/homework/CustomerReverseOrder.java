package homework;


import java.util.Stack;

public class CustomerReverseOrder {
    private final Stack<Customer> customersStack = new Stack<>();

    public void add(Customer customer) {
        customersStack.push(customer);
    }

    public Customer take() {
        return customersStack.pop();
    }
}
