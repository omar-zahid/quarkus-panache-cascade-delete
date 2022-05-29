package org.acme.hibernate.orm.panache.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Cacheable
public class FruitDetailsEntity extends PanacheEntity {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(optional = true)
    @JoinColumn(name = "fruit_id", unique = true, nullable = false, updatable = false)
    public FruitEntity fruit;

    public String color;

    public static FruitDetailsEntity findByFruitID(Long fruit_id) {
        return find("fruit_id", fruit_id).firstResult();
    }

}
