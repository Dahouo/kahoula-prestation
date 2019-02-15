package com.afrologix.kahoula.resources.Partner;
import com.afrologix.kahoula.resources.User.User;
import com.afrologix.kahoula.resources.User.UserDTO;
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

    private User user;

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

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
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
            ", user='" + getuser() + "'" +
            ", cniImage='" + getCniImage() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", references='" + getReferences() + "'" +
            ", qualification='" + getQualification() + "'" +
            "}";
    }
}
