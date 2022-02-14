package ru.otus.hw28springboot.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("address")
public class Address {
    @Id
    private final Long id;

    private final String street;

    @Column("client_id")
    private final Long clientId;

}
