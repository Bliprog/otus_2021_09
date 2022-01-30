package ru.otus.crm.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    public Phone(){}
    public Phone(Long id, String number, Client client){
        this.id=id;
        this.number=number;
        this.client=client;
    }

    public Phone(Long id, String number){
        this.id=id;
        this.number=number;
    }
    public Phone copy(){
        return new Phone(this.id,this.number,this.client);
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
