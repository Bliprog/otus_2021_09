package ru.otus.hw28springboot.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Data
@Table("phone")
public class Phone {
    @Id
    private final Long id;

    @NonNull
    private final String number;

    @Column("client_id")
    private final Long clientId;

    @Override
    public String toString() {
        return number;
    }
}
