package homework;

public class Customer implements Comparable<Customer> {
    private final long id;
    private String name;
    private long scores;


    public Customer(Customer src) {
        this.id = src.id;
        this.name = src.name;
        this.scores = src.scores;
    }

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;


        Customer customer = (Customer) o;

        if (id == customer.id) return true;
        if (scores != customer.scores) return false;
        return name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Customer o) {
        return Long.compare(scores, o.scores);
    }
}
