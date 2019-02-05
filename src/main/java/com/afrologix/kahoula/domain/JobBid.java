package com.afrologix.kahoula.domain;


import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.afrologix.kahoula.domain.enumeration.JobType;

import com.afrologix.kahoula.domain.enumeration.JobStatus;

/**
 * JobBid entity.
 * @author arnaud.
 */
@ApiModel(description = "JobBid entity. @author arnaud.")
@Document(collection = "job_bid")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "jobbid")
public class JobBid implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("type")
    private JobType type;

    @Field("wish_date")
    private Instant wishDate;

    @NotNull
    @Field("customer_id")
    private String customerId;

    @NotNull
    @Field("location")
    private String location;

    @NotNull
    @Field("partner_id")
    private String partnerId;

    @NotNull
    @Field("amount")
    private Double amount;

    @Field("status")
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

    public JobType getType() {
        return type;
    }

    public JobBid type(JobType type) {
        this.type = type;
        return this;
    }

    public void setType(JobType type) {
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

    public String getCustomerId() {
        return customerId;
    }

    public JobBid customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLocation() {
        return location;
    }

    public JobBid location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public JobBid partnerId(String partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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
            ", customerId='" + getCustomerId() + "'" +
            ", location='" + getLocation() + "'" +
            ", partnerId='" + getPartnerId() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
