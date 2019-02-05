package com.afrologix.kahoula.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Partner entity.
 */
@ApiModel(description = "The Partner entity @author arnaud.")
public class PartnerDTO implements Serializable {

    private String id;

    private String userId;

    @NotNull
    private String cniImage;

    private String userImage;

    private String references;

    private String qualification;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCniImage() {
        return cniImage;
    }

    public void setCniImage(String cniImage) {
        this.cniImage = cniImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartnerDTO partnerDTO = (PartnerDTO) o;
        if (partnerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", cniImage='" + getCniImage() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", references='" + getReferences() + "'" +
            ", qualification='" + getQualification() + "'" +
            "}";
    }
}
