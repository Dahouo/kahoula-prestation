package com.afrologix.kahoula.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.afrologix.kahoula.resources.enumeration.JobType;
import com.afrologix.kahoula.resources.enumeration.JobStatus;

/**
 * A DTO for the JobBid entity.
 */
@ApiModel(description = "JobBid entity. @author arnaud.")
public class JobBidDTO implements Serializable {

    private String id;

    @NotNull
    private String description;

    @NotNull
    private JobType type;

    private Instant wishDate;

    @NotNull
    private String customerId;

    @NotNull
    private String location;

    @NotNull
    private String partnerId;

    @NotNull
    private Double amount;

    private JobStatus status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Instant getWishDate() {
        return wishDate;
    }

    public void setWishDate(Instant wishDate) {
        this.wishDate = wishDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobBidDTO jobBidDTO = (JobBidDTO) o;
        if (jobBidDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobBidDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobBidDTO{" +
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
