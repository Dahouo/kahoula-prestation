package com.afrologix.kahoula.resources.Bill;
import com.afrologix.kahoula.resources.JobBid.JobBidDTO;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.afrologix.kahoula.resources.enumeration.BillStatus;

/**
 * A DTO for the Bill entity.
 */
@ApiModel(description = "Bill entity. @author arnaud.")
public class BillDTO implements Serializable {

    private String id;

    @NotNull
    private String designation;

    @NotNull
    private Double quantity;

    private Double unitPrice;

    @NotNull
    private JobBidDTO jobid;

    private BillStatus status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public @NotNull JobBidDTO getJobid() {
        return jobid;
    }

    public void setJobid(JobBidDTO jobid) {
        this.jobid = jobid;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
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

        BillDTO billDTO = (BillDTO) o;
        if (billDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", jobid='" + getJobid() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
