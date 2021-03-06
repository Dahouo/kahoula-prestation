package com.afrologix.kahoula.resources.Customer;


import com.afrologix.kahoula.resources.AbstractAuditingEntity;
import com.afrologix.kahoula.resources.User.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Customer entity
 * @author arnaud.
 */
@Document(collection = "customer")
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("user_id")
    @DBRef
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getuser() {
        return user;
    }

    public Customer user(User user) {
        this.user = user;
        return this;
    }

    public void setuser(User user) {
        this.user = user;
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
        Customer customer = (Customer) o;
        if (customer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", user='" + getuser() + "'" +
            "}";
    }
}
