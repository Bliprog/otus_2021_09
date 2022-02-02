package hibernate.crm.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    public Address(){}

    public Address(Long id, String street){
        this.id=id;
        this.street=street;
    }
    public Address copy(){
        return new Address(this.id,this.street);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
