package com.afrologix.kahoula.resources.JobBid;


import com.afrologix.kahoula.resources.AbstractAuditingEntity;
import com.afrologix.kahoula.resources.Customer.Customer;
import com.afrologix.kahoula.resources.Location.Location;
import com.afrologix.kahoula.resources.Partner.Partner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.afrologix.kahoula.resources.enumeration.JobType;

import com.afrologix.kahoula.resources.enumeration.JobStatus;

/**
 * JobBid entity.
 * @author arnaud.
 */
@Document(collection = "job_bid")
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "jobbid")
public class JobBid extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("type")
    @DBRef
    private List<JobType> type;

    @Field("wish_date")
    private Instant wishDate;

    @NotNull
    @Field("customer")
    @DBRef
    private Customer customer;

    @NotNull
    @Field("location")
    @DBRef
    private Location location;

    @NotNull
    @Field("partner")
    @DBRef
    private Partner partner;

    @NotNull
    @Field("amount")
    private Double amount;

    @Field("status")
    @DBRef
    private JobStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public JobBid description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull List<JobType> getType() {
        return type;
    }

    public JobBid type(List<JobType> type) {
        this.type = type;
        return this;
    }

    public void setType(List<JobType> type) {
        this.type = type;
    }

    public Instant getWishDate() {
        return wishDate;
    }

    public JobBid wishDate(Instant wishDate) {
        this.wishDate = wishDate;
        return this;
    }

    public void setWishDate(Instant wishDate) {
        this.wishDate = wishDate;
    }

    public @NotNull Customer getcustomer() {
        return customer;
    }

    public JobBid customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setcustomer(Customer customer) {
        this.customer = customer;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public JobBid location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Partner getpartner() {
        return partner;
    }

    public JobBid partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setpartner(Partner partner) {
        this.partner = partner;
    }

    public Double getAmount() {
        return amount;
    }

    public JobBid amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public JobStatus getStatus() {
        return status;
    }

    public JobBid status(JobStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
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
        JobBid jobBid = (JobBid) o;
        if (jobBid.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobBid.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobBid{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", wishDate='" + getWishDate() + "'" +
            ", customer='" + getcustomer() + "'" +
            ", location='" + getLocation() + "'" +
            ", partner='" + getpartner() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
