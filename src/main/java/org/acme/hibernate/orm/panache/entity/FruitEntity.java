package org.acme.hibernate.orm.panache.entity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class FruitEntity extends PanacheEntity {

    @Column(length = 40, unique = true)
    public String name;

    @JsonIgnore
    @OneToOne(optional = true, mappedBy = "fruit", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public FruitDetailsEntity details;

    public FruitEntity() {
    }

    public FruitEntity(String name) {
        this.name = name;
    }
}
