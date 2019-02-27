package com.afrologix.kahoula.resources.Partner;


import com.afrologix.kahoula.resources.AbstractAuditingEntity;
import com.afrologix.kahoula.resources.User.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Partner entity
 * @author arnaud.
 */
@Document(collection = "partner")
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "partner")
public class Partner extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @DBRef
    @Field("user_id")
    private User user;

    @NotNull
    @Field("cni_image")
    private String cniImage;

    @Field("user_image")
    private String userImage;

    @Field("references")
    private String references;

    @Field("qualification")
    private String qualification;

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

    public Partner user(User user) {
        this.user = user;
        return this;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public String getCniImage() {
        return cniImage;
    }

    public Partner cniImage(String cniImage) {
        this.cniImage = cniImage;
        return this;
    }

    public void setCniImage(String cniImage) {
        this.cniImage = cniImage;
    }

    public String getUserImage() {
        return userImage;
    }

    public Partner userImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getReferences() {
        return references;
    }

    public Partner references(String references) {
        this.references = references;
        return this;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getQualification() {
        return qualification;
    }

    public Partner qualification(String qualification) {
        this.qualification = qualification;
        return this;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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
        Partner partner = (Partner) o;
        if (partner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", user='" + getuser() + "'" +
            ", cniImage='" + getCniImage() + "'" +
            ", userImage='" + getUserImage() + "'" +
            ", references='" + getReferences() + "'" +
            ", qualification='" + getQualification() + "'" +
            "}";
    }
}
