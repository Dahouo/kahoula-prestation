package com.afrologix.kahoula.resources;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Location.
 */
@Document(collection = "location")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("region")
    @DBRef
    private Region region;

    @NotNull
    @Field("city")
    @DBRef
    private City city;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @NotNull Region getregion() {
        return region;
    }

    public Location region(Region region) {
        this.region = region;
        return this;
    }

    public void setregion(Region region) {
        this.region = region;
    }

    public @NotNull City getcity() {
        return city;
    }

    public Location city(City city) {
        this.city = city;
        return this;
    }

    public void setcity(City city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", region='" + getregion() + "'" +
            ", city='" + getcity() + "'" +
            "}";
    }
}
