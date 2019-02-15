package com.afrologix.kahoula.resources.JobBid;
import com.afrologix.kahoula.resources.Customer.CustomerDTO;
import com.afrologix.kahoula.resources.Location.LocationDTO;
import com.afrologix.kahoula.resources.Partner.PartnerDTO;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
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
    private List<JobType> type;

    private Instant wishDate;

    @NotNull
    private CustomerDTO customer;

    @NotNull
    private LocationDTO location;

    @NotNull
    private PartnerDTO partner;

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

    public @NotNull List<JobType> getType() {
        return type;
    }

    public void setType(List<JobType> type) {
        this.type = type;
    }

    public Instant getWishDate() {
        return wishDate;
    }

    public void setWishDate(Instant wishDate) {
        this.wishDate = wishDate;
    }

    public @NotNull CustomerDTO getcustomer() {
        return customer;
    }

    public void setcustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public @NotNull LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public @NotNull PartnerDTO getpartner() {
        return partner;
    }

    public void setpartner(PartnerDTO partner) {
        this.partner = partner;
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
            ", customer='" + getcustomer() + "'" +
            ", location='" + getLocation() + "'" +
            ", partner='" + getpartner() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
