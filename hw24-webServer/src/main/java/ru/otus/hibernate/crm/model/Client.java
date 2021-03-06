package ru.otus.hibernate.crm.model;


import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Expose
    private Long id;

    @Column(name = "name")
    @Expose
    private String name;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    @Expose
    private List<Phone> phones=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Expose
    private Address address;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public  Client(Long id, String name, Address address, List<Phone> phones){
        this.id=id;
        this.name=name;
        this.address=address;
        this.phones=phones;
        setClientToPhones(this.phones);
    }
    public void setClientToPhones(List<Phone> phones){
        phones.forEach(phone -> phone.setClient(this));
    }
    @Override
    public Client clone() {
        List<Phone> phoneCopies=new ArrayList<>();
        if(this.phones!=null){
            for (Phone phone:this.phones){
                phoneCopies.add(phone.copy());
            }
        }
        Address addressCopy=null;
        if(this.address!=null){
           addressCopy=address.copy();
        }

        return new Client(this.id, this.name, addressCopy, phoneCopies);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
        setClientToPhones(phones);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
