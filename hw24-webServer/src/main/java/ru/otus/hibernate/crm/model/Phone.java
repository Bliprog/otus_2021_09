package ru.otus.hibernate.crm.model;

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;
    @Expose
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private Client client;

    public Phone() {
    }

    public Phone(Long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone copy() {
        return new Phone(this.id, this.number, this.client);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return number;
    }
}
