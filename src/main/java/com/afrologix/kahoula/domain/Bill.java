package com.afrologix.kahoula.domain;


import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.afrologix.kahoula.domain.enumeration.BillStatus;

/**
 * Bill entity.
 * @author arnaud.
 */
@ApiModel(description = "Bill entity. @author arnaud.")
@Document(collection = "bill")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("designation")
    private String designation;

    @NotNull
    @Field("quantity")
    private Double quantity;

    @Field("unit_price")
    private Double unitPrice;

    @NotNull
    @Field("jobid")
    private String jobid;

    @Field("status")
    private BillStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public Bill designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Bill quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Bill unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getJobid() {
        return jobid;
    }

    public Bill jobid(String jobid) {
        this.jobid = jobid;
        return this;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public BillStatus getStatus() {
        return status;
    }

    public Bill status(BillStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(BillStatus status) {
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
        Bill bill = (Bill) o;
        if (bill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", jobid='" + getJobid() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
