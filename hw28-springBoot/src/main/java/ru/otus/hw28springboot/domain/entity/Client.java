package ru.otus.hw28springboot.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@Table("client")
public class Client implements Persistable<Long> {
    @Id
    private final Long id;

    @NonNull
    private final String name;

    @MappedCollection(idColumn = "client_id", keyColumn = "client_id")
    private List<Phone> phones = null;

    @Column("client_id")
    private Address address = null;

    @PersistenceConstructor
    public Client(Long id, String name, List<Phone> phones, Address address) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
